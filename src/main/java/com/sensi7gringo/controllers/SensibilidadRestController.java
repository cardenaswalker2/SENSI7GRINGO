package com.sensi7gringo.controllers;

import com.sensi7gringo.dto.ApiResponse;
import com.sensi7gringo.dto.GenerarSensibilidadRequest;
import com.sensi7gringo.models.Sensibilidad;
import com.sensi7gringo.services.SensibilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensibilidades")
@CrossOrigin(origins = "*")
public class SensibilidadRestController {

    @Autowired
    private SensibilidadService sensibilidadService;

    @PostMapping("/generar")
    public ResponseEntity<ApiResponse<Sensibilidad>> generarSensibilidad(
            @Valid @RequestBody GenerarSensibilidadRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> 
                errors.put(err.getField(), err.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(
                    ApiResponse.validationError("Datos de entrada inválidos", errors)
            );
        }

        Sensibilidad s = sensibilidadService.generarSensibilidad(request);
        return ResponseEntity.ok(ApiResponse.ok(s, "Configuración calibrada exitosamente para Free Fire"));
    }

    @PostMapping("/guardar")
    public ResponseEntity<ApiResponse<Sensibilidad>> guardarSensibilidad(
            @RequestBody Sensibilidad sensibilidad) {
        Sensibilidad guardada = sensibilidadService.guardar(sensibilidad);
        return ResponseEntity.ok(ApiResponse.ok(guardada, "Sensibilidad guardada correctamente"));
    }

    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<Sensibilidad>>> obtenerHistorial(
            @RequestParam(required = false, defaultValue = "default-session") String sessionId) {
        List<Sensibilidad> historial = sensibilidadService.obtenerHistorial(sessionId);
        return ResponseEntity.ok(ApiResponse.ok(historial, "Historial de sensibilidades"));
    }

    @GetMapping("/ranking")
    public ResponseEntity<ApiResponse<List<Sensibilidad>>> obtenerRanking() {
        List<Sensibilidad> ranking = sensibilidadService.obtenerRanking();
        return ResponseEntity.ok(ApiResponse.ok(ranking, "Ranking de sensibilidades"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sensibilidad>> obtenerPorId(@PathVariable String id) {
        Optional<Sensibilidad> opt = sensibilidadService.obtenerPorId(id);
        return opt.map(sensibilidad -> ResponseEntity.ok(ApiResponse.ok(sensibilidad, "Sensibilidad encontrada")))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.error("Sensibilidad no encontrada con id: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> eliminarSensibilidad(@PathVariable String id) {
        boolean ok = sensibilidadService.eliminar(id);
        if (ok) {
            return ResponseEntity.ok(ApiResponse.ok(true, "Sensibilidad eliminada con éxito"));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.error("No se encontró la sensibilidad para eliminar"));
        }
    }

    @PostMapping("/{id}/favorito")
    public ResponseEntity<ApiResponse<Boolean>> alternarFavorito(@PathVariable String id) {
        boolean fav = sensibilidadService.alternarFavorito(id);
        return ResponseEntity.ok(ApiResponse.ok(fav, fav ? "Agregado a favoritos" : "Removido de favoritos"));
    }
}
