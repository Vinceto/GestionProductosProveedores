package com.ejercicios.gestionproductosproveedores.bootstrap5.controller;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.FormElement;
import com.ejercicios.gestionproductosproveedores.bootstrap5.services.FormService;
import com.ejercicios.gestionproductosproveedores.bootstrap5.entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Controller
public class FormController {
    @Autowired
    private FormService formService;

    @GetMapping("/form/{id}")
    public String showForm(@PathVariable Long id, Model model) {
        Form form = formService.getForm(id);
        if (form != null) {
            List<FormElement> elements = formService.getFormElements(form);
            model.addAttribute("form", form);
            model.addAttribute("elements", elements);
            return "form";
        } else {
            model.addAttribute("error", "Formulario no encontrado");
            return "error";
        }
    }
}
