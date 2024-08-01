package com.ejercicios.gestionproductosproveedores.controller;
import com.ejercicios.gestionproductosproveedores.entity.Orden;
import com.ejercicios.gestionproductosproveedores.exception.DuplicateResourceException;
import com.ejercicios.gestionproductosproveedores.exception.ResourceNotFoundException;
import com.ejercicios.gestionproductosproveedores.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Maneja las solicitudes de Thymeleaf
    @GetMapping
    public String listarOrdenes(Model model) {
        model.addAttribute("ordenes", ordenService.obtenerTodasLasOrdenes());
        return "ordenes/dashboard";
    }

    @GetMapping("/{id}")
    public String verOrden(@PathVariable Long id, Model model) {
        Orden orden = ordenService.obtenerOrdenPorId(id);
        model.addAttribute("orden", orden);
        return "ordenes/ver-orden";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaOrden(Model model) {
        model.addAttribute("orden", new Orden());
        return "ordenes/crear-orden";
    }

    @PostMapping
    public String crearOrden(@ModelAttribute Orden orden) {
        ordenService.guardarOrden(orden);
        return "redirect:/ordenes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarOrden(@PathVariable Long id, Model model) {
        Orden orden = ordenService.obtenerOrdenPorId(id);
        model.addAttribute("orden", orden);
        return "ordenes/editar-orden";
    }

    @PutMapping("/{id}")
    public String actualizarOrden(@PathVariable Long id, @ModelAttribute Orden orden) {
        ordenService.actualizarOrden(id, orden);
        return "redirect:/ordenes";
    }

    @DeleteMapping("/{id}/eliminar")
    public String eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return "redirect:/ordenes";
    }

    // Métodos API
    @GetMapping("/api")
    public ResponseEntity<List<Orden>> obtenerTodasLasOrdenesApi() {
        return new ResponseEntity<>(ordenService.obtenerTodasLasOrdenes(), HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Orden> obtenerOrdenPorIdApi(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ordenService.obtenerOrdenPorId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/guardar")
    public ResponseEntity<Map<String, Object>> guardarOrdenApi(@RequestBody Orden orden) {
        try {
            Orden nuevaOrden = ordenService.guardarOrden(orden);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Orden guardada con éxito",
                    "id", nuevaOrden.getId().toString()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "Ya existe una orden con ese nombre"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error al guardar la Orden"));
        }
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Map<String, String>> eliminarOrdenApi(@PathVariable Long id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.ok(Map.of("mensaje", "Orden eliminada con éxito."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Orden no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "Error inesperado"));
        }
    }
}