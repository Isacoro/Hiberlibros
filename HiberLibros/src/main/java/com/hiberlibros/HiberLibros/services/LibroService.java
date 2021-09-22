/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.repositories.LibroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;

/**
 *
 * @author Usuario
 */
@Service
public class LibroService implements ILibroService {

    @Autowired
    private LibroRepository libroRep;

    @Override
    public List<Libro> buscarLibro(String libro) {//recibe un string y busca si hay coincidencias en isbn o libro
        return libroRep.findByIsbnContainsOrTituloContainsIgnoreCase(libro, libro);
    }

    @Override
    public Libro libroId(Integer id) {
        return libroRep.findById(id).get();
    }

    @Override
    public void guardarLibro(Libro l) {
        libroRep.save(l);
    }
<<<<<<< HEAD
    
    public Integer contarLibros() {
        long numLibros = libroRep.findAll().stream()
                               .count();
        return (int)(numLibros);
    }

    @Override
    public List<Libro> encontrarPorAutor(Autor a) {
        return libroRep.findByAutor(a);
    }

    
=======

    @Override
    public void valorarLibro(Libro l, Integer valoracion) {
        l.setNumeroValoraciones(l.getNumeroValoraciones() + 1);
        double operacion = (l.getValoracionLibro() * (l.getNumeroValoraciones() - 1) + valoracion) / l.getNumeroValoraciones();
        double redondeo = Math.round(operacion * 100) / 100.0;
        l.setValoracionLibro(redondeo);
        guardarLibro(l);
    }

>>>>>>> origin/master
}
 