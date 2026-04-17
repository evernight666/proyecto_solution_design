package com.proyecto.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.sistema.model.Pelicula;
import com.proyecto.sistema.repository.PeliculaRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class PeliculaController {

    @Autowired
    private PeliculaRepository repo;

   
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

 
    @GetMapping("/peliculas")
    public String peliculas(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("peliculas", repo.findAll());
        return "peliculas";
    }

   
    @PostMapping("/guardarPelicula")
    public String guardar(
            @RequestParam("nombre") String nombre,
            @RequestParam("director") String director,
            @RequestParam("imagen") String imagen,
            HttpSession session) {

        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        Pelicula p = new Pelicula();
        p.setNombre(nombre);
        p.setDirector(director);
        p.setImagen(imagen);

        repo.save(p);

        return "redirect:/peliculas";
    }

   
    @GetMapping("/eliminarPelicula/{id}")
    public String eliminar(@PathVariable("id") Long id, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        repo.deleteById(id);
        return "redirect:/peliculas";
    }

   
    @GetMapping("/editarPelicula/{id}")
    public String editar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("pelicula", repo.findById(id).orElse(new Pelicula()));
        return "editarPelicula";
    }

  
    @PostMapping("/actualizarPelicula")
    public String actualizar(Pelicula pelicula, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        repo.save(pelicula);
        return "redirect:/peliculas";
    }

    
    @GetMapping("/cartelera")
    public String cartelera(Model model) {
        model.addAttribute("peliculas", repo.findAll());
        return "cartelera";
    }

    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

  
    @PostMapping("/login")
    public String validarLogin(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        if(usuario.equals("admin") && password.equals("123")) {
            session.setAttribute("admin", true);
            return "redirect:/peliculas";
        } else {
            model.addAttribute("error", "Datos incorrectos");
            return "login";
        }
    }

    		
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
						
						    
    @GetMapping("/api/peliculas")
    @ResponseBody
    public List<Pelicula> obtenerPeliculas() {
        return repo.findAll();
    }

    @GetMapping("/api/peliculas/{id}")
    @ResponseBody
    public Pelicula obtenerPorId(@PathVariable("id") Long id) {
        return repo.findById(id).orElse(null);
    }
}