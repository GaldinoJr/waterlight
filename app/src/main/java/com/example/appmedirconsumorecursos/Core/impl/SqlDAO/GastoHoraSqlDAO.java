package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;
import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHora;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Galdino on 24/10/2015.
 */
public class GastoHoraSqlDAO  extends AbstractSqlDAO {
    // Tabela
    private static final String NM_TABELA = GastoHora.DF_NOME_TABELA;
    // Colunas
    private static final String Col_cd_gasto_hora = GastoHora.DF_CD_TABELA;
    private static final String Col_dt_inclusao = GastoHora.DF_dt_inclusao;
    private static final String Col_vlr_gasto_agua = GastoHora.DF_vlrGastoAgua;
    private static final String Col_vlr_gasto_luz = GastoHora.DF_vlrGastLuz;
    private static final String Col_nr_watts = GastoHora.DF_nrWatts;
    private static final String Col_nr_metro_cubico_agua = GastoHora.DF_nrMetroCubicoAgua;
    private static final String Col_cd_residencia = GastoHora.DF_cdResidencia;
    //
    private static final String[] colunas = { Col_dt_inclusao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private static final String[] colunasBusca = {Col_cd_gasto_hora, Col_dt_inclusao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private SQL db;
    private Map<String, String> mapGastoHora;
    private List<EntidadeDominio> listGastoHora;
    private GastoHora gastoHora;

    public GastoHoraSqlDAO(Context context)
    {
        iniciar();
        //db  = new SQL(context, DATABASE_NAME, nomeTabela,colunas, sqlCriarTabela );
        db  = SQL.getInstance(context, DATABASE_NAME);
        db.popularInfo( nomeTabela, colunas, sqlCriarTabela);
    }

    @Override
    protected void iniciar() {
        DATABASE_NAME = "watherLightDB";
        nomeTabela = NM_TABELA;
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nomeTabela+ "( " +
                Col_cd_gasto_hora + " INTEGER PRIMARY KEY, " +
                Col_dt_inclusao + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Col_vlr_gasto_agua + " REAL, " +
                Col_vlr_gasto_luz + " REAL, " +
                Col_nr_watts + " REAL, " +
                Col_nr_metro_cubico_agua + " REAL, " +
                Col_cd_residencia + " INTEGER);";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapGastoHora = new HashMap<String, String>();
        gastoHora =  (GastoHora)entidade;
        try {
            mapGastoHora.put(Col_dt_inclusao, String.valueOf(gastoHora.getsDtInclusao()));
            mapGastoHora.put(Col_vlr_gasto_agua, String.valueOf(gastoHora.getVlrGastoAgua()));
            mapGastoHora.put(Col_vlr_gasto_luz, String.valueOf(gastoHora.getVlrGastLuz()));
            mapGastoHora.put(Col_nr_watts, String.valueOf(gastoHora.getNrWatts()));
            mapGastoHora.put(Col_nr_metro_cubico_agua, String.valueOf(gastoHora.getNrMetroCubicoAgua()));
            mapGastoHora.put(Col_cd_residencia, String.valueOf(gastoHora.getCdResidencia()));
            db.addRegistro(mapGastoHora);
           // db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        gastoHora =  (GastoHora)entidade;
        try
        {
            int i;
            mapGastoHora.put(Col_dt_inclusao, String.valueOf(gastoHora.getsDtInclusao()));
            mapGastoHora.put(Col_vlr_gasto_agua, String.valueOf(gastoHora.getVlrGastoAgua()));
            mapGastoHora.put(Col_vlr_gasto_luz, String.valueOf(gastoHora.getVlrGastLuz()));
            mapGastoHora.put(Col_nr_watts, String.valueOf(gastoHora.getNrWatts()));
            mapGastoHora.put(Col_nr_metro_cubico_agua, String.valueOf(gastoHora.getNrMetroCubicoAgua()));
            mapGastoHora.put(Col_cd_residencia, String.valueOf(gastoHora.getCdResidencia()));
            i = db.alterarRegistro(mapGastoHora,Col_cd_gasto_hora, gastoHora.getId());
            db.close();
        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        int i;
        gastoHora =  (GastoHora)entidade;
        try
        {
            String query = "SELECT " + Col_cd_gasto_hora;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nomeTabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(gastoHora.getId()))
                query += " AND "+ Col_cd_gasto_hora  +" = '" + gastoHora.getId() + "'";
            if (gastoHora.getDtInclusao() != null)
                query += " AND " + Col_dt_inclusao + " = '" + gastoHora.getDtInclusao() + "'";
            if(gastoHora.getsDtInclusao() != null)
            {
                query += " AND DATE(" + Col_dt_inclusao + ") = '"+ gastoHora.getsDtInclusao() +
                        "' AND TIME(" + Col_dt_inclusao + ") BETWEEN '00:00:00' AND '23:59:59'";
            }
            if(gastoHora.getFiltro_fgTodosRegistros() == 1)
                query += " ORDER BY " + Col_dt_inclusao + " ASC";
            else
                query += " ORDER BY "+Col_dt_inclusao+ " DESC";
            //query += " ORDER BY " + Col_dt_inclusao + " ASC";
            listGastoHora = new ArrayList<EntidadeDominio>();
            List<Map<String, String>> listMapGastoHoje = new LinkedList<Map<String, String>>();
            listMapGastoHoje = db.pesquisarComSelect(query, colunasBusca);
           // db.close();
            for(i = 0; i< listMapGastoHoje.size();i++)
            {
                GastoHora g = new GastoHora();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                g.setId(listMapGastoHoje.get(i).get(colunasBusca[0]));
                g.setDtInclusao(formatarData(listMapGastoHoje.get(i).get(colunasBusca[1])));
                g.setVlrGastoAgua(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[2])));
                g.setVlrGastLuz(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[3])));
                g.setNrWatts(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[4])));
                g.setNrMetroCubicoAgua(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[5])));
                g.setCdResidencia(Integer.parseInt(listMapGastoHoje.get(i).get(colunasBusca[6])));
                listGastoHora.add(g);
            }
            if(listGastoHora.size() > 0)
                return listGastoHora;
            else
                return null;
        }
        catch(Exception e){ e.printStackTrace(); }

        return null;
    }
    private Date formatarData(String data)
    {
        SimpleDateFormat df;
        Date dc;
        try
        {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dc = df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
//    private Date converterStringParaData(String sDate) {
//        Date date;
//        String dia,
//                mes,
//                ano,
//                hora;
//        dia = sDate.substring(8, 10);
//        mes = sDate.substring(4, 7);
//        ano = sDate.substring(24, 28);
//        hora = sDate.substring(10, 19);
//        try {
//            mes = new SimpleDateFormat("MM").format(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(mes));
//        } catch (Exception e2) {
//            mes = null;
//        }
//        sDate = ano+"-"+mes+"-"+dia + " " + hora;
//        date = formatarData(sDate);
//        return date;
//    }
}
