package com.sensi7gringo;

import com.sensi7gringo.dto.GenerarSensibilidadRequest;
import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.models.Sensibilidad;
import com.sensi7gringo.services.AlgoritmoSensibilidadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgoritmoSensibilidadServiceTest {

    private AlgoritmoSensibilidadService service;

    @BeforeEach
    void setUp() {
        service = new AlgoritmoSensibilidadService();
    }

    @Test
    @DisplayName("Debe calibrar sensibilidad coherente para iPhone 15 Pro Max")
    void testCalibracionIPhone() {
        GenerarSensibilidadRequest req = new GenerarSensibilidadRequest();
        req.setSessionId("test-session-1");
        req.setSistemaOperativo("iOS");
        req.setMarca("Apple");
        req.setModelo("iPhone 15 Pro Max");
        req.setNivelExperiencia("avanzado");
        req.setTipoSensibilidad("alta");
        req.setConDpi(false);
        req.setPreferenciaJuego("velocidad");

        Dispositivo d = new Dispositivo("ios-1", "Apple", "iPhone 15 Pro Max", "iOS", 6.7, "1290x2796", 460, 120, 1.15, 0, 0);

        Sensibilidad res = service.calcularSensibilidad(req, d);

        assertNotNull(res);
        assertTrue(res.getGeneral() >= 100 && res.getGeneral() <= 200, "General debe estar dentro de la escala Free Fire (100-200)");
        assertTrue(res.getPuntoRojo() >= 90 && res.getPuntoRojo() <= 200);
        assertTrue(res.getMira2x() >= 90 && res.getMira2x() <= 200);
        assertTrue(res.getFrancotirador() >= 40 && res.getFrancotirador() <= 150);
        assertTrue(res.getBotonDisparo() >= 40 && res.getBotonDisparo() <= 60);
        assertNull(res.getDpiRecomendado(), "En iOS no se recomienda modificar DPI");
        assertNotNull(res.getExplicacionAlgoritmo());
    }

    @Test
    @DisplayName("Debe calcular DPI recomendado para Android con opción Con DPI")
    void testCalibracionAndroidConDpi() {
        GenerarSensibilidadRequest req = new GenerarSensibilidadRequest();
        req.setSessionId("test-session-2");
        req.setSistemaOperativo("Android");
        req.setMarca("Samsung");
        req.setModelo("Galaxy S24 Ultra");
        req.setNivelExperiencia("intermedio");
        req.setTipoSensibilidad("alta");
        req.setConDpi(true);
        req.setPreferenciaJuego("precision");

        Dispositivo d = new Dispositivo("sam-1", "Samsung", "Galaxy S24 Ultra", "Android", 6.8, "1440x3120", 505, 120, 1.18, 411, 600);

        Sensibilidad res = service.calcularSensibilidad(req, d);

        assertNotNull(res);
        assertNotNull(res.getDpiRecomendado(), "En Android con DPI activado debe devolver un valor de DPI numérico");
        assertTrue(res.getDpiRecomendado() >= 411 && res.getDpiRecomendado() <= 600, "DPI debe estar dentro del rango seguro para el modelo");
        assertEquals(46, res.getBotonDisparo());
    }
}
