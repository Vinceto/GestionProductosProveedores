package com.ejercicios.gestionproductosproveedores.bootstrap5.services;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.Form;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.FormElement;
import com.ejercicios.gestionproductosproveedores.bootstrap5.repository.FormElementRepository;
import com.ejercicios.gestionproductosproveedores.bootstrap5.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormElementRepository formElementRepository;

    public Form getForm(Long id) {
        return formRepository.findById(id).orElse(null);
    }

    public List<FormElement> getFormElements(Form form) {
        return formElementRepository.findByFormOrderByOrdenAsc(form);
    }
}