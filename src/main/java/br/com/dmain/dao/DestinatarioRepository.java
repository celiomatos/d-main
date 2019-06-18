package br.com.dmain.dao;

import br.com.dmain.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

    List<Destinatario> findByGrupo(String grupo);
}
