/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.UsuarioSeguridad;
import com.hiberlibros.HiberLibros.entities.Rol;
import com.hiberlibros.HiberLibros.repositories.UsuarioSeguridadRepository;
import com.hiberlibros.HiberLibros.repositories.RolRepository;
import com.hiberlibros.HiberLibros.services.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class SeguridadControlador {

    @Autowired
    private UsuarioSeguridadRepository repoUsuSeg;

    @Autowired
    private UsuarioService serviceUsuario;

    @Autowired
    private RolRepository repoRol;

    @Autowired
    private PasswordEncoder passEncoder;

    @GetMapping("/url1")    //Todo el mundo
    @ResponseBody
    public String saludar1() {
        return "saluda 1";
    }

    @GetMapping("/url2")    //Todo el validados
    @ResponseBody
    public String saludar2() {
        return "saluda 2";
    }

    @GetMapping("/url3")    //Todo el adminstradores Admin.
    @ResponseBody
    public String saludar3() {
        return "saluda 3";
    }

    @GetMapping("/login2")
    public String login() {
        return "/admin/milogin";
    }

    @GetMapping("/altaUsuarioSeguridad")
    public String altaUsuario() {
        return "/admin/adminGestion";
    }

    @PostMapping("/altaUsuarioSeguridad")
    public String altaUsuarioPost(Model m, Integer autoIncremental, String mail, Integer idUsuario, String password, String nombre_rol) {
        Optional<UsuarioSeguridad> usuAplic = repoUsuSeg.findByIdUsuario(idUsuario);
        if (usuAplic.isPresent()) {
            List<Rol> roles = usuAplic.get().getRoles();
            List<Rol> rolesFiltrados = roles.stream().filter(x -> x.getNombre_rol().equals(nombre_rol)).collect(Collectors.toList());
            if (rolesFiltrados.size() > 0) {
                m.addAttribute("errMensaje", "alta no realizada: usuario con este Rol activado previamente");
                return "/admin/adminGestion";
            }
        }

        UsuarioSeguridad u = new UsuarioSeguridad();
        u.setIdUsuario(idUsuario);
        u.setMail(mail);
        u.setPassword(passEncoder.encode(password));
        repoUsuSeg.save(u);

        Rol r = new Rol();
        r.setIdUsuario(u);
        r.setNombre_rol(nombre_rol);
        repoRol.save(r);

        return "redirect:/admin/altaUsuarioSeguridad";

    }

    @GetMapping("/info")
    @ResponseBody
    public String info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final StringBuffer res = new StringBuffer();
        authentication.getAuthorities().forEach(x -> {
            res.append("," + x.getAuthority());
        });
        return authentication.getName() + res.toString();
    }
    
    @GetMapping("/logout")
    public String logout(){

        return "logout";
    }
}
