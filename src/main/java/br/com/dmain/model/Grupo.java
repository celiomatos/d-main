package br.com.dmain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "grupo", schema = "main")
public class Grupo implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gru_id", nullable = false)
    private Long id;

    @Column(name = "gru_nome", nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo", fetch = FetchType.LAZY)
    private Set<PessoaGrupo> pessoaGrupoSet;
}
