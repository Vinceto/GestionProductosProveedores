package com.ejercicios.gestionproductosproveedores.repository;
import com.ejercicios.gestionproductosproveedores.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombreProducto(String nombreProducto);
}