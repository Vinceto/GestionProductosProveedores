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
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerProveedorPorId(Long id) throws ResourceNotFoundException {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
        if (!proveedorOptional.isPresent()) {
            throw new ResourceNotFoundException("No existe proveedor con id: " + id);
        }
        return proveedorOptional.get();
    }

    public Proveedor guardarProveedor(Proveedor proveedor) throws DuplicateResourceException {
        if (proveedorRepository.findByNombreProveedor(proveedor.getNombreProveedor()).isPresent()) {
            throw new DuplicateResourceException("El proveedor ya existe.");
        }
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) throws ResourceNotFoundException {
        if (!proveedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    public Proveedor actualizarProveedor(Long id, Proveedor proveedorActualizado) {
        Proveedor proveedorExistente = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));

        proveedorExistente.setNombreProveedor(proveedorActualizado.getNombreProveedor());
        proveedorExistente.setProductos(proveedorActualizado.getProductos());

        return proveedorRepository.save(proveedorExistente);
    }
}