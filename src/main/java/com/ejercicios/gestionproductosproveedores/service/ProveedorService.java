package com.ejercicios.gestionproductosproveedores.service;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> obtenerTodosLosProveedores() {
        if (proveedorRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No existen proveedores");
        }
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        if (!proveedorRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No existe proveedor con id {}"+id);
        }
        return proveedorRepository.findById(id);
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