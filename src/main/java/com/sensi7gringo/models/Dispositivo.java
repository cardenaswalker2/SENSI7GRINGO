package com.sensi7gringo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "dispositivos")
public class Dispositivo {

    @Id
    private String id;
    private String marca;
    private String modelo;
    private String sistemaOperativo; // "Android" | "iOS"
    private double tamanoPantalla;   // pulgadas (ej: 6.67)
    private String resolucion;       // ej: "1080x2400"
    private int densidadPpi;         // ej: 395
    private int tasaRefrescoHz;      // ej: 90, 120, 60
    private double factorSensibilidadBase; // Factor de friccion y respuesta táctil (ej: 0.95 - 1.15)
    private int dpiDefault;          // ej: 384 o 411
    private int dpiMaximoSugerido;   // ej: 580 o 640
    private boolean disponible;
    private LocalDateTime fechaRegistro;

    public Dispositivo() {
        this.disponible = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Dispositivo(String id, String marca, String modelo, String sistemaOperativo, 
                       double tamanoPantalla, String resolucion, int densidadPpi, 
                       int tasaRefrescoHz, double factorSensibilidadBase, 
                       int dpiDefault, int dpiMaximoSugerido) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaOperativo = sistemaOperativo;
        this.tamanoPantalla = tamanoPantalla;
        this.resolucion = resolucion;
        this.densidadPpi = densidadPpi;
        this.tasaRefrescoHz = tasaRefrescoHz;
        this.factorSensibilidadBase = factorSensibilidadBase;
        this.dpiDefault = dpiDefault;
        this.dpiMaximoSugerido = dpiMaximoSugerido;
        this.disponible = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public double getTamanoPantalla() { return tamanoPantalla; }
    public void setTamanoPantalla(double tamanoPantalla) { this.tamanoPantalla = tamanoPantalla; }

    public String getResolucion() { return resolucion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }

    public int getDensidadPpi() { return densidadPpi; }
    public void setDensidadPpi(int densidadPpi) { this.densidadPpi = densidadPpi; }

    public int getTasaRefrescoHz() { return tasaRefrescoHz; }
    public void setTasaRefrescoHz(int tasaRefrescoHz) { this.tasaRefrescoHz = tasaRefrescoHz; }

    public double getFactorSensibilidadBase() { return factorSensibilidadBase; }
    public void setFactorSensibilidadBase(double factorSensibilidadBase) { this.factorSensibilidadBase = factorSensibilidadBase; }

    public int getDpiDefault() { return dpiDefault; }
    public void setDpiDefault(int dpiDefault) { this.dpiDefault = dpiDefault; }

    public int getDpiMaximoSugerido() { return dpiMaximoSugerido; }
    public void setDpiMaximoSugerido(int dpiMaximoSugerido) { this.dpiMaximoSugerido = dpiMaximoSugerido; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
