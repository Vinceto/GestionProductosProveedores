package com.ejercicios.gestionproductosproveedores.repository;
import com.ejercicios.gestionproductosproveedores.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByNombreProveedor(String nombreProveedor);
}