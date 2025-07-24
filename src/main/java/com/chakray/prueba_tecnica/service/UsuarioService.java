package com.chakray.prueba_tecnica.service;


import com.chakray.prueba_tecnica.response.UserResponse;
import com.chakray.prueba_tecnica.entity.Address;
import com.chakray.prueba_tecnica.entity.Usuario;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private Long nextUserId = 123L;
    private static final Long ID_INCREMENT = 123L;


    public UsuarioService() {
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address(1, "workaddres", "Street No.1", "UK");
        Address address2 = new Address(2, "homeaddress", "Street No.2", "AU");
        addresses.add(address1);
        addresses.add(address2);
        Usuario user1 = new Usuario(123, "user1@mail.com","user1", "7c4a8d09ca3762af61e59520943dc26494f8941b","01-01-2024 00:00:00", addresses);
        //Usuario user2 = new Usuario(456, "user2@mail.com", "user2", "another_hash", "02-01-2024 00:00:00", addresses);
        //Usuario user3 = new Usuario(789, "user3@mail.com", "user3", "yet_another_hash", "03-01-2024 00:00:00", new ArrayList<>());
        usuarios.add(user1);
        nextUserId += ID_INCREMENT;
        //usuarios.add(user2);
        //usuarios.add(user3);
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


    public UserResponse createNewUser(Usuario user){
        if(user == null || user.getName() == null || user.getEmail() == null || user.getPassword() == null){
            return null;
        }
        user.setId(nextUserId);
        nextUserId += ID_INCREMENT;
        user.setCreated_at(getUKCurrentTimestamp());
        user.setPassword(hashPassword(user.getPassword()));
        if (user.getAddresses() == null) {
            user.setAddresses(new ArrayList<>());
        } else {
            for(int i=0; i<user.getAddresses().size(); i++){
                user.getAddresses().get(i).setId(i+1);
            }
        }
        usuarios.add(user);
        UserResponse userDto = new UserResponse();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCreated_at(user.getCreated_at());
        userDto.setAddresses(user.getAddresses());
        return userDto;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encrptar", e);
        }
    }

    private String getUKCurrentTimestamp() {
        ZonedDateTime nowUK = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        return nowUK.format(DATE_TIME_FORMATTER);
    }

    public UserResponse updateUSer(Long user_id, Usuario updateUser){
        Usuario user = findUserById(user_id);
        if(user == null){
            return null;
        }

        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(hashPassword(updateUser.getPassword()));
        user.setCreated_at(getUKCurrentTimestamp());

        if (user.getAddresses() == null) {
            user.setAddresses(new ArrayList<>());
        } else {
            user.setAddresses(updateUser.getAddresses());
            for(int i=0; i<user.getAddresses().size(); i++){
                user.getAddresses().get(i).setId(i+1);
            }
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setAddresses(user.getAddresses());

        return userResponse;
    }


    public String deleteUser(Long user_id){
        Usuario usuario = findUserById(user_id);
        if(usuario == null){
            return null;
        }
        usuarios.remove(usuario);
        return "Eliminado correctamente";
    }
}
