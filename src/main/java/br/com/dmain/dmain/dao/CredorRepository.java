package br.com.dmain.dmain.dao;

import br.com.dmain.dmain.model.Credor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredorRepository extends JpaRepository<Credor, Long> {

}
