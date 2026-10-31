package com.sensi7gringo.repositories;

import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.models.Sensibilidad;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Repositorio en memoria concurrente de alta disponibilidad que garantiza
 * que Sensi7gringo funcione al 100% incluso si MongoDB está apagado o en mantenimiento.
 */
@Component
public class InMemoryDataStore {

    private final Map<String, Dispositivo> dispositivos = new ConcurrentHashMap<>();
    private final Map<String, Sensibilidad> sensibilidades = new ConcurrentHashMap<>();

    public InMemoryDataStore() {
        inicializarDispositivosSemilla();
    }

    private void inicializarDispositivosSemilla() {
        // Catálogo de dispositivos reales y calibrados
        // iOS
        agregar(new Dispositivo("ios-1", "Apple", "iPhone 15 Pro Max", "iOS", 6.7, "1290x2796", 460, 120, 1.15, 0, 0));
        agregar(new Dispositivo("ios-2", "Apple", "iPhone 15 Pro", "iOS", 6.1, "1179x2556", 460, 120, 1.14, 0, 0));
        agregar(new Dispositivo("ios-3", "Apple", "iPhone 14", "iOS", 6.1, "1170x2532", 460, 60, 1.05, 0, 0));
        agregar(new Dispositivo("ios-4", "Apple", "iPhone 13 Pro Max", "iOS", 6.7, "1284x2778", 458, 120, 1.12, 0, 0));
        agregar(new Dispositivo("ios-5", "Apple", "iPhone 11", "iOS", 6.1, "828x1792", 326, 60, 0.98, 0, 0));
        agregar(new Dispositivo("ios-6", "Apple", "iPhone XR", "iOS", 6.1, "828x1792", 326, 60, 0.96, 0, 0));
        agregar(new Dispositivo("ios-7", "Apple", "iPhone 8 Plus", "iOS", 5.5, "1080x1920", 401, 60, 0.92, 0, 0));

        // Samsung
        agregar(new Dispositivo("sam-1", "Samsung", "Galaxy S24 Ultra", "Android", 6.8, "1440x3120", 505, 120, 1.18, 411, 600));
        agregar(new Dispositivo("sam-2", "Samsung", "Galaxy S23 Ultra", "Android", 6.8, "1440x3088", 500, 120, 1.16, 411, 590));
        agregar(new Dispositivo("sam-3", "Samsung", "Galaxy A54 5G", "Android", 6.4, "1080x2340", 403, 120, 1.05, 384, 550));
        agregar(new Dispositivo("sam-4", "Samsung", "Galaxy A34 5G", "Android", 6.6, "1080x2340", 390, 120, 1.02, 384, 520));
        agregar(new Dispositivo("sam-5", "Samsung", "Galaxy A14", "Android", 6.6, "1080x2408", 400, 90, 0.95, 384, 480));
        agregar(new Dispositivo("sam-6", "Samsung", "Galaxy A10 / A20", "Android", 6.2, "720x1520", 271, 60, 0.88, 360, 440));

        // Xiaomi / Redmi / POCO
        agregar(new Dispositivo("xia-1", "Xiaomi", "Xiaomi 13T Pro", "Android", 6.67, "1220x2712", 446, 144, 1.20, 392, 620));
        agregar(new Dispositivo("xia-2", "Xiaomi", "Redmi Note 13 Pro+", "Android", 6.67, "1220x2712", 446, 120, 1.10, 392, 580));
        agregar(new Dispositivo("xia-3", "Xiaomi", "Redmi Note 12", "Android", 6.67, "1080x2400", 395, 120, 1.03, 392, 540));
        agregar(new Dispositivo("xia-4", "Xiaomi", "POCO X6 Pro", "Android", 6.67, "1220x2712", 446, 120, 1.15, 392, 600));
        agregar(new Dispositivo("xia-5", "Xiaomi", "POCO F5", "Android", 6.67, "1080x2400", 395, 120, 1.12, 392, 590));
        agregar(new Dispositivo("xia-6", "Xiaomi", "Redmi 9 / 10C", "Android", 6.53, "720x1600", 269, 60, 0.90, 360, 450));

        // Motorola
        agregar(new Dispositivo("mot-1", "Motorola", "Edge 40 Neo", "Android", 6.55, "1080x2400", 402, 144, 1.14, 411, 590));
        agregar(new Dispositivo("mot-2", "Motorola", "Moto G84 5G", "Android", 6.5, "1080x2400", 405, 120, 1.04, 411, 540));
        agregar(new Dispositivo("mot-3", "Motorola", "Moto G54", "Android", 6.5, "1080x2400", 405, 120, 1.02, 411, 520));
        agregar(new Dispositivo("mot-4", "Motorola", "Moto G22 / G23", "Android", 6.5, "720x1600", 268, 90, 0.92, 384, 460));

        // Realme
        agregar(new Dispositivo("rea-1", "Realme", "Realme GT Neo 5 / GT3", "Android", 6.74, "1240x2772", 451, 144, 1.18, 400, 600));
        agregar(new Dispositivo("rea-2", "Realme", "Realme 11 Pro+", "Android", 6.7, "1080x2412", 394, 120, 1.06, 380, 560));
        agregar(new Dispositivo("rea-3", "Realme", "Realme C55", "Android", 6.72, "1080x2400", 392, 90, 0.98, 380, 500));

        // Huawei
        agregar(new Dispositivo("hua-1", "Huawei", "P60 Pro", "Android", 6.67, "1220x2700", 444, 120, 1.12, 400, 580));
        agregar(new Dispositivo("hua-2", "Huawei", "Nova 11", "Android", 6.7, "1084x2412", 395, 120, 1.04, 380, 540));
        agregar(new Dispositivo("hua-3", "Huawei", "Y9 Prime", "Android", 6.59, "1080x2340", 391, 60, 0.93, 360, 480));

        // Infinix / Tecno
        agregar(new Dispositivo("inf-1", "Infinix", "Infinix GT 20 Pro", "Android", 6.78, "1080x2436", 393, 144, 1.15, 400, 590));
        agregar(new Dispositivo("inf-2", "Infinix", "Infinix Hot 40 Pro", "Android", 6.78, "1080x2460", 396, 120, 1.03, 380, 520));
        agregar(new Dispositivo("tec-1", "Tecno", "Tecno Camon 30 Pro", "Android", 6.78, "1080x2436", 393, 144, 1.12, 390, 560));
        agregar(new Dispositivo("tec-2", "Tecno", "Tecno Spark 20 Pro", "Android", 6.78, "1080x2460", 396, 120, 1.00, 380, 510));
    }

    private void agregar(Dispositivo d) {
        dispositivos.put(d.getId(), d);
    }

    public List<Dispositivo> getTodosDispositivos() {
        return new ArrayList<>(dispositivos.values());
    }

    public Optional<Dispositivo> getDispositivo(String id) {
        return Optional.ofNullable(dispositivos.get(id));
    }

    public List<Dispositivo> getPorSistema(String so) {
        return dispositivos.values().stream()
                .filter(d -> d.getSistemaOperativo().equalsIgnoreCase(so))
                .collect(Collectors.toList());
    }

    public List<Dispositivo> getPorMarca(String marca) {
        return dispositivos.values().stream()
                .filter(d -> d.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public Optional<Dispositivo> getPorMarcaYModelo(String marca, String modelo) {
        return dispositivos.values().stream()
                .filter(d -> d.getMarca().equalsIgnoreCase(marca) && d.getModelo().equalsIgnoreCase(modelo))
                .findFirst();
    }

    public void guardarDispositivo(Dispositivo d) {
        if (d.getId() == null || d.getId().isEmpty()) {
            d.setId(UUID.randomUUID().toString());
        }
        dispositivos.put(d.getId(), d);
    }

    // Sensibilidades
    public void guardarSensibilidad(Sensibilidad s) {
        if (s.getId() == null || s.getId().isEmpty()) {
            s.setId(UUID.randomUUID().toString());
        }
        sensibilidades.put(s.getId(), s);
    }

    public Optional<Sensibilidad> getSensibilidad(String id) {
        return Optional.ofNullable(sensibilidades.get(id));
    }

    public List<Sensibilidad> getSensibilidadesPorSesion(String sessionId) {
        return sensibilidades.values().stream()
                .filter(s -> sessionId != null && sessionId.equals(s.getSessionId()))
                .sorted((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion()))
                .collect(Collectors.toList());
    }

    public List<Sensibilidad> getTopSensibilidades() {
        return sensibilidades.values().stream()
                .sorted((a, b) -> Double.compare(b.getPuntuacionPromedio(), a.getPuntuacionPromedio()))
                .limit(20)
                .collect(Collectors.toList());
    }

    public boolean eliminarSensibilidad(String id) {
        return sensibilidades.remove(id) != null;
    }
}
