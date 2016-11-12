package br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6196 on 22/10/16.
 */
public class AlunoDao extends SQLiteOpenHelper {

    private static final int VERSAO = 4;
    private static final String TABELA = "Alunos";
    public static final String SQL_DROP_TABLE_ALUNO = "DROP TABLE IF EXISTS " + TABELA;
    private static final String DATABASE = "CadastroCaelum";

    public static final String SQL_CREATE_TABLE_ALUNO = "CREATE TABLE "+ TABELA
            + " ( id INTEGER PRIMARY KEY, "
            + "nome TEXT NOT NULL, telefone TEXT, "
            + "endereco TEXT, site TEXT, nota REAL, caminhoFoto TEXTa);";

    public AlunoDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ALUNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DROP_TABLE_ALUNO);
        db.execSQL("ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;");
    }

    public void insert(Aluno aluno){
        ContentValues values = preencherContentValues(aluno);

        getWritableDatabase().insert(TABELA, null, values);
    }

    @NonNull
    private ContentValues preencherContentValues(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("telefone", aluno.getTelefone());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        return values;
    }

    public List<Aluno> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
             cursor = db.rawQuery("SELECT * FROM " + TABELA + ";", null);

            ArrayList<Aluno> alunos = new ArrayList<>();

            while (cursor.moveToNext()){
                Aluno aluno = new Aluno();

                aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
                aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
                aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
                aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

                alunos.add(aluno);
            }

            return alunos;
        } finally {
            cursor.close();
        }
    }

    public void delete(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {aluno.getId().toString()};

        db.delete(TABELA, "id=?", args);
    }

    public void update(Aluno aluno){
        ContentValues values = preencherContentValues(aluno);

        String[] args = {aluno.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", args);
    }

    public boolean isAluno(String telefone){
        String[] parametros = {telefone};

        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT TELEFONE FROM " + TABELA +
            " WHERE TELEFONE like %?%", parametros);
        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }
}
