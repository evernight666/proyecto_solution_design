package com.proyecto.sistema.model;

import jakarta.persistence.*;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private String pelicula;
    private String horario;

    private int asiento;      // ✅ CAMBIAR
    private double precio;    // ✅ AGREGAR

    public Long getId() { return id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getPelicula() { return pelicula; }
    public void setPelicula(String pelicula) { this.pelicula = pelicula; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getAsiento() { return asiento; }   // ✅
    public void setAsiento(int asiento) { this.asiento = asiento; } // ✅

    public double getPrecio() { return precio; }  // ✅
    public void setPrecio(double precio) { this.precio = precio; } // ✅
}