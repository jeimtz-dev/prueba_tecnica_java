package com.chakray.prueba_tecnica.controller;

import com.chakray.prueba_tecnica.response.UserResponse;
import com.chakray.prueba_tecnica.entity.Address;
import com.chakray.prueba_tecnica.entity.Usuario;
import com.chakray.prueba_tecnica.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Prueba técnica")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users")
    @Operation(summary = "Obtener usuarios", description = "Obtiene la lista de los usuarios ordenados por email | id | name | created_at.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios ordenados")
    public ResponseEntity<List<Usuario>> usersSorted(@RequestParam(value = "sortedBy", required = false) String sortedBy){
        return ResponseEntity.ok(this.usuarioService.getUsuariosSorted(sortedBy));
    }

    @GetMapping("/users/{user_id}/addresses")
    @Operation(summary = "Obtener direcciones por id de un usuario", description = "Obtiene la lista de direcciones de un usuario por su id del usuario.")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones del usuario")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<List<Address>> addressUserById(@PathVariable Long user_id){
        List<Address> addresses = this.usuarioService.findAddressUserById(user_id);
        if (addresses != null) {
            return ResponseEntity.ok(addresses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{user_id}/addresses/{address_id}")
    @Operation(summary = "Actualiza una direcciones", description = "Actualiza una dirección por su campo id del usuario y id de la dirección.")
    @ApiResponse(responseCode = "200", description = "Dirección actualizada")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    public ResponseEntity<Address> updateAddress(@PathVariable Long user_id, @PathVariable Long address_id, @RequestBody Address updatedAddress) {
        Address address = this.usuarioService.updateUserAddress(user_id, address_id, updatedAddress);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    @Operation(summary = "Crea un nuevo usuario", description = "Crea un nuevo usuario")
    @ApiResponse(responseCode = "200", description = "Usuario creado con éxito")
    @ApiResponse(responseCode = "400", description = "Parámetros incorrectos")
    public ResponseEntity<UserResponse> createUser(@RequestBody Usuario user) {
        UserResponse usuario = this.usuarioService.createNewUser(user);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/users/{id}")
    @Operation(summary = "Actualiza un usuario", description = "Actualiza un usuario por su campo id")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Parámetros incorrectos")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody Usuario user) {
        UserResponse usuario = this.usuarioService.updateUSer(id, user);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Elimina un usuario", description = "Elimina un usuario por su campo id")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado con éxito")
    @ApiResponse(responseCode = "400", description = "Parámetros incorrectos")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String response = this.usuarioService.deleteUser(id);
        if (response != null){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
