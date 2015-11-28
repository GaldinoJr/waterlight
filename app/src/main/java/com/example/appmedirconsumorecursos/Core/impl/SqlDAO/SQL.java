package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

/**
 * Created by Galdino on 29/08/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SQL extends SQLiteOpenHelper {
    private static SQL sInstance;

    private int i;
    private String sqlCriarTabela;
    private String[] colunas;
    //
    private String nomeTabela;
    // Database Version
    private static final int DATABASE_VERSION = 2;
//    public SQL(Context context, String nomeBase, String nomeTabela, String[] colunas, String sqlCriarTabela) {
//        super(context, nomeBase, null, DATABASE_VERSION);
//        //onUpgrade();
//        this.nomeTabela = nomeTabela;
//        this.colunas = new String[colunas.length];
//        this.colunas = colunas;
//        this.sqlCriarTabela = sqlCriarTabela;
//        try {
//            SQLiteDatabase db = getReadableDatabase();
//            onCreate(db); // sempre colocar IF NOT EXISTS na criacão da tabela
//        }
//        catch (SQLiteException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
    public static synchronized SQL getInstance(Context context, String nomeBase) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SQL(context, nomeBase);
        }
        return sInstance;
    }
    public void popularInfo(String nomeTabela, String[] colunas, String sqlCriarTabela)
    {
        this.nomeTabela = nomeTabela;
        this.colunas = new String[colunas.length];
        this.colunas = colunas;
        this.sqlCriarTabela = sqlCriarTabela;
        try {
            SQLiteDatabase db = getReadableDatabase();
            onCreate(db); // sempre colocar IF NOT EXISTS na criacão da tabela
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
    }
    private SQL(Context context, String nomeBase) {
        super(context, nomeBase, null, DATABASE_VERSION);
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
        db.insert(nomeTabela, // table
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
                db.query(nomeTabela, // a. table
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
        db.delete(nomeTabela, //table name
                colunaID +" = ?",  // selections
                new String[] {sId }); //selections args

        // 3. close
        db.close();

        //log
        //Log.d("deleteBook", book.toString());
        // Log.d("deleteBook", String.valueOf(id)); trazendo só o id do livro da certo, porém o log fica sem a informacão completa do livro, tem apenas o id
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
        int retorno = db.update(nomeTabela, //table
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
        String query = "SELECT " + colunas +" FROM "+ nomeTabela;

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
        try {
            if (cursor.moveToFirst()) { // ? o primeiro registro do cursor
                do {

                    mregistro = new HashMap<String, String>();
                    for (i = 0; i < colunasBusca.length; i++)
                        mregistro.put(colunasBusca[i], cursor.getString(i));

                    // Add o registro na lista de registro
                    LMregistro.add(mregistro);
                } while (cursor.moveToNext());
            }
        }
        finally {
            db.close();
        }
        db.close();
        return LMregistro;
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    // mudar para deletar registro
    public void deletarComClausula(String clausula, String[] args)
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(nomeTabela, //table name
                clausula,  // selections
                args); //selections args

        // 3. close
        db.close();
    }
}
