package com.proyecto.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.sistema.model.Usuario;
import com.proyecto.sistema.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;

    // 👉 IR A LOGIN
    @GetMapping("/loginUsuario")
    public String loginForm() {
        return "loginUsuario";
    }

    // 👉 PROCESAR LOGIN
    @PostMapping("/loginUsuario")
    public String login(
            @RequestParam(name = "correo") String correo,
            @RequestParam(name = "password") String password,
            HttpSession session,
            Model model) {

        Usuario u = repo.findByCorreoAndPassword(correo, password);

        if (u != null) {
            session.setAttribute("usuario", u);
            return "redirect:/cartelera";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "loginUsuario";
        }
    }

    // 👉 FORM REGISTRO
    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    // 👉 GUARDAR USUARIO
    @PostMapping("/registro")
    public String registrar(
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "correo") String correo,
            @RequestParam(name = "password") String password,
            Model model) {

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setPassword(password);

        repo.save(u);

        model.addAttribute("msg", "Usuario registrado correctamente ✅");
        return "loginUsuario";
    }

    // 👉 LOGOUT
    @GetMapping("/logoutUsuario")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginUsuario";
    }
}