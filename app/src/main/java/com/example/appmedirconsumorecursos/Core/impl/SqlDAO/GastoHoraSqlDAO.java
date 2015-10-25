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
import java.util.Locale;
import java.util.Map;

/**
 * Created by Galdino on 24/10/2015.
 */
public class GastoHoraSqlDAO  extends AbstractSqlDAO {
    // Tabela
    private static final String NM_TABELA = GastoHora.DF_NOME_TABELA;
    // Colunas
    private static final String Col_cd_gasto_hoje = GastoHora.DF_CD_TABELA;
    private static final String Col_dt_inclusao = GastoHora.DF_dt_inclusao;
    private static final String Col_vlr_gasto_agua = GastoHora.DF_vlrGastoAgua;
    private static final String Col_vlr_gasto_luz = GastoHora.DF_vlrGastLuz;
    private static final String Col_nr_watts = GastoHora.DF_nrWatts;
    private static final String Col_nr_metro_cubico_agua = GastoHora.DF_nrMetroCubicoAgua;
    private static final String Col_cd_residencia = GastoHora.DF_cdResidencia;
    //
    private static final String[] colunas = { Col_dt_inclusao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private static final String[] colunasBusca = {Col_cd_gasto_hoje, Col_dt_inclusao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private SQL db;
    private Map<String, String> mapGastoHoje;
    private List<EntidadeDominio> listGastoHoje;
    private GastoHora gastoHora;

    public GastoHoraSqlDAO(Context context)
    {
        iniciar();
        db  = new SQL(context, DATABASE_NAME, nomeTabela,colunas, sqlCriarTabela );
    }

    @Override
    protected void iniciar() {
        DATABASE_NAME = "watherLightDB";
        nomeTabela = NM_TABELA;
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nomeTabela+ "( " +
                Col_cd_gasto_hoje + " INTEGER PRIMARY KEY, " +
                Col_dt_inclusao + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Col_vlr_gasto_agua + " REAL, " +
                Col_vlr_gasto_luz + " REAL, " +
                Col_nr_watts + " REAL, " +
                Col_nr_metro_cubico_agua + " REAL, " +
                Col_cd_residencia + " INTEGER);";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapGastoHoje = new HashMap<String, String>();
        gastoHora =  (GastoHora)entidade;
        try {
            mapGastoHoje.put(Col_dt_inclusao, String.valueOf(gastoHora.getDtInclusao()));
            mapGastoHoje.put(Col_vlr_gasto_agua, String.valueOf(gastoHora.getVlrGastoAgua()));
            mapGastoHoje.put(Col_vlr_gasto_luz, String.valueOf(gastoHora.getVlrGastLuz()));
            mapGastoHoje.put(Col_nr_watts, String.valueOf(gastoHora.getNrWatts()));
            mapGastoHoje.put(Col_nr_metro_cubico_agua, String.valueOf(gastoHora.getNrMetroCubicoAgua()));
            mapGastoHoje.put(Col_cd_residencia, String.valueOf(gastoHora.getCdResidencia()));
            db.addRegistro(mapGastoHoje);
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
            mapGastoHoje.put(Col_dt_inclusao, String.valueOf(gastoHora.getDtInclusao()));
            mapGastoHoje.put(Col_vlr_gasto_agua, String.valueOf(gastoHora.getVlrGastoAgua()));
            mapGastoHoje.put(Col_vlr_gasto_luz, String.valueOf(gastoHora.getVlrGastLuz()));
            mapGastoHoje.put(Col_nr_watts, String.valueOf(gastoHora.getNrWatts()));
            mapGastoHoje.put(Col_nr_metro_cubico_agua, String.valueOf(gastoHora.getNrMetroCubicoAgua()));
            mapGastoHoje.put(Col_cd_residencia, String.valueOf(gastoHora.getCdResidencia()));
            i = db.alterarRegistro(mapGastoHoje,Col_cd_gasto_hoje, gastoHora.getId());

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
            String query = "SELECT " + Col_cd_gasto_hoje;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nomeTabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(gastoHora.getId()))
                query += " AND "+ Col_cd_gasto_hoje  +" = '" + gastoHora.getId() + "'";
            if (gastoHora.getDtInclusao() != null)
                query += " AND " + Col_dt_inclusao + " = '" + gastoHora.getDtInclusao() + "'";

            query += " ORDER BY "+Col_cd_gasto_hoje+ " DESC";
            listGastoHoje = new ArrayList<EntidadeDominio>();
            List<Map<String, String>> listMapGastoHoje = new LinkedList<Map<String, String>>();
            listMapGastoHoje = db.pesquisarComSelect(query, colunasBusca);
            for(i = 0; i< listMapGastoHoje.size();i++)
            {
                GastoHora g = new GastoHora();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                g.setId(listMapGastoHoje.get(i).get(colunasBusca[0]));
                g.setDtInclusao(converterStringParaData(listMapGastoHoje.get(i).get(colunasBusca[1])));
                g.setVlrGastoAgua(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[2])));
                g.setVlrGastLuz(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[3])));
                g.setNrWatts(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[4])));
                g.setNrMetroCubicoAgua(Double.parseDouble(listMapGastoHoje.get(i).get(colunasBusca[5])));
                g.setCdResidencia(Integer.parseInt(listMapGastoHoje.get(i).get(colunasBusca[6])));
                listGastoHoje.add(g);
            }
            if(listGastoHoje.size() > 0)
                return listGastoHoje;
            else
                return null;
        }
        catch(Exception e){ e.printStackTrace(); }

        return null;
    }
    private Date formatarData(String data)
    {
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = null;
        Date dc;
        try
        {
            //String a = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(data));
            dc = (java.util.Date)df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
    private Date converterStringParaData(String sDate) {
        Date date;
        String dia,
                mes,
                ano,
                hora;
        dia = sDate.substring(8, 10);
        mes = sDate.substring(4, 7);
        ano = sDate.substring(24, 28);
        hora = sDate.substring(10, 19);
        try {
            mes = new SimpleDateFormat("MM").format(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(mes));
        } catch (Exception e2) {
            mes = null;
        }
        sDate = ano+"-"+mes+"-"+dia + " " + hora;
        date = formatarData(sDate);
        return date;
    }
}
