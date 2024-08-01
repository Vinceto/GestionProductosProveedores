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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;

    // Maneja las solicitudes de Thymeleaf
    @GetMapping
    public String obtenerTodosLosProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        return "productos"; // Vista Thymeleaf
    }

    @GetMapping("/{id}")
    public String obtenerProductoPorId(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("producto", productoService.obtenerProductoPorId(id));
            return "producto"; // Vista Thymeleaf
        } catch (ResourceNotFoundException e) {
            model.addAttribute("mensaje", "Producto no encontrado");
            return "error"; // Vista Thymeleaf para errores
        }
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("proveedores", proveedorService.obtenerTodosLosProveedores());
        return "producto"; // Vista Thymeleaf para crear nuevo producto
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, Model model) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            model.addAttribute("mensaje", "Producto guardado con éxito");
            return "redirect:/productos";
        } catch (DuplicateResourceException e) {
            model.addAttribute("mensaje", "Ya existe un producto con ese nombre");
            return "producto"; // Vista Thymeleaf para error de duplicado
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el producto");
            return "producto"; // Vista Thymeleaf para error genérico
        }
    }

    @GetMapping("/{id}/editar")
    public String editarProducto(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("producto", productoService.obtenerProductoPorId(id));
            model.addAttribute("proveedores", proveedorService.obtenerTodosLosProveedores());
            return "producto"; // Vista Thymeleaf para editar producto
        } catch (ResourceNotFoundException e) {
            model.addAttribute("mensaje", "Producto no encontrado");
            return "error"; // Vista Thymeleaf para errores
        }
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable Long id, Model model) {
        try {
            productoService.eliminarProducto(id);
            model.addAttribute("mensaje", "Producto eliminado con éxito");
        } catch (ResourceNotFoundException e) {
            model.addAttribute("mensaje", "Producto no encontrado");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error inesperado");
        }
        return "redirect:/productos"; // Redirige a la lista de productos
    }

    // Métodos API
    @GetMapping("/api")
    public ResponseEntity<List<Producto>> obtenerTodosLosProductosApi() {
        return new ResponseEntity<>(productoService.obtenerTodosLosProductos(), HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Producto> obtenerProductoPorIdApi(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/guardar")
    public ResponseEntity<Map<String, Object>> guardarProductoApi(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Producto guardado con éxito",
                    "id", nuevoProducto.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe un producto con ese nombre"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar el producto"));
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Map<String, String>> eliminarProductoApi(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error inesperado"));
        }
    }
}