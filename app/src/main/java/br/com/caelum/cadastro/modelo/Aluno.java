package br.com.caelum.cadastro.modelo;

/**
 * Created by android6196 on 22/10/16.
 */
public class Aluno {
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private Double nota;
    private Long id;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getSite() {
        return site;
    }

    public Double getNota() {
        return nota;
    }

    public Long getId() {
        return id;
    }
}