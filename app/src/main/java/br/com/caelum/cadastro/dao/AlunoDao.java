package br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6196 on 22/10/16.
 */
public class AlunoDao extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Alunos";
    public static final String SQL_DROP_TABLE_ALUNO = "DROP TABLE IF EXISTS " + TABELA;
    private static final String DATABASE = "CadastroCaelum";

    public static final String SQL_CREATE_TABLE_ALUNO = "CREATE TABLE "+ TABELA
            + " ( id INTEGER PRIMARY KEY, "
            + "nome TEXT NOT NULL, telefone TEXT, "
            + "endereco TEXT, site TEXT, nota REAL);";

    public AlunoDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ALUNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_ALUNO);
        onCreate(db);
    }

    public void insert(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());

        getWritableDatabase().insert(TABELA, null, values);
    }
}
