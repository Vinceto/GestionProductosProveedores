package com.ejercicios.gestionproductosproveedores.bootstrap5.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "form_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    private String type; // tipo de input (text, email, password, etc.)
    private String label;
    private String name;
    private String placeholder;
    private boolean required;
    private String options; // opciones para select, radio, checkbox (formato JSON)
    private int orden; // orden del elemento en el formulario
}