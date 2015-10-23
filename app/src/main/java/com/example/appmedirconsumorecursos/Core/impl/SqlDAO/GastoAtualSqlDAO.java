package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;
import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Galdino on 17/10/2015.
 */
public class GastoAtualSqlDAO extends AbstractSqlDAO {
    // Tabela
    private static final String NM_TABELA = GastoAtual.DF_NOME_TABELA;
    // Colunas
    private static final String Col_cd_gasto_atual = GastoAtual.DF_CD_TABELA;
    private static final String Col_dt_inicio_medicao = GastoAtual.DF_dtInicioMedicao;
    private static final String Col_dt_ultima_medicao = GastoAtual.DF_dtUltimaMedicao;
    private static final String Col_vlr_gasto_agua = GastoAtual.DF_vlrGastoAgua;
    private static final String Col_vlr_gasto_luz = GastoAtual.DF_vlrGastLuz;
    private static final String Col_nr_watts = GastoAtual.DF_nrWatts;
    private static final String Col_nr_metro_cubico_agua = GastoAtual.DF_nrMetroCubicoAgua;
    private static final String Col_cd_residencia = GastoAtual.DF_cdResidencia;
    //
    private static final String[] colunas = { Col_dt_inicio_medicao, Col_dt_ultima_medicao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private static final String[] colunasBusca = {Col_cd_gasto_atual, Col_dt_inicio_medicao, Col_dt_ultima_medicao, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private SQL db;
    private Map<String, String> mapGastoAtual;
    private List<EntidadeDominio> listGastoAtual;
    private GastoAtual gastoAtual;

    public GastoAtualSqlDAO(Context context)
    {
        iniciar();
        db  = new SQL(context, DATABASE_NAME, nomeTabela,colunas, sqlCriarTabela );
    }
    @Override
    protected void iniciar() {
        http://tips.androidhive.info/2013/10/android-insert-datetime-value-in-sqlite-database/
        DATABASE_NAME = "watherLightDB";
        nomeTabela = NM_TABELA;
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nomeTabela+ "( " +
                Col_cd_gasto_atual + " INTEGER PRIMARY KEY, " +
                Col_dt_inicio_medicao + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Col_dt_ultima_medicao + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Col_vlr_gasto_agua + " REAL, " +
                Col_vlr_gasto_luz + " REAL, " +
                Col_nr_watts + " REAL, " +
                Col_nr_metro_cubico_agua + " REAL, " +
                Col_cd_residencia + " INTEGER);";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapGastoAtual = new HashMap<String, String>();
        gastoAtual =  (GastoAtual)entidade;
        try {
            mapGastoAtual.put(Col_dt_inicio_medicao, String.valueOf(gastoAtual.getDtInicioMedicao()));
            mapGastoAtual.put(Col_dt_ultima_medicao, String.valueOf(gastoAtual.getDtUltimaMedicao()));
            mapGastoAtual.put(Col_vlr_gasto_agua, String.valueOf(gastoAtual.getVlrGastoAgua()));
            mapGastoAtual.put(Col_vlr_gasto_luz, String.valueOf(gastoAtual.getVlrGastLuz()));
            mapGastoAtual.put(Col_nr_watts, String.valueOf(gastoAtual.getNrWatts()));
            mapGastoAtual.put(Col_nr_metro_cubico_agua, String.valueOf(gastoAtual.getNrMetroCubicoAgua()));
            mapGastoAtual.put(Col_cd_residencia, String.valueOf(gastoAtual.getCdResidencia()));
            db.addRegistro(mapGastoAtual);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        gastoAtual =  (GastoAtual)entidade;
        try
        {
            int i;
            mapGastoAtual.put(Col_dt_inicio_medicao, String.valueOf(gastoAtual.getDtInicioMedicao()));
            mapGastoAtual.put(Col_dt_ultima_medicao, String.valueOf(gastoAtual.getDtUltimaMedicao()));
            mapGastoAtual.put(Col_vlr_gasto_agua, String.valueOf(gastoAtual.getVlrGastoAgua()));
            mapGastoAtual.put(Col_vlr_gasto_luz, String.valueOf(gastoAtual.getVlrGastLuz()));
            mapGastoAtual.put(Col_nr_watts, String.valueOf(gastoAtual.getNrWatts()));
            mapGastoAtual.put(Col_nr_metro_cubico_agua, String.valueOf(gastoAtual.getNrMetroCubicoAgua()));
            mapGastoAtual.put(Col_cd_residencia, String.valueOf(gastoAtual.getCdResidencia()));
            i = db.alterarRegistro(mapGastoAtual,Col_cd_gasto_atual, gastoAtual.getId());

        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        int i;
        gastoAtual =  (GastoAtual)entidade;
        try
        {
            String query = "SELECT " + Col_cd_gasto_atual;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nomeTabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(gastoAtual.getId()))
                query += " AND cd_gasto_atual = '" + gastoAtual.getId() + "'";
            query += " ORDER BY " + Col_cd_gasto_atual + " DESC;";
            listGastoAtual = new ArrayList<EntidadeDominio>();
            List<Map<String, String>> listMapGastoAtual = new LinkedList<Map<String, String>>();
            listMapGastoAtual = db.pesquisarComSelect(query, colunasBusca);
            for(i = 0; i< listMapGastoAtual.size();i++)
            {
                GastoAtual g = new GastoAtual();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                g.setId(listMapGastoAtual.get(i).get(colunasBusca[0]));
                g.setDtInicioMedicao(converterStringParaData(listMapGastoAtual.get(i).get(colunasBusca[1])));
                g.setDtUltimaMedicao(converterStringParaData(listMapGastoAtual.get(i).get(colunasBusca[2])));
                g.setVlrGastoAgua(Double.parseDouble(listMapGastoAtual.get(i).get(colunasBusca[3])));
                g.setVlrGastLuz(Double.parseDouble(listMapGastoAtual.get(i).get(colunasBusca[4])));
                g.setNrWatts(Double.parseDouble(listMapGastoAtual.get(i).get(colunasBusca[5])));
                g.setNrMetroCubicoAgua(Double.parseDouble(listMapGastoAtual.get(i).get(colunasBusca[6])));
                g.setCdResidencia(Integer.parseInt(listMapGastoAtual.get(i).get(colunasBusca[7])));
                listGastoAtual.add(g);
            }
            if(listGastoAtual.size() > 0)
                return listGastoAtual;
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
