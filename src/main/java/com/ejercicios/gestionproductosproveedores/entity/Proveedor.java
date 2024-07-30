package com.ejercicios.gestionproductosproveedores.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre del proveedor no puede superar los 100 caracteres")
    private String nombreProveedor;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Producto> productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre del proveedor es obligatorio") @Size(max = 100, message = "El nombre del proveedor no puede superar los 100 caracteres") String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(@NotBlank(message = "El nombre del proveedor es obligatorio") @Size(max = 100, message = "El nombre del proveedor no puede superar los 100 caracteres") String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
