package com.adso.crud_mysql.controller;

import com.adso.crud_mysql.model.User;
import com.adso.crud_mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        // userRepository.findAll() va a la base de datos y trae todos los registros mágicamente
        List<User> listaUsuarios = userRepository.findAll();

        // Empaquetamos esa lista con el nombre "usuarios" para enviarla al HTML
        model.addAttribute("usuarios", listaUsuarios);

        // Retornamos el nombre de la vista HTML que vamos a crear
        return "usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        // Aquí creamos un usuario "en blanco" y se lo enviamos a la vista
        // Thymeleaf usará este objeto vacío para enlazar los datos que escribas
        model.addAttribute("usuario", new User());
        return "nuevo_usuario";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute User usuario) {

        if (usuario.getId() != null) {
            // MODO EDICIÓN: El usuario ya existe porque tiene un ID
            User usuarioExistente = userRepository.findById(usuario.getId()).orElse(null);

            if (usuarioExistente != null) {
                // Actualizamos solo los datos que vienen del formulario
                usuarioExistente.setFirstname(usuario.getFirstname());
                usuarioExistente.setLastname(usuario.getLastname());
                usuarioExistente.setEmail(usuario.getEmail());

                // Guardamos el usuario original (que mantiene intacta su contraseña y rol)
                userRepository.save(usuarioExistente);
            }
        } else {
            // MODO CREACIÓN: No tiene ID, es un usuario totalmente nuevo
            usuario.setActive(true);
            // Le ponemos una contraseña por defecto para cumplir con la regla de MySQL
            usuario.setPassword("12345");

            userRepository.save(usuario);
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        // Buscamos el ID en la base de datos y lo borramos con una sola línea
        userRepository.deleteById(id);

        // Recargamos la tabla
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Integer id, Model model) {
        // Buscamos al usuario por su ID. Si no existe, devuelve nulo.
        User usuarioExistente = userRepository.findById(id).orElse(null);

        // Enviamos ese usuario lleno de datos al mismo formulario que usamos para crear
        model.addAttribute("usuario", usuarioExistente);

        // Reutilizamos la misma vista HTML
        return "nuevo_usuario";
    }
}
