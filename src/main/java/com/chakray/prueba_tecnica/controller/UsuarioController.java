package com.chakray.prueba_tecnica.controller;


import com.chakray.prueba_tecnica.entity.Usuario;
import com.chakray.prueba_tecnica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> usuarios(){
        return ResponseEntity.ok(this.usuarioService.getUsuarios());
    }
}
