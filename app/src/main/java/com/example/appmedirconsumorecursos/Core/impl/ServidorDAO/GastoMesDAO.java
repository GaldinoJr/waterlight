package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoMes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Galdino on 14/09/2015.
 */
public class GastoMesDAO extends AbstractServerDAO {
    private List<EntidadeDominio> listResidencias;
    private GastoMes gastoMes;

    public GastoMesDAO() {
        super( GastoMes.DF_NOME_TABELA, GastoMes.DF_CD_TABELA, GastoMes.DF_NOME_PHP);
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
        gastoMes = (GastoMes) entidade;
        JSONObject jo = new JSONObject();

        try {
            String query = "SELECT * FROM " + GastoMes.DF_NOME_TABELA + " WHERE 1 = 1";
            if(gastoMes.getCdResidencia() != null)
                query += " AND cd_residencia = " + String.valueOf(gastoMes.getCdResidencia());
//
            if(gastoMes.getSdt_inclusao() != null)
               query += " AND dt_inclusao = STR_TO_DATE( '" + gastoMes.getSdt_inclusao()+"' ,'%d/%m/%Y')";

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
                GastoMes g = new GastoMes();
                g.setId(jsonObject.getString(g.DF_CD_TABELA));
                g.setVlrGastoAgua(jsonObject.getDouble(g.DF_vlrGastoAgua));
                g.setVlrGastLuz(jsonObject.getDouble(g.DF_vlrGastLuz));
                g.setNrWatts(jsonObject.getDouble(g.DF_nrWatts));
                g.setNrMetroCubicoAgua(jsonObject.getDouble(g.DF_nrMetroCubicoAgua));
                g.setDtInclusao(fomatarData(jsonObject.getString(g.DF_dt_inclusao)));
                g.setSdt_inclusao(jsonObject.getString(g.DF_dt_inclusao));
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
