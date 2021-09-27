package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.repositories.ForoLibroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ForoLibroService implements IForoLibroService {

    @Autowired
    private ForoLibroRepository repoForoLibro;

    @Override
    public List<ForoLibro> recuperarForosDeLibro(Libro idLibro) {
        return repoForoLibro.findByIdLibroAndDesactivado(idLibro,Boolean.FALSE);
    }

    @Override
    public List<ForoLibro> recuperarTodosLosForos() {
        return repoForoLibro.findByDesactivado(Boolean.FALSE);
    }

    @Override
    public void altaForoLibro(ForoLibro l) {
        repoForoLibro.save(l);
    }

    @Override
    public void bajaForoLibro(Integer id) {
            repoForoLibro.deleteById(id);
    }
    
}
