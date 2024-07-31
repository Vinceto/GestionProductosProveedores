package com.ejercicios.gestionproductosproveedores.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private String fecha;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrdenProducto> ordenesProductos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<OrdenProducto> getOrdenesProductos() {
        return ordenesProductos;
    }

    public void setOrdenesProductos(List<OrdenProducto> ordenesProductos) {
        this.ordenesProductos = ordenesProductos;
    }
}