package com.ejercicios.gestionproductosproveedores.service;
import com.ejercicios.gestionproductosproveedores.entity.Orden;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenRepository.findAll();
    }

    public Orden obtenerOrdenPorId(Long id) throws ResourceNotFoundException {
        Optional<Orden> ordenOptional = ordenRepository.findById(id);
        if (!ordenOptional.isPresent()) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        return ordenOptional.get();
    }

    public Orden guardarOrden(Orden orden) {
        return ordenRepository.save(orden);
    }

    public void eliminarOrden(Long id) {
        ordenRepository.deleteById(id);
    }
}