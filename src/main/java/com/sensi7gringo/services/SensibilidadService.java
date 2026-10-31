package com.sensi7gringo.services;

import com.sensi7gringo.dto.GenerarSensibilidadRequest;
import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.models.Sensibilidad;
import com.sensi7gringo.repositories.DispositivoRepository;
import com.sensi7gringo.repositories.InMemoryDataStore;
import com.sensi7gringo.repositories.SensibilidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensibilidadService {

    private static final Logger log = LoggerFactory.getLogger(SensibilidadService.class);

    @Autowired(required = false)
    private SensibilidadRepository sensibilidadRepository;

    @Autowired(required = false)
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private InMemoryDataStore inMemoryDataStore;

    @Autowired
    private AlgoritmoSensibilidadService algoritmoService;

    public Sensibilidad generarSensibilidad(GenerarSensibilidadRequest request) {
        Dispositivo d = buscarDispositivo(request.getMarca(), request.getModelo());
        Sensibilidad s = algoritmoService.calcularSensibilidad(request, d);
        guardar(s);
        return s;
    }

    public Dispositivo buscarDispositivo(String marca, String modelo) {
        try {
            if (dispositivoRepository != null) {
                Optional<Dispositivo> opt = dispositivoRepository.findByMarcaIgnoreCaseAndModeloIgnoreCase(marca, modelo);
                if (opt.isPresent()) return opt.get();
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al buscar dispositivo, usando InMemoryDataStore: {}", e.getMessage());
        }
        return inMemoryDataStore.getPorMarcaYModelo(marca, modelo).orElse(null);
    }

    public Sensibilidad guardar(Sensibilidad s) {
        // Guardar siempre en memoria para inmediatez
        inMemoryDataStore.guardarSensibilidad(s);

        // Guardar en MongoDB si está activo
        try {
            if (sensibilidadRepository != null) {
                return sensibilidadRepository.save(s);
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al guardar sensibilidad, guardado en memoria: {}", e.getMessage());
        }
        return s;
    }

    public List<Sensibilidad> obtenerHistorial(String sessionId) {
        try {
            if (sensibilidadRepository != null) {
                List<Sensibilidad> list = sensibilidadRepository.findBySessionIdOrderByFechaCreacionDesc(sessionId);
                if (!list.isEmpty()) return list;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al consultar historial, usando memoria: {}", e.getMessage());
        }
        return inMemoryDataStore.getSensibilidadesPorSesion(sessionId);
    }

    public List<Sensibilidad> obtenerRanking() {
        try {
            if (sensibilidadRepository != null) {
                List<Sensibilidad> ranking = sensibilidadRepository.findTop20ByOrderByPuntuacionPromedioDescValoracionesDesc();
                if (!ranking.isEmpty()) return ranking;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al consultar ranking, usando memoria: {}", e.getMessage());
        }
        return inMemoryDataStore.getTopSensibilidades();
    }

    public Optional<Sensibilidad> obtenerPorId(String id) {
        try {
            if (sensibilidadRepository != null) {
                Optional<Sensibilidad> opt = sensibilidadRepository.findById(id);
                if (opt.isPresent()) return opt;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al obtener por id: {}", e.getMessage());
        }
        return inMemoryDataStore.getSensibilidad(id);
    }

    public boolean eliminar(String id) {
        boolean removed = inMemoryDataStore.eliminarSensibilidad(id);
        try {
            if (sensibilidadRepository != null) {
                sensibilidadRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al eliminar: {}", e.getMessage());
        }
        return removed;
    }

    public boolean alternarFavorito(String id) {
        Optional<Sensibilidad> opt = obtenerPorId(id);
        if (opt.isPresent()) {
            Sensibilidad s = opt.get();
            s.setFavorito(!s.isFavorito());
            guardar(s);
            return s.isFavorito();
        }
        return false;
    }
}
