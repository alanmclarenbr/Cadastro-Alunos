package br.com.caelum.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.helper.FormularioHelper;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private static final String ALUNO_SELECIONADO = "alunoSelecionado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);
        Aluno aluno = (Aluno) getIntent().getSerializableExtra(ALUNO_SELECIONADO);
        if(aluno!=null){
            helper.colocaNoFormulario(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDao alunoDao = new AlunoDao(this);

                if(aluno.getId()==null){
                    alunoDao.insert(aluno);
                }else{
                    alunoDao.update(aluno);
                }
                alunoDao.close();
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
