package com.chakray.prueba_tecnica.service;

import com.chakray.prueba_tecnica.entity.Address;
import com.chakray.prueba_tecnica.entity.Usuario;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address(1, "workaddres", "Street No.1", "UK");
        Address address2 = new Address(2, "homeaddress", "Street No.2", "AU");
        addresses.add(address1);
        addresses.add(address2);
        Usuario user = new Usuario(123, "user1@mail.com","user1", "7c4a8d09ca3762af61e59520943dc26494f8941b","01-01-2024 00:00:00", addresses);
        usuarios.add(user);
    }


    public List<Usuario> getUsuarios(){
        return new ArrayList<>(usuarios);
    }
}
