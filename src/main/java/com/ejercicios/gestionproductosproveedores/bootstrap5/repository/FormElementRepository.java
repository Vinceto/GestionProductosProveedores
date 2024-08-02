package com.ejercicios.gestionproductosproveedores.bootstrap5.repository;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.Form;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.FormElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FormElementRepository extends JpaRepository<FormElement, Long> {
    List<FormElement> findByFormOrderByOrderAsc(Form form);
}
