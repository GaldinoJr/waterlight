package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoAtualDAO  extends AbstractJdbcDAO  {
    private List<EntidadeDominio> listResidencias;
    private GastoAtual gastoAtual;

    public GastoAtualDAO() {
        super(GastoAtual.DF_NOME_TABELA, GastoAtual.DF_CD_TABELA, GastoAtual.DF_NOME_PHP);
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
        gastoAtual = (GastoAtual) entidade;
        JSONObject jo = new JSONObject();

        try {
            String query = "SELECT * FROM tb_gasto_atual WHERE 1 = 1";
            if(gastoAtual.getCdResidencia() != null)
                query += " AND cd_residencia = " + String.valueOf(gastoAtual.getCdResidencia());
//
//            if(!TextUtils.isEmpty(residencia.getNome()))
//                query += " AND ds_nome = '" + residencia.getNome() +"'";

            query += " ORDER BY cd_gasto_atual DESC LIMIT 1;";
            //
            jo.put("query", query);
            String json = jo.toString();
            //
            retornoJason = openConnection("get-json", json);
            //
            listResidencias = new ArrayList<EntidadeDominio>();
            JSONArray jsonArray = new JSONArray(retornoJason);
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = new JSONObject(jsonArray.getString(i));
                GastoAtual g = new GastoAtual();
                g.setId(jsonObject.getString(g.DF_CD_TABELA));
                g.setDtInicioMedicao(fomatarData(jsonObject.getString(g.DF_dtInicioMedicao)));
                g.setDtUltimaMedicao(fomatarData(jsonObject.getString(g.DF_dtUltimaMedicao)));
                g.setVlrGastoAgua(jsonObject.getDouble(g.DF_vlrGastoAgua));
                g.setVlrGastLuz(jsonObject.getDouble(g.DF_vlrGastLuz));
                g.setNrWatts(jsonObject.getDouble(g.DF_nrWatts));
                g.setNrMetroCubicoAgua(jsonObject.getDouble(g.DF_nrMetroCubicoAgua));
                g.setCdResidencia(jsonObject.getInt(g.DF_cdResidencia));
                listResidencias.add(g);
            }
            if (listResidencias.size() > 0)
                return listResidencias;
            else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    private Date fomatarData(String data)
    {
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dc;
        try
        {
            dc = (java.util.Date)df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
}
