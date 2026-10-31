package com.sensi7gringo.services;

import com.sensi7gringo.dto.GenerarSensibilidadRequest;
import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.models.Sensibilidad;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Motor Algorítmico de Calibración de Sensibilidades Free Fire de Sensi7gringo.
 * 
 * Principio del Algoritmo:
 * En Free Fire la sensibilidad (escala 0 - 200) interactúa con:
 * 1. Tasa de muestreo táctil y refresco del panel (60Hz vs 120Hz/144Hz):
 *    Paneles con alto refresco requieren sensibilidades más compactas para no "volar" la mira por encima de la cabeza.
 * 2. Densidad de píxeles (PPI) y resolución física:
 *    A mayor PPI, el arrastre de píxeles físicos por pulgada es mayor, exigiendo calibración precisa del DPI.
 * 3. Factor táctil por Sistema Operativo (iOS touch latency vs Android touch latency):
 *    iOS posee una latencia táctil de buffer más baja (~30ms), por lo que las sensibilidades generales deben 
 *    ser equilibradas entre 120-175, mientras que Android sin DPI requiere empujes de 160-198.
 * 4. Tipo de sensibilidad elegida (Alta, Media, Baja).
 * 5. Estilo y preferencia (Precisión = mira firme a cuello/cabeza; Velocidad = giros rápidos 360 y rusheo; Equilibrio).
 * 6. Tamaño del Botón de Disparo: A mayor sensibilidad se recomienda botón menor (40-48%) para recorrido de levantamiento limpio;
 *    a menor sensibilidad botón medio-grande (50-58%).
 */
@Service
public class AlgoritmoSensibilidadService {

    public Sensibilidad calcularSensibilidad(GenerarSensibilidadRequest request, Dispositivo dispositivo) {
        Sensibilidad s = new Sensibilidad();
        s.setId(UUID.randomUUID().toString());
        s.setSessionId(request.getSessionId());
        s.setSistemaOperativo(request.getSistemaOperativo());
        s.setMarca(request.getMarca());
        s.setModelo(request.getModelo());
        s.setNivelExperiencia(request.getNivelExperiencia());
        s.setTipoSensibilidad(request.getTipoSensibilidad());
        s.setConDpi(request.getConDpi());
        s.setPreferenciaJuego(request.getPreferenciaJuego());

        // Parámetros técnicos base del dispositivo (o defaults si no está registrado)
        double baseFactor = (dispositivo != null) ? dispositivo.getFactorSensibilidadBase() : 1.0;
        int hz = (dispositivo != null) ? dispositivo.getTasaRefrescoHz() : 60;
        boolean esIos = "iOS".equalsIgnoreCase(request.getSistemaOperativo()) || "Apple".equalsIgnoreCase(request.getMarca());

        // 1. Base General según Tipo de Sensibilidad
        // Escala Free Fire moderna (0 a 200)
        double generalBase;
        switch (request.getTipoSensibilidad().toLowerCase()) {
            case "alta":
                generalBase = 182.0;
                break;
            case "baja":
                generalBase = 125.0;
                break;
            case "media":
            default:
                generalBase = 158.0;
                break;
        }

        // 2. Ajuste según Preferencia de Juego
        // Precision: Reduce ligeramente general para control de retroceso y fijación
        // Velocidad: Aumenta general y olhadinha para giros y pared gloo
        // Equilibrio: Mantiene curva óptima
        double modEstilo = 0.0;
        switch (request.getPreferenciaJuego().toLowerCase()) {
            case "precision":
                modEstilo = -10.0;
                s.setEstiloRecomendado("Enfoque en headshots limpios a media distancia y control de retroceso.");
                break;
            case "velocidad":
                modEstilo = +12.0;
                s.setEstiloRecomendado("Enfoque en rusheo agresivo, giros de cámara instantáneos y colocación de pared Gloo.");
                break;
            case "equilibrio":
            default:
                modEstilo = 0.0;
                s.setEstiloRecomendado("Balance ideal entre velocidad de levantamiento y estabilidad en enfrentamientos.");
                break;
        }

        // 3. Ajuste por Tasa de Refresco (Hz)
        // A 120Hz o 144Hz, el rastreo de dedos es hipersensible; ajustamos para evitar que los disparos se dispersen sobre el casco
        double modHz = 0.0;
        if (hz >= 120) {
            modHz = -8.0;
        } else if (hz == 90) {
            modHz = -3.0;
        }

        // 4. Ajuste por Factor de Dispositivo y SO
        double modSo = esIos ? -6.0 : +4.0;
        double modDispositivo = (baseFactor - 1.0) * 20.0;

        // 5. Ajuste si usa DPI o no
        double modDpi = 0.0;
        if (request.getConDpi() && !esIos) {
            // Con DPI alto, la pantalla se mueve más rápido por pixel -> la sensi in-game se optimiza en rangos controlados
            modDpi = -12.0;
            int dpiCalculado = calcularDpiOptimo(dispositivo, request.getTipoSensibilidad());
            s.setDpiRecomendado(dpiCalculado);
        } else {
            s.setDpiRecomendado(null);
        }

        // Cálculo de GENERAL
        int general = clamp((int) Math.round(generalBase + modEstilo + modHz + modSo + modDispositivo + modDpi), 85, 200);

        // 6. Miras complementarias calculadas armónicamente:
        // - Punto Rojo (Red Dot): Clave para tiros sin mira y distancias cortas. Debe ser cercano a General.
        // - Mira 2X: Ligeramente mayor o igual a Punto Rojo para compensar el zoom óptico.
        // - Mira 4X: Requiere mayor estabilidad; se reduce respecto a 2X para no perder el objetivo.
        // - Francotirador (AWM): Ajuste milimétrico, suele ser entre 40 y 110 para flick-shots certeros.
        // - Cámara 360 (Olhadinha): Para visibilidad periférica sin mover la retícula principal.
        
        int puntoRojo = clamp(general - (request.getPreferenciaJuego().equals("precision") ? 6 : 2), 80, 200);
        int mira2x = clamp(puntoRojo + (esIos ? 4 : 2), 80, 200);
        int mira4x = clamp(mira2x - 12, 70, 195);
        
        int awmBase = request.getTipoSensibilidad().equals("alta") ? 88 : (request.getTipoSensibilidad().equals("baja") ? 52 : 70);
        int francotirador = clamp(awmBase + (int)(modEstilo * 0.5), 35, 140);
        
        int camara360 = clamp(general + 10, 90, 200);

        // 7. Tamaño del Botón de Disparo (%)
        // Sensibilidad alta -> botón 42% - 46% (más espacio para jalar hacia arriba)
        // Sensibilidad media -> botón 47% - 52%
        // Sensibilidad baja -> botón 53% - 58%
        int boton;
        if ("alta".equalsIgnoreCase(request.getTipoSensibilidad())) {
            boton = request.getPreferenciaJuego().equals("velocidad") ? 43 : 46;
        } else if ("baja".equalsIgnoreCase(request.getTipoSensibilidad())) {
            boton = 54;
        } else {
            boton = 49;
        }

        s.setGeneral(general);
        s.setPuntoRojo(puntoRojo);
        s.setMira2x(mira2x);
        s.setMira4x(mira4x);
        s.setFrancotirador(francotirador);
        s.setCamara360(camara360);
        s.setBotonDisparo(boton);

        // Justificación explicativa para el usuario
        StringBuilder explicacion = new StringBuilder();
        explicacion.append("Calibrado para ").append(request.getModelo())
                   .append(" (").append(hz).append("Hz)");
        if (s.getDpiRecomendado() != null) {
            explicacion.append(" con DPI recomendado de ").append(s.getDpiRecomendado()).append(".");
        } else {
            explicacion.append(" con DPI estándar de fábrica para evitar desajustes del sistema.");
        }
        explicacion.append(" La Mira 2X (").append(mira2x)
                   .append(") y Punto Rojo (").append(puntoRojo)
                   .append(") están sincronizados con la respuesta táctil del display para facilitar el levantamiento de mira sin sobrepasar la cabeza.");

        s.setExplicacionAlgoritmo(explicacion.toString());
        return s;
    }

    private int calcularDpiOptimo(Dispositivo d, String tipoSensi) {
        if (d != null && d.getDpiMaximoSugerido() > 0) {
            int def = d.getDpiDefault() > 0 ? d.getDpiDefault() : 384;
            int max = d.getDpiMaximoSugerido();
            if ("alta".equalsIgnoreCase(tipoSensi)) {
                return Math.min(def + 180, max);
            } else if ("baja".equalsIgnoreCase(tipoSensi)) {
                return def + 60;
            } else {
                return Math.min(def + 120, max);
            }
        }
        // Valor genérico seguro para Android
        return "alta".equalsIgnoreCase(tipoSensi) ? 560 : 480;
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
