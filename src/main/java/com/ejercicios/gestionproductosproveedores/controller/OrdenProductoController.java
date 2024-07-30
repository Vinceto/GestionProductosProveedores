package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.OrdenProducto;
import com.ejercicios.gestionproductosproveedores.service.OrdenProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-productos")
public class OrdenProductoController {

    @Autowired
    private OrdenProductoService ordenProductoService;

    @GetMapping
    public ResponseEntity<List<OrdenProducto>> obtenerTodosLosOrdenProductos() {
        return new ResponseEntity<>(ordenProductoService.obtenerTodosLosOrdenProductos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> guardarOrdenProducto(@RequestBody OrdenProducto ordenProducto) {
        OrdenProducto nuevoOrdenProducto = ordenProductoService.guardarOrdenProducto(ordenProducto);
        return new ResponseEntity<>("OrdenProducto guardado con éxito: " + nuevoOrdenProducto.getId(), HttpStatus.CREATED);
    }
}