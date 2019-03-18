package br.com.dmain.dmain.dao;

import br.com.dmain.dmain.model.Fonte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonteRepository extends JpaRepository<Fonte, String> {

}

