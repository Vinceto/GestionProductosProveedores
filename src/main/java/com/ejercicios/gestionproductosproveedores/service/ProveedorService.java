package com.ejercicios.gestionproductosproveedores.service;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Map<String, Object>> obtenerTodosLosProveedores() {
        if (proveedorRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No existen proveedores");
        }
        return proveedorRepository.findAll().stream().map(proveedor -> {
            Map<String, Object> result = new HashMap<>();
            result.put("id", proveedor.getId());
            result.put("nombreProveedor", proveedor.getNombreProveedor());
            return result;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> obtenerProveedorPorId(Long id) {
        if (!proveedorRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No existe proveedor con id {}"+id);
        }
        return proveedorRepository.findById(id).stream().map(proveedor -> {
            Map<String, Object> result = new HashMap<>();
            result.put("id", proveedor.getId());
            result.put("nombreProveedor", proveedor.getNombreProveedor());
            return result;
        }).collect(Collectors.toList());
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        Optional<Proveedor> proveedorExistente = proveedorRepository.findByNombreProveedor(proveedor.getNombreProveedor());
        if (proveedorExistente.isPresent()) {
            throw new DuplicateResourceException("El proveedor ya existe.");
        }
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
}