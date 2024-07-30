package com.ejercicios.gestionproductosproveedores.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String cliente;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrdenProducto> orderItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<OrdenProducto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrdenProducto> orderItems) {
        this.orderItems = orderItems;
    }
}