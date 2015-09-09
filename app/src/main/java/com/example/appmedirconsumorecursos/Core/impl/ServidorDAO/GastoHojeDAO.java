package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

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
public class GastoHojeDAO extends AbstractJdbcDAO {
    private List<EntidadeDominio> listResidencias;
    private GastoHoje gastoHoje = new GastoHoje();

    public GastoHojeDAO() {
        super( gastoHoje.DF_NOME_TABELA, gastoHoje.DF_CD_TABELA, gastoHoje.DF_NOME_PHP);
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
        gastoHoje = (GastoHoje) entidade;
        JSONObject jo = new JSONObject();

        try {
            String query = "SELECT * FROM tb_gasto_atual WHERE 1 = 1";
            if(gastoHoje.getCdResidencia() != null)
                query += " AND cd_residencia = " + String.valueOf(gastoHoje.getCdResidencia());
//
//            if(!TextUtils.isEmpty(residencia.getNome()))
//                query += " AND ds_nome = '" + residencia.getNome() +"'";

            query += " LIMIT 1;";
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
                GastoHoje g = new GastoHoje();
                g.setId(jsonObject.getString(g.DF_CD_TABELA));
                g.setDtUltimaRegistroDia(fomatarData(jsonObject.getString(g.DF_dt_ultimo_registro_dia)));
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
        df = new SimpleDateFormat("dd/MM/yyyy");
        Date dc;
        try
        {
            dc = df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
}
