package com.ejercicios.gestionproductosproveedores.controller;

import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    @Autowired
    private ProveedorService proveedorService;

    // Maneja las solicitudes de Thymeleaf
    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.obtenerTodosLosProveedores());
        return "proveedores/mantenedor-proveedores";
    }

    @GetMapping("/{id}")
    public String verProveedor(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(id);
        model.addAttribute("proveedor", proveedor);
        return "proveedores/ver-proveedor";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/crear-proveedor";
    }

    @PostMapping
    public String crearProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorService.guardarProveedor(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarProveedor(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(id);
        model.addAttribute("proveedor", proveedor);
        return "proveedores/editar-proveedor";
    }

    @PutMapping("/{id}")
    public String actualizarProveedor(@PathVariable Long id, @ModelAttribute Proveedor proveedor) {
        proveedorService.actualizarProveedor(id, proveedor);
        return "redirect:/proveedores";
    }

    @DeleteMapping("/{id}/eliminar")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return "redirect:/proveedores";
    }

    // Métodos API
    @GetMapping("/api")
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedoresApi() {
        return new ResponseEntity<>(proveedorService.obtenerTodosLosProveedores(), HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorIdApi(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(proveedorService.obtenerProveedorPorId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/guardar")
    public ResponseEntity<Map<String, Object>> guardarProveedorApi(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevoProveedor = proveedorService.guardarProveedor(proveedor);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Proveedor guardado con éxito",
                    "id", nuevoProveedor.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe un proveedor con ese nombre"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar el proveedor"));
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Map<String, String>> eliminarProveedorApi(@PathVariable Long id) {
        try {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Proveedor no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error inesperado"));
        }
    }
}