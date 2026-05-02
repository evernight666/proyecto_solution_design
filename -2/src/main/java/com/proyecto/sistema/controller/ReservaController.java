package com.proyecto.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.sistema.model.Reserva;
import com.proyecto.sistema.model.Usuario;
import com.proyecto.sistema.repository.ReservaRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository repo;

    // 👉 MOSTRAR FORMULARIO
    @GetMapping("/reservar")
    public String formulario(
            @RequestParam(name = "pelicula", required = false) String pelicula,
            Model model,
            HttpSession session) {

        // 🔒 PROTEGER
        if (session.getAttribute("usuario") == null) {
            return "redirect:/loginUsuario";
        }

        if (pelicula == null || pelicula.isEmpty()) {
            pelicula = "No seleccionada";
        }

        model.addAttribute("pelicula", pelicula);

        List<Reserva> ocupados = repo.findAll();
        model.addAttribute("ocupados", ocupados);

        return "reserva";
    }

    // 👉 GUARDAR RESERVA
    @PostMapping("/guardarReserva")
    public String guardar(
            @RequestParam(name = "pelicula") String pelicula,
            @RequestParam(name = "horario") String horario,
            @RequestParam(name = "asiento") int asiento,
            Model model,
            HttpSession session) {

        // 🔒 PROTEGER
        if (session.getAttribute("usuario") == null) {
            return "redirect:/loginUsuario";
        }

        // 👤 OBTENER USUARIO LOGUEADO
        Usuario u = (Usuario) session.getAttribute("usuario");

        // 🔒 VALIDAR SI YA EXISTE
        boolean ocupado = repo.existsByAsientoAndHorario(asiento, horario);

        if (ocupado) {
            model.addAttribute("error", "⚠️ Ese asiento ya está ocupado");
            model.addAttribute("pelicula", pelicula);
            model.addAttribute("ocupados", repo.findAll());
            return "reserva";
        }

        // 💰 PRECIO
        double precio;
        if (asiento <= 20) precio = 10;
        else if (asiento <= 40) precio = 15;
        else precio = 20;

        Reserva reserva = new Reserva();
        reserva.setCliente(u.getNombre()); // 🔥 AUTOMÁTICO
        reserva.setPelicula(pelicula);
        reserva.setHorario(horario);
        reserva.setAsiento(asiento);
        reserva.setPrecio(precio);

        repo.save(reserva);

        model.addAttribute("reserva", reserva);

        return "confirmacion";
    }

    // 🔥 API JSON
    @GetMapping("/api/reservas")
    @ResponseBody
    public List<Reserva> obtenerReservas() {
        return repo.findAll();
    }
}