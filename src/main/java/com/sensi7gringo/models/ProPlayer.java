package com.sensi7gringo.models;

import java.util.List;

public class ProPlayer {
    private String id;
    private String nombre;
    private String apodo;
    private String equipo;
    private String dispositivoReferencia;
    private String fechaVerificacion;
    private String estiloJuego;
    private int general;
    private int puntoRojo;
    private int mira2x;
    private int mira4x;
    private int francotirador;
    private int camara360;
    private int botonDisparo;
    private Integer dpi;
    private String insignia;
    private String fuenteVerificada;

    public ProPlayer() {}

    public ProPlayer(String id, String nombre, String apodo, String equipo, 
                     String dispositivoReferencia, String fechaVerificacion, 
                     String estiloJuego, int general, int puntoRojo, int mira2x, 
                     int mira4x, int francotirador, int camara360, int botonDisparo, 
                     Integer dpi, String insignia, String fuenteVerificada) {
        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.equipo = equipo;
        this.dispositivoReferencia = dispositivoReferencia;
        this.fechaVerificacion = fechaVerificacion;
        this.estiloJuego = estiloJuego;
        this.general = general;
        this.puntoRojo = puntoRojo;
        this.mira2x = mira2x;
        this.mira4x = mira4x;
        this.francotirador = francotirador;
        this.camara360 = camara360;
        this.botonDisparo = botonDisparo;
        this.dpi = dpi;
        this.insignia = insignia;
        this.fuenteVerificada = fuenteVerificada;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }
    public String getEquipo() { return equipo; }
    public void setEquipo(String equipo) { this.equipo = equipo; }
    public String getDispositivoReferencia() { return dispositivoReferencia; }
    public void setDispositivoReferencia(String dispositivoReferencia) { this.dispositivoReferencia = dispositivoReferencia; }
    public String getFechaVerificacion() { return fechaVerificacion; }
    public void setFechaVerificacion(String fechaVerificacion) { this.fechaVerificacion = fechaVerificacion; }
    public String getEstiloJuego() { return estiloJuego; }
    public void setEstiloJuego(String estiloJuego) { this.estiloJuego = estiloJuego; }
    public int getGeneral() { return general; }
    public void setGeneral(int general) { this.general = general; }
    public int getPuntoRojo() { return puntoRojo; }
    public void setPuntoRojo(int puntoRojo) { this.puntoRojo = puntoRojo; }
    public int getMira2x() { return mira2x; }
    public void setMira2x(int mira2x) { this.mira2x = mira2x; }
    public int getMira4x() { return mira4x; }
    public void setMira4x(int mira4x) { this.mira4x = mira4x; }
    public int getFrancotirador() { return francotirador; }
    public void setFrancotirador(int francotirador) { this.francotirador = francotirador; }
    public int getCamara360() { return camara360; }
    public void setCamara360(int camara360) { this.camara360 = camara360; }
    public int getBotonDisparo() { return botonDisparo; }
    public void setBotonDisparo(int botonDisparo) { this.botonDisparo = botonDisparo; }
    public Integer getDpi() { return dpi; }
    public void setDpi(Integer dpi) { this.dpi = dpi; }
    public String getInsignia() { return insignia; }
    public void setInsignia(String insignia) { this.insignia = insignia; }
    public String getFuenteVerificada() { return fuenteVerificada; }
    public void setFuenteVerificada(String fuenteVerificada) { this.fuenteVerificada = fuenteVerificada; }
}
