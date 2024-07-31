package com.ejercicios.gestionproductosproveedores.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonBackReference("orden-ordenesproductos")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference("producto-ordenProductos")
    private Producto producto;

    private int cantidad;
}