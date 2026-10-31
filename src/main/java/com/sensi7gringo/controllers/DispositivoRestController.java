package com.sensi7gringo.controllers;

import com.sensi7gringo.dto.ApiResponse;
import com.sensi7gringo.models.Dispositivo;
import com.sensi7gringo.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
@CrossOrigin(origins = "*")
public class DispositivoRestController {

    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("/marcas")
    public ResponseEntity<ApiResponse<List<String>>> obtenerMarcas(
            @RequestParam(required = false) String sistemaOperativo) {
        List<String> marcas = dispositivoService.obtenerMarcasPorSistema(sistemaOperativo);
        return ResponseEntity.ok(ApiResponse.ok(marcas, "Marcas obtenidas exitosamente"));
    }

    @GetMapping("/modelos/{marca}")
    public ResponseEntity<ApiResponse<List<Dispositivo>>> obtenerModelos(
            @PathVariable String marca) {
        List<Dispositivo> modelos = dispositivoService.obtenerModelosPorMarca(marca);
        return ResponseEntity.ok(ApiResponse.ok(modelos, "Modelos obtenidos para la marca " + marca));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Dispositivo>>> buscarDispositivos(
            @RequestParam(required = false) String q) {
        List<Dispositivo> resultados = dispositivoService.buscarDispositivos(q);
        return ResponseEntity.ok(ApiResponse.ok(resultados, "Resultados de búsqueda"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Dispositivo>>> obtenerTodos() {
        List<Dispositivo> todos = dispositivoService.obtenerTodosDispositivos();
        return ResponseEntity.ok(ApiResponse.ok(todos, "Listado completo de dispositivos"));
    }
}
