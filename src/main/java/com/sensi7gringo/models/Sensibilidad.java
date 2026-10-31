package com.sensi7gringo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "sensibilidades")
public class Sensibilidad {

    @Id
    private String id;
    private String sessionId;       // Identificador único de sesión de usuario para persistencia
    private String sistemaOperativo;
    private String marca;
    private String modelo;
    private String nivelExperiencia; // principiante, intermedio, avanzado
    private String tipoSensibilidad; // alta, media, baja
    private boolean conDpi;
    private String preferenciaJuego; // precision, velocidad, equilibrio

    // Valores Calculados (Escala Free Fire 0 - 200)
    private int general;
    private int puntoRojo;
    private int mira2x;
    private int mira4x;
    private int francotirador;
    private int camara360; // Olhadinha
    private int botonDisparo; // Tamaño recomendado % (ej: 42% - 58%)
    private Integer dpiRecomendado; // ej: 540 (null si es iOS o sin DPI)

    // Justificación técnica
    private String explicacionAlgoritmo;
    private String estiloRecomendado;
    private boolean favorito;
    private int valoraciones;
    private double puntuacionPromedio;
    private LocalDateTime fechaCreacion;

    public Sensibilidad() {
        this.favorito = false;
        this.valoraciones = 0;
        this.puntuacionPromedio = 5.0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNivelExperiencia() { return nivelExperiencia; }
    public void setNivelExperiencia(String nivelExperiencia) { this.nivelExperiencia = nivelExperiencia; }

    public String getTipoSensibilidad() { return tipoSensibilidad; }
    public void setTipoSensibilidad(String tipoSensibilidad) { this.tipoSensibilidad = tipoSensibilidad; }

    public boolean isConDpi() { return conDpi; }
    public void setConDpi(boolean conDpi) { this.conDpi = conDpi; }

    public String getPreferenciaJuego() { return preferenciaJuego; }
    public void setPreferenciaJuego(String preferenciaJuego) { this.preferenciaJuego = preferenciaJuego; }

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

    public Integer getDpiRecomendado() { return dpiRecomendado; }
    public void setDpiRecomendado(Integer dpiRecomendado) { this.dpiRecomendado = dpiRecomendado; }

    public String getExplicacionAlgoritmo() { return explicacionAlgoritmo; }
    public void setExplicacionAlgoritmo(String explicacionAlgoritmo) { this.explicacionAlgoritmo = explicacionAlgoritmo; }

    public String getEstiloRecomendado() { return estiloRecomendado; }
    public void setEstiloRecomendado(String estiloRecomendado) { this.estiloRecomendado = estiloRecomendado; }

    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }

    public int getValoraciones() { return valoraciones; }
    public void setValoraciones(int valoraciones) { this.valoraciones = valoraciones; }

    public double getPuntuacionPromedio() { return puntuacionPromedio; }
    public void setPuntuacionPromedio(double puntuacionPromedio) { this.puntuacionPromedio = puntuacionPromedio; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
