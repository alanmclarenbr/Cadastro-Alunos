package br.com.caelum.cadastro;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.helper.AlunoConverter;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.modelo.Permissao;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.cadastro.task.EnviaAlunosTask;

public class ListaAlunosActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private List<Aluno> alunos;
    private ListView listaAlunos;
    ListaAlunosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Permissao.fazPermissao(this);

        AlunoDao dao = new AlunoDao(this);

        alunos = dao.getAll();
        dao.close();

        adapter = new ListaAlunosAdapter(this, alunos);
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setAdapter(adapter);

        Intent intent = getIntent();

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent edicao = new Intent(
                        ListaAlunosActivity.this,
                        FormularioActivity.class
                );
                edicao.putExtra("alunoSelecionado", (Aluno) listaAlunos.getItemAtPosition(position));

                startActivity(edicao);
            }
        });

        /*
        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                Toast.makeText(ListaAlunosActivity.this, "Clique Longo: " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        */

        Button botaoAdiciona = (Button) findViewById(R.id.lista_alunos_floating_button);
        botaoAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListaAlunos Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.caelum.cadastro/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListaAlunos Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.caelum.cadastro/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista();
    }

    private void carregaLista(){
        AlunoDao dao = new AlunoDao(this);

        List<Aluno> alunos = dao.getAll();
        dao.close();

        adapter = new ListaAlunosAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem ligar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_VIEW);
        intentLigar.setData(Uri.parse("tel:"+ alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        MenuItem sms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
        intentSms.putExtra("sms_body", "Mensagem");
        sms.setIntent(intentSms);

        MenuItem maps = menu.add("Achar no mapa");
        Intent intentMaps = new Intent(Intent.ACTION_VIEW);
        intentMaps.setData(Uri.parse("geo:0,0?z=14&q="+ alunoSelecionado.getEndereco()));
        maps.setIntent(intentMaps);

        MenuItem site = menu.add("Navegar no site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        if(!alunoSelecionado.getSite().startsWith("http://")){
            alunoSelecionado.setSite("http://"+alunoSelecionado.getSite());
        }
        intentSite.setData(Uri.parse(alunoSelecionado.getSite()));
        site.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(ListaAlunosActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
                                        dao.delete(alunoSelecionado);
                                        dao.close();
                                        carregaLista();
                                    }
                                })
                        .setNegativeButton("Náo", null).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlunoDao alunoDao = new AlunoDao(this);
        try {
            switch (item.getItemId()){
                case R.id.menu_enviar_notas:
                    /*List<Aluno> alunos = alunoDao.getAll();

                    String json = new AlunoConverter().toJSON(alunos);
                    WebClient client = new WebClient();
                    String resposta = client.post(json);

                    Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
                    return true;*/

                    new EnviaAlunosTask(this).execute();
                    return true;
                case R.id.menu_receber_provas:
                    Intent provas = new Intent(this, ProvasActivity.class);
                    startActivity(provas);
                    return true;
                case R.id.menu_mapa:
                    Intent mapas = new Intent(this, MostraAlunosActivity.class);
                    startActivity(mapas);
                    return true;
            }
        } finally {
            alunoDao.close();
        }
        return super.onOptionsItemSelected(item);
    }


}
