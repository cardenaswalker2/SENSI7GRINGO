package com.sensi7gringo.services;

import com.sensi7gringo.models.ProPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProPlayerService {

    private final List<ProPlayer> players = new ArrayList<>();

    public ProPlayerService() {
        // Configuraciones de referencia documentadas de jugadores verificados
        players.add(new ProPlayer(
                "pro-1", "Bruno Goes", "Nobru", "Fluxo",
                "iPhone 15 Pro Max", "2024 - Temporada LBFF", "Rusher agresivo / Levantamiento ágil",
                195, 192, 188, 178, 85, 200, 44, null, "👑 Campeón Mundial", "Transmisiones y entrevistas oficiales"
        ));

        players.add(new ProPlayer(
                "pro-2", "Ygor Araújo", "Fantasma", "Ex-LOUD / Influencer",
                "iPhone 14 Pro", "2024 - Oficial", "Movimiento extremo / Puntería fija",
                185, 180, 175, 165, 75, 190, 46, null, "👻 Leyenda Mobile", "Perfiles oficiales @fantasmaip"
        ));

        players.add(new ProPlayer(
                "pro-3", "Diogo Lima", "Blackn444", "Los Grandes",
                "Android (POCO / ROG)", "2024 - Verificada", "Control de retroceso / MP40 y Escopetas",
                175, 170, 168, 155, 68, 180, 48, 560, "⚡ Maestro del Capa", "Directos y configuración compartida"
        ));

        players.add(new ProPlayer(
                "pro-4", "Angelo Roberto", "Two9", "Fluxo",
                "iPhone 13 Pro Max", "2023/2024", "Juego táctico y media distancia",
                168, 165, 160, 150, 70, 175, 50, null, "🎯 Francotirador", "Torneos competitivos"
        ));

        players.add(new ProPlayer(
                "pro-5", "Gabriel Henrique", "Bak", "Fluxo",
                "iPhone 15 Pro", "2024", "Capitán / Visión periférica",
                182, 178, 172, 160, 80, 195, 45, null, "⭐ Estratega", "Canal oficial"
        ));

        players.add(new ProPlayer(
                "pro-6", "Jonatha Silva", "Apelapato", "Mobile Pro",
                "Samsung Galaxy S24 Ultra", "2024", "Capas consecutivos a larga distancia",
                172, 168, 165, 154, 65, 170, 47, 520, "🔥 High Sense Android", "Configuración competitiva"
        ));
    }

    public List<ProPlayer> obtenerTodos() {
        return new ArrayList<>(players);
    }

    public Optional<ProPlayer> obtenerPorId(String id) {
        return players.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst();
    }
}
