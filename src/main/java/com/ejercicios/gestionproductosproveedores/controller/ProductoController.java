package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.ProductoService;
import com.ejercicios.gestionproductosproveedores.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarProducto(@RequestBody Producto producto) throws DuplicateResourceException {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Producto guardado con éxito",
                    "id", nuevoProducto.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe un producto con ese nombre"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // Simpler approach for not found supplier
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("mensaje", "Error al guardar el producto"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // Simpler approach for not found product
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("mensaje", "Ocurrió un error inesperado"));
        }
    }
}