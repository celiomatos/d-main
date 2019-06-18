package br.com.dmain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pessoa", schema = "main")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pes_id", nullable = false)
    private Long id;

    @Column(name = "pes_tipo", nullable = false)
    private short tipo;

    @Column(name = "pes_sexo", nullable = false)
    private String sexo;

    @Column(name = "pes_nome", nullable = false)
    private String nome;

    @Column(name = "pes_apelido", nullable = false)
    private String apelido;

    @Column(name = "pes_nome_pesquisa", nullable = false)
    private String nomePesquisa;

    @Column(name = "pes_cpf")
    private String cpf;

    @Column(name = "pes_naturalidade")
    private String naturalidade;

    @Column(name = "pes_nacionalidade")
    private String nacionalidade;

    @Column(name = "pes_estado_civil")
    private Short estadoCivil;

    @Column(name = "pes_escolaridade")
    private Short escolaridade;

    @Column(name = "pes_profissao")
    private String profissao;

    @Column(name = "pes_nascimento")
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Column(name = "pes_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cadastro;

    @Column(name = "pes_email")
    private String email;

    @Column(name = "pes_celular")
    private String celular;

    @Column(name = "pes_cep")
    private String cep;

    @Column(name = "pes_uf")
    private String uf;

    @Column(name = "pes_municipio")
    private String municipio;

    @Column(name = "pes_bairro")
    private String bairro;

    @Column(name = "pes_logradouro")
    private String logradouro;

    @Column(name = "pes_numero")
    private String numero;

    @Column(name = "pes_grupo")
    private String grupo;

    @Column(name = "pes_cargo")
    private String cargo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    private Set<PessoaContato> pessoaContatoSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    private Set<PessoaEndereco> pessoaEnderecoSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    private Set<PessoaGrupo> pessoaGrupoSet;
}
