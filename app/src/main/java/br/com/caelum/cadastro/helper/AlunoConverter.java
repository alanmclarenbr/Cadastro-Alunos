package br.com.caelum.cadastro.helper;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Class Created by android6196 on 12/11/16.
 */
public class AlunoConverter {

    public String toJSON(List<Aluno> alunos){
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object().key("list").array()
                    .object().key("aluno").array();

            for(Aluno aluno : alunos){
                jsonStringer.object()
                        .key("id").value(aluno.getId())
                        .key("nome").value(aluno.getNome())
                        .key("telefone").value(aluno.getTelefone())
                        .key("endereco").value(aluno.getEndereco())
                        .key("site").value(aluno.getSite())
                        .key("nota").value(aluno.getNota())
                .endObject();
            }

            return jsonStringer.endArray().endObject().endArray().endObject().toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}