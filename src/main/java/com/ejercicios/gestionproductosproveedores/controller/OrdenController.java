package com.ejercicios.gestionproductosproveedores.controller;

import com.ejercicios.gestionproductosproveedores.entity.Orden;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<Orden>> obtenerTodasLasOrdenes() {
        return new ResponseEntity<>(ordenService.obtenerTodasLasOrdenes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerOrdenPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(ordenService.obtenerOrdenPorId(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarOrden(@RequestBody Orden orden) {
        try {
            Orden nuevaOrden = ordenService.guardarOrden(orden);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Orden guardada con éxito",
                    "id", nuevaOrden.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe un orden con ese nombre"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Orden no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar la Orden"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarOrden(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.ok(Map.of("mensaje", "Orden eliminada con éxito."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("mensaje", "Ocurrió un error inesperado"));
        }
    }
}