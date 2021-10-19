package com.example.ecommute;
import java.util.*;

public class Usuario {


    private Integer idPersona;
    private String nombreUsuario;
    private String contraseña;
    private String email;
    private String nombre;
    private String apellidos;
    private boolean isAdmin;
    private String credencialesRRSS;
    private Integer puntos;
    private Integer co2Ahorrado;

    public Usuario() {
    }

    public Usuario(Integer idPersona, String nombreUsuario,
                   String contraseña, String email, String nombre,
                   String apellidos) {
        this.idPersona = idPersona;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.isAdmin = false;
        this.credencialesRRSS = null;
        this.puntos = 0;
        this.co2Ahorrado = 0;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getCredencialesRRSS() {
        return credencialesRRSS;
    }

    public void setCredencialesRRSS(String credencialesRRSS) {
        this.credencialesRRSS = credencialesRRSS;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getCo2Ahorrado() {
        return co2Ahorrado;
    }

    public void setCo2Ahorrado(Integer co2Ahorrado) {
        this.co2Ahorrado = co2Ahorrado;
    }
}