package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Galdino on 24/08/2015.
 */
public class ResidenciaServerDAO extends AbstractJdbcDAO
{
    private List<EntidadeDominio> listResidencias;
    private Residencia residencia;
    public ResidenciaServerDAO()
    {
        super("tb_residencia", "cd_residencia", "phpResidenciaServer2.php");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        String retornoJason = "";
        residencia =  (Residencia)entidade;
        JSONObject jo = new JSONObject();

        try{
            String query = "SELECT * FROM tb_residencia WHERE 1 = 1";
            if(!TextUtils.isEmpty(residencia.getSenha()))
                query += " AND ds_senha = '" + residencia.getSenha() +"'" ;

            if(!TextUtils.isEmpty(residencia.getNome()))
                query += " AND ds_nome = '" + residencia.getNome() +"'";
            //
            jo.put("query", query);
            String json = jo.toString();
            //
            retornoJason = openConnection("get-json",json);
            //
            listResidencias = new ArrayList<EntidadeDominio>();
            JSONArray jsonArray = new JSONArray(retornoJason);
            JSONObject jsonObject;

            for(int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = new JSONObject(jsonArray.getString(i));
                Residencia r = new Residencia();
                r.setId(jsonObject.getString("cd_residencia"));
                r.setNome(jsonObject.getString("ds_nome"));
                r.setSenha(jsonObject.getString("ds_senha"));
                r.setEndereco(jsonObject.getString("ds_endereco"));
                r.setNumero(jsonObject.getInt("nr_numero"));
                r.setBairro(jsonObject.getString("ds_bairro"));
                r.setCidade(jsonObject.getString("ds_cidade"));
                r.setUf(jsonObject.getString("ds_uf"));
                r.setCep(jsonObject.getString("nr_cep"));
                r.setNrMorador(jsonObject.getString("nr_morador"));
                r.setNrComodos(jsonObject.getString("nr_comodos"));
                r.setFgExcluido(jsonObject.getInt("fg_excluido"));
                listResidencias.add(r);
            }
            if(listResidencias.size() > 0)
                return listResidencias;
            else
                return null;
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
