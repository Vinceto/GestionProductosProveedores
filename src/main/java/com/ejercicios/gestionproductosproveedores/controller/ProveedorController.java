package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;


    @GetMapping
    public ResponseEntity<List<Proveedor>>
    obtenerTodosLosProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerTodosLosProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(proveedorService.obtenerProveedorPorId(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarProveedor(@RequestBody Proveedor proveedor) throws DuplicateResourceException {
        try {
            Proveedor nuevoProveedor = proveedorService.guardarProveedor(proveedor);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor guardado correctamente", "id", nuevoProveedor.getId()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe un proveedor con ese nombre"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("mensaje", "Error al guardar el proveedor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProveedor(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado correctamente"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // Simpler approach for not found
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("mensaje", "Ocurrió un error inesperado"));
        }
    }
}