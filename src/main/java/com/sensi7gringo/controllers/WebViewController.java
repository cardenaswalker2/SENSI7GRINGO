package com.sensi7gringo.controllers;

import com.sensi7gringo.models.ProPlayer;
import com.sensi7gringo.services.DispositivoService;
import com.sensi7gringo.services.ProPlayerService;
import com.sensi7gringo.services.SensibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebViewController {

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private ProPlayerService proPlayerService;

    @Autowired
    private SensibilidadService sensibilidadService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("brandName", "Sensi7gringo");
        model.addAttribute("slogan", "Domina el Campo con Sensibilidad Milimétrica");
        model.addAttribute("proPlayers", proPlayerService.obtenerTodos());
        model.addAttribute("dispositivosDestacados", dispositivoService.obtenerTodosDispositivos());
        return "index";
    }
}
