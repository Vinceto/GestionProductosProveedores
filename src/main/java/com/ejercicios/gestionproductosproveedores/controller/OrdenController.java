package com.ejercicios.gestionproductosproveedores.controller;

import com.ejercicios.gestionproductosproveedores.entity.Orden;
import com.ejercicios.gestionproductosproveedores.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Orden> obtenerOrdenPorId(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.obtenerOrdenPorId(id);
        return orden.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> guardarOrden(@RequestBody Orden orden) {
        Orden nuevaOrden = ordenService.guardarOrden(orden);
        return new ResponseEntity<>("Orden guardada con éxito: " + nuevaOrden.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return new ResponseEntity<>("Orden eliminada con éxito.", HttpStatus.NO_CONTENT);
    }
}