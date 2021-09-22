package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import com.hiberlibros.HiberLibros.repositories.GeneroRepository;
import com.hiberlibros.HiberLibros.repositories.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.services.PreferenciaService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Isabel
 */
@Controller
@RequestMapping("preferencia")
public class PreferenciaController {

    @Autowired
    private PreferenciaService prefService;
    @Autowired
    private PreferenciaRepository prefRepo;
    @Autowired
    private GeneroRepository genRepo;
    @Autowired
    private IUsuarioService usuServ;
    @Autowired
    private ISeguridadService serviceSeguridad;

    @GetMapping
    public String verPreferencias(Model model) {
        Usuario u = usuServ.usuarioRegistrado(serviceSeguridad.getMailFromContext());
        model.addAttribute("preferencias", prefService.findByUsuario(u));
        model.addAttribute("generos", genRepo.findAll());;
        model.addAttribute("formulario", new Preferencia());

        return "/preferencias/preferencia";
    }

    @PostMapping("/guardar")
    public String anadirPreferencia(Integer id_genero) {
        
        Usuario u = usuServ.usuarioRegistrado(serviceSeguridad.getMailFromContext());
        Genero gen = genRepo.findById(id_genero).get();
        Preferencia pref = new Preferencia();
        pref.setGenero(gen);
        pref.setUsuario(u);
       
        prefService.addPreferencia(pref);

        return "redirect:/preferencia";
    }

    @GetMapping("/borrar/{id}")
    public String borrarPreferencia(@PathVariable Integer id) {
        
        
        prefService.borrarPreferencia(id);

        return "redirect:/preferencia";
    }
}
