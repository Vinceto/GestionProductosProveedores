package com.ejercicios.gestionproductosproveedores.service;

import com.ejercicios.gestionproductosproveedores.entity.OrdenProducto;
import com.ejercicios.gestionproductosproveedores.repository.OrdenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenProductoService {

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    public List<OrdenProducto> obtenerTodosLosOrdenProductos() {
        return ordenProductoRepository.findAll();
    }

    public OrdenProducto guardarOrdenProducto(OrdenProducto ordenProducto) {
        return ordenProductoRepository.save(ordenProducto);
    }
}