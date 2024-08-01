package com.ejercicios.gestionproductosproveedores.service;
import com.ejercicios.gestionproductosproveedores.entity.Orden;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.OrdenRepository;
import com.ejercicios.gestionproductosproveedores.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ProductoRepository productoRepository;

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

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Orden actualizarOrden(Long id, Orden ordenActualizado) {
        Orden ordenExistente = ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrado con ID: " + id));

        ordenExistente.setCliente(ordenActualizado.getCliente());
        ordenExistente.setFecha(ordenActualizado.getFecha());
        ordenExistente.setOrdenesProductos(ordenActualizado.getOrdenesProductos());

        return ordenRepository.save(ordenExistente);
    }
}