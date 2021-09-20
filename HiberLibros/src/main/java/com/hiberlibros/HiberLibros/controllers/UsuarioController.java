/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioSeguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;
    
//    @Autowired
//    private UsuarioSeguridad serviceUsuarioSeguridad;

    @GetMapping
    public String usuarioFormulario(Model m, String registro) { //devuelve una lista con todos los usuarios, parte administrador
        m.addAttribute("registro", registro);
        m.addAttribute("usuarios", service.usuariosList());
        return "/usuarios/usuariosFormulario";
    }

    @PostMapping("/guardarUsuario")//guarda un usuario devuelve un mensaje de error concreto
    public String usuarioRegistrar(Usuario u) {
        String resultado = service.guardarUsuario(u);
        if (resultado.contains("Error")) {
            return "redirect:/hiberlibros?error=" + resultado;//mail existente, mail no válido
        } else {
            return "redirect:/hiberlibros/panelUsuario?mail=" + u.getMail();//va al panel de usuario con el mail
        }

    }

    @PostMapping("/editarUsuario")//edita usuario, manda el usuario para rellenar el formulario
    public String usuarioEditar(Usuario u) {
        return "redirect:/hiberlibros/panelUsuario?mail=" + service.editarUsuario(u);
    }

    @GetMapping("/borrar")
    public String borrar(Integer id) {//borra usuario por ID en administrador
        service.borrarUsuario(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/borrarUsuario")//borra usuario por ID en HIBERLIBRO
    public String borrarUsuario(Integer id) {
        service.borrarUsuario(id);
        return "redirect:/hiberlibros";
    }

}
