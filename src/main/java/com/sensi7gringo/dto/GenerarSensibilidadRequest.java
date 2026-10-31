package com.sensi7gringo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GenerarSensibilidadRequest {

    private String sessionId;

    @NotBlank(message = "El sistema operativo es requerido (Android o iOS)")
    private String sistemaOperativo;

    @NotBlank(message = "La marca es requerida")
    private String marca;

    @NotBlank(message = "El modelo exacto es requerido")
    private String modelo;

    @NotBlank(message = "El nivel de experiencia es requerido (principiante, intermedio, avanzado)")
    private String nivelExperiencia;

    @NotBlank(message = "El tipo de sensibilidad es requerido (alta, media, baja)")
    private String tipoSensibilidad;

    @NotNull(message = "Debe indicar si desea configuración con o sin DPI")
    private Boolean conDpi;

    @NotBlank(message = "La preferencia de juego es requerida (precision, velocidad, equilibrio)")
    private String preferenciaJuego;

    public GenerarSensibilidadRequest() {}

    // Getters and Setters
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

    public Boolean getConDpi() { return conDpi; }
    public void setConDpi(Boolean conDpi) { this.conDpi = conDpi; }

    public String getPreferenciaJuego() { return preferenciaJuego; }
    public void setPreferenciaJuego(String preferenciaJuego) { this.preferenciaJuego = preferenciaJuego; }
}
