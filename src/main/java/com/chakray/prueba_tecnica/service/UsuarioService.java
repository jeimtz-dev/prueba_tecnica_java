package com.chakray.prueba_tecnica.service;

import com.chakray.prueba_tecnica.entity.Address;
import com.chakray.prueba_tecnica.entity.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public UsuarioService() {
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address(1, "workaddres", "Street No.1", "UK");
        Address address2 = new Address(2, "homeaddress", "Street No.2", "AU");
        addresses.add(address1);
        addresses.add(address2);
        Usuario user1 = new Usuario(123, "user1@mail.com","user1", "7c4a8d09ca3762af61e59520943dc26494f8941b","01-01-2024 00:00:00", addresses);
        Usuario user2 = new Usuario(456, "user2@mail.com", "user2", "another_hash", "02-01-2024 00:00:00", addresses);
        Usuario user3 = new Usuario(789, "user3@mail.com", "user3", "yet_another_hash", "03-01-2024 00:00:00", new ArrayList<>());
        usuarios.add(user1);
        usuarios.add(user2);
        usuarios.add(user3);
    }


    public List<Usuario> getUsuariosSorted(String sortedBy) {
        if (sortedBy == null || sortedBy.isEmpty()) {
            return usuarios;
        }
        List<Usuario> usuariosCopia = new ArrayList<>(usuarios);
        Comparator<Usuario> comparator = null;
        switch (sortedBy.toLowerCase()) {
            case "email":
                comparator = Comparator.comparing(Usuario::getEmail, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "id":
                comparator = Comparator.comparing(Usuario::getId, Comparator.nullsLast(Long::compareTo));
                break;
            case "name":
                comparator = Comparator.comparing(Usuario::getName, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "created_at":
                comparator = Comparator.comparing(usuario -> {
                    try {
                        return LocalDateTime.parse(usuario.getCreated_at(), DATE_TIME_FORMATTER);
                    } catch (Exception e) {
                        return null;
                    }
                }, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            default:
                return usuariosCopia;
        }
        if (comparator != null) {
            usuariosCopia.sort(comparator);
        }
        return usuariosCopia;
    }

    public Usuario findUserById(Long user_id){
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == user_id) {
                return usuario;
            }
        }
        return null;
    }
    public List<Address> findAddressUserById(Long user_id){
        Usuario usuario = findUserById(user_id);
        if (usuario != null){
            return usuario.getAddresses();
        } else {
            return null;
        }
    }
    
    public Address updateUserAddress(Long user_id, Long address_id, Address updatedAddress) {
        Usuario usuario = findUserById(user_id);
        if(usuario == null){
            return null;
        }
        for (Address address : usuario.getAddresses()) {
            if(address.getId() == address_id){
                address.setName(updatedAddress.getName());
                address.setStreet(updatedAddress.getStreet());
                address.setCountry_code(updatedAddress.getCountry_code());
                return address;
            }
        }
        return null;
    }
}
