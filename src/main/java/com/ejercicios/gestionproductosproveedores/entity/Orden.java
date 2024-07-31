package com.ejercicios.gestionproductosproveedores.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Table(name = "ordenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private String fecha;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @JsonManagedReference("orden-ordenesproductos")
    private List<OrdenProducto> ordenesProductos;
}