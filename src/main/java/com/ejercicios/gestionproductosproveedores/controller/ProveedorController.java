package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.ProveedorService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<?>> obtenerTodosLosProveedores() {
        logger.info("Obteniendo todos los proveedores");
        return new ResponseEntity<>(proveedorService.obtenerTodosLosProveedores(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<?>> obtenerProveedorPorId(@PathVariable Long id) {
        logger.info("Obteniendo proveedor por ID {}", id);
        List<Map<String, Object>> proveedor = proveedorService.obtenerProveedorPorId(id);
        return new ResponseEntity<>(proveedor,HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Serializable>> guardarProveedor(@RequestBody String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String nombreProveedor = jsonObject.getString("nombreProveedor");
            Proveedor proveedor = new Proveedor();
            proveedor.setNombreProveedor(nombreProveedor);
            Proveedor nuevoProveedor = proveedorService.guardarProveedor(proveedor);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor guardado correctamente", "id", nuevoProveedor.getId()));
        } catch (DuplicateResourceException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", "Ya existe un proveedor con ese nombre"));
        } catch (JSONException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Formato de JSON inválido"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error al guardar el proveedor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProveedor(@PathVariable Long id) {
        logger.info("Eliminar proveedor {}", id);
        try {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado correctamente"));
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Proveedor no encontrado"));
        } catch (Exception e) {
            logger.error("Error al eliminar el proveedor", e);
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Ocurrió un error inesperado"));
        }
    }
}