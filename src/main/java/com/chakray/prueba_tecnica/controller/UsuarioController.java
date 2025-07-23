package com.chakray.prueba_tecnica.controller;

import com.chakray.prueba_tecnica.response.UserResponse;
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
        List<Address> addresses = this.usuarioService.findAddressUserById(user_id);
        if (addresses != null) {
            return ResponseEntity.ok(addresses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{user_id}/addresses/{address_id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long user_id, @PathVariable Long address_id, @RequestBody Address updatedAddress) {
        Address address = this.usuarioService.updateUserAddress(user_id, address_id, updatedAddress);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody Usuario user) {
        UserResponse usuario = this.usuarioService.createNewUser(user);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long user_id, @RequestBody Usuario user) {
        UserResponse usuario = this.usuarioService.updateUSer(user_id, user);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
