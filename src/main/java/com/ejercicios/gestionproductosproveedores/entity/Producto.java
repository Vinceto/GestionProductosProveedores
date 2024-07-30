package com.ejercicios.gestionproductosproveedores.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre del producto no puede superar los 100 caracteres")
    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    @JsonBackReference
    private Proveedor proveedor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrdenProducto> ordenesProductos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre del producto es obligatorio") @Size(max = 100, message = "El nombre del producto no puede superar los 100 caracteres") String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(@NotBlank(message = "El nombre del producto es obligatorio") @Size(max = 100, message = "El nombre del producto no puede superar los 100 caracteres") String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<OrdenProducto> getOrdenesProductos() {
        return ordenesProductos;
    }

    public void setOrdenesProductos(List<OrdenProducto> ordenesProductos) {
        this.ordenesProductos = ordenesProductos;
    }
}