package com.chakray.prueba_tecnica.controller;


import com.chakray.prueba_tecnica.entity.Address;
import com.chakray.prueba_tecnica.entity.Usuario;
import com.chakray.prueba_tecnica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Usuario>> usersSorted(@RequestParam(value = "sortedBy", required = false) String sortedBy){
        return ResponseEntity.ok(this.usuarioService.getUsuariosSorted(sortedBy));
    }

    @GetMapping("/users/{user_id}/addresses")
    public ResponseEntity<List<Address>> addressUserById(@PathVariable Long user_id){
        List<Address> addresses = this.usuarioService.getAddressUsserById(user_id);
        if (addresses != null) {
            return ResponseEntity.ok(addresses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
