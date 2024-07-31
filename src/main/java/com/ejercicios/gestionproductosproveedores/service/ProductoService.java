package com.ejercicios.gestionproductosproveedores.service;

import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Map<String, Object>> obtenerTodosLosProductos() {
        if (productoRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos");
        }
        return productoRepository.findAll().stream().map(producto -> {
            Map<String, Object> result = new HashMap<>();
            result.put("id", producto.getId());
            result.put("nombreProducto", producto.getNombreProducto());
            result.put("proveedorId", producto.getProveedor().getId());
            return result;
        }).collect(Collectors.toList());
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findByNombreProducto(producto.getNombreProducto());
        if (productoExistente.isPresent()) {
            throw new DuplicateResourceException("El producto ya existe.");
        }
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}