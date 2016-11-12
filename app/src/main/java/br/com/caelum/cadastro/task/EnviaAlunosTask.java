package br.com.caelum.cadastro.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.helper.AlunoConverter;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Class Created by android6196 on 12/11/16.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    public Context ctx;
    private ProgressDialog progress;
    private String endereco = "https://www.caelum.com.br/mobile";

    public EnviaAlunosTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute(){
        progress = ProgressDialog.show(ctx,"Aguarde...","Envio de dados para a web",true,true);
    }

    @Override
    protected String doInBackground(Object... params){
        AlunoDao alunoDao = new AlunoDao(ctx);
        alunoDao.close();
        List<Aluno> alunos = alunoDao.getAll();
        String json = new AlunoConverter().toJSON(alunos);
        WebClient client = new WebClient();
        return client.post(json);
    }

    @Override
    protected void onPostExecute(String result){
        progress.dismiss();
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }


}
