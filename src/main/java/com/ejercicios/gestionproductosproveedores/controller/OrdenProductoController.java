package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.OrdenProducto;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.OrdenRepository;
import com.ejercicios.gestionproductosproveedores.service.OrdenProductoService;
import com.ejercicios.gestionproductosproveedores.service.OrdenService;
import com.ejercicios.gestionproductosproveedores.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes-productos")
public class OrdenProductoController {

    @Autowired
    private OrdenProductoService ordenProductoService;
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<OrdenProducto>> obtenerTodosLosOrdenProductos() {
        return new ResponseEntity<>(ordenProductoService.obtenerTodosLosOrdenProductos(), HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarOrdenProducto(@RequestBody Map<String, Object> request) {
        try {
            Long orden_id = Long.valueOf(request.get("orden_id").toString());
            Long producto_id = Long.valueOf(request.get("producto_id").toString());
            int cantidad = Integer.parseInt(request.get("cantidad").toString());

            OrdenProducto ordenProducto = new OrdenProducto();
            ordenProducto.setOrden(ordenService.obtenerOrdenPorId(orden_id));
            ordenProducto.setProducto(productoService.obtenerProductoPorId(producto_id));
            ordenProducto.setCantidad(cantidad);

            OrdenProducto nuevoOrdenProducto = ordenProductoService.guardarOrdenProducto(ordenProducto);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "OrdenProducto guardado con éxito",
                    "id", nuevoOrdenProducto.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe esta relacion de OrdenProducto"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Relacion OrdenProducto no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar la relacion en OrdenProducto"));
        }
    }
}