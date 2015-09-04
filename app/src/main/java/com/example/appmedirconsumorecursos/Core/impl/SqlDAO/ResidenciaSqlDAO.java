package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;
import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Galdino on 29/08/2015.
 */
public class ResidenciaSqlDAO extends AbstractSqlDAO {
    // region Colunas
    private static final String Col_cd_residencia = "cd_residencia";
    private static final String Col_ds_nome = "ds_nome";
    private static final String Col_ds_senha = "ds_senha";
    private static final String Col_ds_endereco = "ds_endereco";
    private static final String Col_nr_numero = "nr_numero";
    private static final String Col_ds_bairro = "ds_bairro";
    private static final String Col_ds_cidade = "ds_cidade";
    private static final String Col_ds_uf = "ds_uf";
    private static final String Col_nr_cep = "nr_cep";
    private static final String Col_nr_morador = "nr_morador";
    private static final String Col_nr_comodos = "nr_comodos";
    private static final String Col_fg_excluido = "fg_excluido";
    //
    private static final String[] colunas = { Col_ds_nome , Col_ds_senha, Col_ds_endereco, Col_nr_numero, Col_ds_bairro, Col_ds_cidade,
           Col_ds_uf, Col_nr_cep, Col_nr_morador, Col_nr_comodos,Col_fg_excluido };
    private static final String[] colunasBusca = {Col_cd_residencia, Col_ds_nome , Col_ds_senha, Col_ds_endereco, Col_nr_numero, Col_ds_bairro, Col_ds_cidade,
            Col_ds_uf, Col_nr_cep, Col_nr_morador, Col_nr_comodos,Col_fg_excluido };
    private SQL db;
    private Map<String, String> mapResidencia;
    private List<EntidadeDominio> listResidencias;
    private Residencia residencia;

    public ResidenciaSqlDAO(Context context){
        iniciar();
        db  = new SQL(context, DATABASE_NAME, nm_tabela,colunas, sqlCriarTabela );
    }


    @Override
    protected void iniciar() {
        DATABASE_NAME = "watherLightDB";
        nm_tabela = "tb_residencia";
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nm_tabela+ "( " +
                Col_cd_residencia + " INTEGER PRIMARY KEY, " +
                Col_ds_nome + " TEXT, " +
                Col_ds_endereco + " TEXT, " +
                Col_ds_senha + " TEXTE, " +
                Col_nr_numero + " INTEGER, " +
                Col_ds_bairro + " TEXT, " +
                Col_ds_cidade + " TEXT, " +
                Col_ds_uf + " TEXT, " +
                Col_nr_cep + " TEXT, " +
                Col_nr_morador + " INTEGER, " +
                Col_nr_comodos + " INTEGER, " +
                Col_fg_excluido + " INTEGER );";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapResidencia = new HashMap<String, String>();
        residencia =  (Residencia)entidade;
        try {
            mapResidencia.put(Col_ds_nome, residencia.getNome());
            mapResidencia.put(Col_ds_senha, residencia.getSenha());
            mapResidencia.put(Col_ds_endereco, residencia.getEndereco());
            mapResidencia.put(Col_nr_numero, String.valueOf(residencia.getNumero()));
            mapResidencia.put(Col_ds_bairro, residencia.getBairro());
            mapResidencia.put(Col_ds_cidade, residencia.getCidade());
            mapResidencia.put(Col_ds_uf, residencia.getUf());
            mapResidencia.put(Col_nr_cep, residencia.getCep());
            mapResidencia.put(Col_nr_morador, residencia.getNrMorador());
            mapResidencia.put(Col_nr_comodos, residencia.getNrComodos());
            mapResidencia.put(Col_fg_excluido, String.valueOf(residencia.getFgExcluido()));

            db.addRegistro(mapResidencia);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        int i;
        residencia =  (Residencia)entidade;
        try
        {
            String query = "SELECT " + Col_cd_residencia;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nm_tabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(residencia.getSenha()))
                query += " AND ds_senha = '" + residencia.getSenha() + "'";

            if (!TextUtils.isEmpty(residencia.getNome()))
                query += " AND ds_nome = '" + residencia.getNome() + "'";
            listResidencias = new ArrayList<EntidadeDominio>();
             List<Map<String, String>> lmResidencias = new LinkedList<Map<String, String>>();
            lmResidencias = db.pesquisarComSelect(query, colunasBusca);
            for(i = 0; i< lmResidencias.size();i++)
            {
                Residencia r = new Residencia();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                r.setId(lmResidencias.get(i).get("cd_residencia"));
                r.setNome(lmResidencias.get(i).get("ds_nome"));
                r.setSenha(lmResidencias.get(i).get("ds_senha"));
                r.setEndereco(lmResidencias.get(i).get("ds_endereco"));
                r.setNumero(Integer.parseInt(lmResidencias.get(i).get("nr_numero")));
                r.setBairro(lmResidencias.get(i).get("ds_bairro"));
                r.setCidade(lmResidencias.get(i).get("ds_cidade"));
                r.setUf(lmResidencias.get(i).get("ds_uf"));
                r.setCep(lmResidencias.get(i).get("nr_cep"));
                r.setNrMorador(lmResidencias.get(i).get("nr_morador"));
                r.setNrComodos(lmResidencias.get(i).get("nr_comodos"));
                r.setFgExcluido(Integer.parseInt(lmResidencias.get(i).get("fg_excluido")));
                listResidencias.add(r);
            }
            if(listResidencias.size() > 0)
                return listResidencias;
            else
                return null;
        }
        catch(Exception e){ e.printStackTrace(); }

        return null;
    }
}