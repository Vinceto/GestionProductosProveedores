package com.ejercicios.gestionproductosproveedores.bootstrap5.repository;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FormRepository extends JpaRepository<Form, Long> {}

