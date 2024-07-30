package com.ejercicios.gestionproductosproveedores.repository;

import com.ejercicios.gestionproductosproveedores.entity.OrdenProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenProductoRepository extends JpaRepository<OrdenProducto, Long> {
}
