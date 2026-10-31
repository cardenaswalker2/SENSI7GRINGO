package com.sensi7gringo;

import com.sensi7gringo.dto.GenerarSensibilidadRequest;
import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.models.Sensibilidad;
import com.sensi7gringo.services.DispositivoService;
import com.sensi7gringo.services.SensibilidadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Sensi7gringoApplicationTests {

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private SensibilidadService sensibilidadService;

    @Test
    @DisplayName("El contexto debe arrancar y el catálogo de dispositivos en memoria debe estar poblado")
    void contextLoads() {
        List<Dispositivo> todos = dispositivoService.obtenerTodosDispositivos();
        assertNotNull(todos);
        assertFalse(todos.isEmpty(), "El catálogo de dispositivos debe contener datos iniciales");
    }

    @Test
    @DisplayName("Debe listar marcas correctamente por sistema operativo")
    void testListarMarcas() {
        List<String> marcasAndroid = dispositivoService.obtenerMarcasPorSistema("Android");
        assertTrue(marcasAndroid.contains("Samsung"));
        assertTrue(marcasAndroid.contains("Xiaomi"));

        List<String> marcasIos = dispositivoService.obtenerMarcasPorSistema("iOS");
        assertTrue(marcasIos.contains("Apple"));
    }

    @Test
    @DisplayName("Debe generar y guardar sensibilidad en el repositorio de fallback sin requerir MongoDB levantado")
    void testGenerarYGuardarSensibilidad() {
        GenerarSensibilidadRequest req = new GenerarSensibilidadRequest();
        req.setSessionId("test-session-integration");
        req.setSistemaOperativo("Android");
        req.setMarca("Xiaomi");
        req.setModelo("POCO X6 Pro");
        req.setNivelExperiencia("avanzado");
        req.setTipoSensibilidad("alta");
        req.setConDpi(true);
        req.setPreferenciaJuego("velocidad");

        Sensibilidad res = sensibilidadService.generarSensibilidad(req);

        assertNotNull(res);
        assertNotNull(res.getId());
        assertEquals("Xiaomi", res.getMarca());
        assertEquals("POCO X6 Pro", res.getModelo());

        List<Sensibilidad> historial = sensibilidadService.obtenerHistorial("test-session-integration");
        assertFalse(historial.isEmpty());
        assertEquals(res.getId(), historial.get(0).getId());
    }
}
