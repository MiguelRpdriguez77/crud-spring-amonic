package com.adso.crud_mysql.service;

import com.adso.crud_mysql.model.User;
import com.adso.crud_mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarTodos() {
        return (List<User>) userRepository.findAll();
    }

    public void guardar(User usuario) {
        if (usuario.getId() != null) {
            User usuarioExistente = userRepository.findById(usuario.getId()).orElse(null);
            
            if (usuarioExistente != null) {
                usuarioExistente.setFirstname(usuario.getFirstname());
                usuarioExistente.setLastname(usuario.getLastname());
                usuarioExistente.setEmail(usuario.getEmail());
                userRepository.save(usuarioExistente);
            }
        } else {
            usuario.setActive(true);
            usuario.setPassword("12345"); 
            userRepository.save(usuario);
        }
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}