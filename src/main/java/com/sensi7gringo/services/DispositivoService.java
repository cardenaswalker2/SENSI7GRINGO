package com.sensi7gringo.services;

import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.repositories.DispositivoRepository;
import com.sensi7gringo.repositories.InMemoryDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DispositivoService {

    private static final Logger log = LoggerFactory.getLogger(DispositivoService.class);

    @Autowired(required = false)
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private InMemoryDataStore inMemoryDataStore;

    public List<String> obtenerMarcasPorSistema(String sistemaOperativo) {
        List<Dispositivo> todos = obtenerTodosDispositivos();
        return todos.stream()
                .filter(d -> sistemaOperativo == null || sistemaOperativo.isEmpty() || d.getSistemaOperativo().equalsIgnoreCase(sistemaOperativo))
                .map(Dispositivo::getMarca)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Dispositivo> obtenerModelosPorMarca(String marca) {
        List<Dispositivo> todos = obtenerTodosDispositivos();
        return todos.stream()
                .filter(d -> d.getMarca().equalsIgnoreCase(marca))
                .sorted(Comparator.comparing(Dispositivo::getModelo))
                .collect(Collectors.toList());
    }

    public List<Dispositivo> buscarDispositivos(String query) {
        if (query == null || query.trim().isEmpty()) {
            return obtenerTodosDispositivos();
        }
        String q = query.toLowerCase().trim();
        return obtenerTodosDispositivos().stream()
                .filter(d -> d.getModelo().toLowerCase().contains(q) || d.getMarca().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Dispositivo> obtenerTodosDispositivos() {
        try {
            if (dispositivoRepository != null) {
                List<Dispositivo> fromDb = dispositivoRepository.findAll();
                if (!fromDb.isEmpty()) return fromDb;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al listar dispositivos, usando InMemoryDataStore: {}", e.getMessage());
        }
        return inMemoryDataStore.getTodosDispositivos();
    }

    public Optional<Dispositivo> obtenerPorId(String id) {
        try {
            if (dispositivoRepository != null) {
                Optional<Dispositivo> opt = dispositivoRepository.findById(id);
                if (opt.isPresent()) return opt;
            }
        } catch (Exception e) {
            log.warn("MongoDB no disponible al consultar dispositivo: {}", e.getMessage());
        }
        return inMemoryDataStore.getDispositivo(id);
    }
}
