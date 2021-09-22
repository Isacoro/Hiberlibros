
package com.hiberlibros.HiberLibros.repositories;

import com.hiberlibros.HiberLibros.entities.Preferencia;
import com.hiberlibros.HiberLibros.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isabel
 */
@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {
    
    //Todo el listado de Preferencias
    List<Preferencia> findAll();
    
    //Listado de preferencias por usuario
    List<Preferencia> findByUsuario(Usuario idUsuario);
    
    
}