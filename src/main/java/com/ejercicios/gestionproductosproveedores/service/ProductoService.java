package com.ejercicios.gestionproductosproveedores.service;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) throws ResourceNotFoundException {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (!productoOptional.isPresent()) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        return productoOptional.get();
    }

    public Producto guardarProducto(Producto producto) throws DuplicateResourceException, ResourceNotFoundException {
        if (productoRepository.findByNombreProducto(producto.getNombreProducto()).isPresent()) {
            throw new DuplicateResourceException("El producto ya existe.");
        }
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) throws ResourceNotFoundException {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}