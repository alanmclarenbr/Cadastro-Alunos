package br.com.caelum.cadastro.modelo;

import java.io.Serializable;

/**
 * Class Created by android6196 on 22/10/16.
 */
public class Aluno implements Serializable{

    private static final long serialVersionUID = -3433857247122086996L;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private Double nota;
    private Long id;
    private String caminhoFoto;

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Aluno) {
            if (((Aluno) o).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
