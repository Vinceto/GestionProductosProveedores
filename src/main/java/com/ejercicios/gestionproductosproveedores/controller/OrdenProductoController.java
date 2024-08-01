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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ordenes-productos")
public class OrdenProductoController {

    @Autowired
    private OrdenProductoService ordenProductoService;
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private ProductoService productoService;

    // Maneja las solicitudes de Thymeleaf
    @GetMapping
    public String obtenerTodosLosOrdenProductos(Model model) {
        model.addAttribute("ordenProductos", ordenProductoService.obtenerTodosLosOrdenProductos());
        return "ordenes-productos"; // Vista Thymeleaf
    }

    @GetMapping("/nuevo")
    public String nuevoOrdenProducto(Model model) {
        model.addAttribute("ordenProducto", new OrdenProducto());
        model.addAttribute("ordenes", ordenService.obtenerTodasLasOrdenes());
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        return "orden-producto"; // Vista Thymeleaf para crear nueva relación
    }

    @PostMapping("/guardar")
    public String guardarOrdenProducto(@ModelAttribute OrdenProducto ordenProducto, Model model) {
        try {
            OrdenProducto nuevoOrdenProducto = ordenProductoService.guardarOrdenProducto(ordenProducto);
            model.addAttribute("mensaje", "Relación Orden-Producto guardada con éxito");
            return "redirect:/ordenes-productos";
        } catch (DuplicateResourceException e) {
            model.addAttribute("mensaje", "Ya existe esta relación de Orden-Producto");
            return "orden-producto"; // Vista Thymeleaf para error de duplicado
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la relación Orden-Producto");
            return "orden-producto"; // Vista Thymeleaf para error genérico
        }
    }

    // Métodos API
    @GetMapping("/api")
    public ResponseEntity<List<OrdenProducto>> obtenerTodosLosOrdenProductosApi() {
        return new ResponseEntity<>(ordenProductoService.obtenerTodosLosOrdenProductos(), HttpStatus.OK);
    }

    @PostMapping("/api/guardar")
    public ResponseEntity<Map<String, Object>> guardarOrdenProductoApi(@RequestBody OrdenProducto ordenProducto) {
        try {
            OrdenProducto nuevoOrdenProducto = ordenProductoService.guardarOrdenProducto(ordenProducto);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Relación Orden-Producto guardada con éxito",
                    "id", nuevoOrdenProducto.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe esta relación de Orden-Producto"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar la relación Orden-Producto"));
        }
    }
}
