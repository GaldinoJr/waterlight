package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

/**
 * Created by Galdino on 29/08/2015.
 */
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper {
    private int i;
    private String sqlCriarTabela;
    private String[] colunas;
    //
    private String nm_tabela;
    // Database Version
    private static final int DATABASE_VERSION = 2;
    public SQL(Context context, String nomeBase, String nm_tabela, String[] colunas, String sqlCriarTabela) {
        super(context, nomeBase, null, DATABASE_VERSION);
        //onUpgrade();
        this.nm_tabela = nm_tabela;
        this.colunas = new String[colunas.length];
        this.colunas = colunas;
        this.sqlCriarTabela = sqlCriarTabela;
        try {
            SQLiteDatabase db = getReadableDatabase();
            onCreate(db); // sempre colocar IF NOT EXISTS na criação da tabela
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // cria a tabela
        db.execSQL(sqlCriarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS livro");

        // create fresh books table
        this.onCreate(db);
    }

    // Adicionar um registro no banco
    public void addRegistro(Map<String, String> map){
        //for logging
        //Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        for(i = 0; i <map.size();i++) //roda todo o mapa
        {
            values.put(colunas[i], (String) map.get(colunas[i])); // pega os conteudos
        }


        // 3. insert
        db.insert(nm_tabela, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Buscar um registro
    public Map<String, String> buscarRegistro(String colunaID, String sId, String[] colunasBusca)
    {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        // 2. build query
        Cursor cursor =
                db.query(nm_tabela, // a. table
                        colunasBusca, // b. column names
                        colunaID + " = ?", // c. selections
                        new String[]{sId}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                return null;
            }
        }
        Map<String, String>mapRegistro = new HashMap<String, String>();

        for(i = 0;i < cursor.getColumnCount(); i++) // passa pelas colunas da tabela
            mapRegistro.put(colunasBusca[i], cursor.getString(i)); // add o coteudo junto com as chaves corespondentes
        //log
        //Log.d("getBook("+id+")", entidade.toString());

        // 5. return book
        return mapRegistro;
    }


    // Deletar um registro
    public void deletarRegistro(String sId, String colunaID) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(nm_tabela, //table name
                colunaID +" = ?",  // selections
                new String[] {sId }); //selections args

        // 3. close
        db.close();

        //log
        //Log.d("deleteBook", book.toString());
        // Log.d("deleteBook", String.valueOf(id)); trazendo só o id do livro da certo, porém o log fica sem a informação completa do livro, tem apenas o id
        //Log.d("deleteBook", book.toString()); para o log esse caso é melhor pois guarda em log as informações do livro que foi apagado
    }

    // Fazer update
    public int alterarRegistro(Map<String, String> map, String ColunaID, String sId) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        for(i = 0; i <map.size();i++) //roda todo o mapa
        {
            values.put(colunas[i], (String) map.get(colunas[i])); // pega os conteudos
        }


        // 3. updating row
        int retorno = db.update(nm_tabela, //table
                values, // column/value
                ColunaID+" = ?", // selections
                new String[] { sId }); //selection args

        // 4. close
        db.close();

        return retorno;
    }
    // Buscar todos os registros
    public List<Map<String, String>> buscarTodosRegistros(String[] colunasBusca) {
        List<Map<String, String>> LMregistro = new LinkedList<Map<String, String>>();
        String colunas = "";
        for(i = 0; i < (colunasBusca.length)-1; i++)
            colunas += colunasBusca[i] + ",";
        colunas += colunasBusca[(colunasBusca.length)-1];
        // 1. build the query
        String query = "SELECT " + colunas +" FROM "+ nm_tabela;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Map<String, String> mregistro = null;
        if (cursor.moveToFirst()) { // ? o primeiro registro do cursor
            do {

                mregistro = new HashMap<String, String>();
                for(i = 0; i < colunasBusca.length;i++)
                    mregistro.put(colunasBusca[i], cursor.getString(i));

                // Add o registro na lista de registro
                LMregistro.add(mregistro);
            } while (cursor.moveToNext());
        }
        //Log.d("getAllBooks()", books.toString());

        // return books
        return LMregistro;
    }

    public List<Map<String, String>> pesquisarComSelect(String query, String[] colunasBusca)
    {
        List<Map<String, String>> LMregistro = new LinkedList<Map<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Map<String, String> mregistro = null;
        if (cursor.moveToFirst()) { // ? o primeiro registro do cursor
            do {

                mregistro = new HashMap<String, String>();
                for(i = 0; i < colunasBusca.length;i++)
                    mregistro.put(colunasBusca[i], cursor.getString(i));

                // Add o registro na lista de registro
                LMregistro.add(mregistro);
            } while (cursor.moveToNext());
        }
        return LMregistro;
    }

    public void deletarComClausula(String where, String[] args)
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(nm_tabela, //table name
                where,  // selections
                args); //selections args

        // 3. close
        db.close();
    }
}
