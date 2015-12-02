package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;
import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Galdino on 21/10/2015.
 */
public class GastoHojeSqlDAO2 extends AbstractSqlDAO {
    // Tabela
    private static final String NM_TABELA = GastoHoje.DF_NOME_TABELA;
    // Colunas
    private static final String Col_cd_gasto_hoje = GastoHoje.DF_CD_TABELA;
    private static final String Col_dt_ultimo_registro_dia = GastoHoje.DF_dt_ultimo_registro_dia;
    private static final String Col_vlr_gasto_agua = GastoHoje.DF_vlrGastoAgua;
    private static final String Col_vlr_gasto_luz = GastoHoje.DF_vlrGastLuz;
    private static final String Col_nr_watts = GastoHoje.DF_nrWatts;
    private static final String Col_nr_metro_cubico_agua = GastoHoje.DF_nrMetroCubicoAgua;
    private static final String Col_cd_residencia = GastoHoje.DF_cdResidencia;
    //
    private static final String[] colunas = { Col_dt_ultimo_registro_dia, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private static final String[] colunasBusca = {Col_cd_gasto_hoje, Col_dt_ultimo_registro_dia, Col_vlr_gasto_agua, Col_vlr_gasto_luz, Col_nr_watts, Col_nr_metro_cubico_agua, Col_cd_residencia};
    private SQL db;
    private Map<String, String> mapGastoHoje;
    private List<EntidadeDominio> listGastoHoje;
    private GastoHoje gastohoje;

    public GastoHojeSqlDAO2(Context context)
    {
        iniciar();
       // db  = SQL.getInstance(context, DATABASE_NAME, nomeTabela, colunas, sqlCriarTabela );
        db  = SQL.getInstance(context, DATABASE_NAME);
        db.popularInfo( nomeTabela, colunas, sqlCriarTabela);
    }

    @Override
    protected void iniciar() {
        DATABASE_NAME = "watherLightDB";
        nomeTabela = NM_TABELA;
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nomeTabela+ "( " +
                Col_cd_gasto_hoje + " INTEGER PRIMARY KEY, " +
                Col_dt_ultimo_registro_dia + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Col_vlr_gasto_agua + " REAL, " +
                Col_vlr_gasto_luz + " REAL, " +
                Col_nr_watts + " REAL, " +
                Col_nr_metro_cubico_agua + " REAL, " +
                Col_cd_residencia + " INTEGER);";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapGastoHoje = new HashMap<String, String>();
        gastohoje =  (GastoHoje)entidade;
        try {
            mapGastoHoje.put(Col_dt_ultimo_registro_dia, gastohoje.getsDtUltimoRegistroDia());
            mapGastoHoje.put(Col_vlr_gasto_agua, String.valueOf(gastohoje.getVlrGastoAgua()));
            mapGastoHoje.put(Col_vlr_gasto_luz, String.valueOf(gastohoje.getVlrGastLuz()));
            mapGastoHoje.put(Col_nr_watts, String.valueOf(gastohoje.getNrWatts()));
            mapGastoHoje.put(Col_nr_metro_cubico_agua, String.valueOf(gastohoje.getNrMetroCubicoAgua()));
            mapGastoHoje.put(Col_cd_residencia, String.valueOf(gastohoje.getCdResidencia()));
            db.addRegistro(mapGastoHoje);
           /// db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        gastohoje =  (GastoHoje)entidade;
        try
        {
            int i;
            mapGastoHoje.put(Col_dt_ultimo_registro_dia, String.valueOf(gastohoje.getDtUltimaRegistroDia()));
            mapGastoHoje.put(Col_vlr_gasto_agua, String.valueOf(gastohoje.getVlrGastoAgua()));
            mapGastoHoje.put(Col_vlr_gasto_luz, String.valueOf(gastohoje.getVlrGastLuz()));
            mapGastoHoje.put(Col_nr_watts, String.valueOf(gastohoje.getNrWatts()));
            mapGastoHoje.put(Col_nr_metro_cubico_agua, String.valueOf(gastohoje.getNrMetroCubicoAgua()));
            mapGastoHoje.put(Col_cd_residencia, String.valueOf(gastohoje.getCdResidencia()));
            i = db.alterarRegistro(mapGastoHoje,Col_cd_gasto_hoje, gastohoje.getId());
           // db.close();
        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        int i;
        gastohoje =  (GastoHoje)entidade;
        try
        {
            String query = "SELECT DISTINCT " + Col_cd_gasto_hoje;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nomeTabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(gastohoje.getId()))
                query += " AND "+ Col_cd_gasto_hoje  +" = '" + gastohoje.getId() + "'";
            if (gastohoje.getDtUltimaRegistroDia() != null)
                query += " AND " + Col_dt_ultimo_registro_dia + " = '" + gastohoje.getDtUltimaRegistroDia() + "'";

            if (gastohoje.getsDtInicialBusca() != null && gastohoje.getsDtFinalBusca() != null)
            {
                query += " AND DATE(" + Col_dt_ultimo_registro_dia + ") BETWEEN '" + gastohoje.getsDtInicialBusca() +
                        "' AND '" + gastohoje.getsDtFinalBusca() +
                "' AND ((TIME(" + Col_dt_ultimo_registro_dia + ") BETWEEN '23:00:00' AND '23:59:59') OR TIME(" + Col_dt_ultimo_registro_dia + ") = '23:00:00')";
                query += " ORDER BY " + Col_dt_ultimo_registro_dia + " ASC";
            }
            else
                query += " ORDER BY " + Col_dt_ultimo_registro_dia + " DESC";
            listGastoHoje = new ArrayList<EntidadeDominio>();
            List<Map<String, String>> listMapGastoHoje = new LinkedList<Map<String, String>>();
            listMapGastoHoje = db.pesquisarComSelect(query, colunasBusca);
           // db.close();
            //db.finalize();
            for(i = 0; i< listMapGastoHoje.size();i++)
            {
                GastoHoje g = new GastoHoje();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                g.setId(listMapGastoHoje.get(i).get(colunasBusca[0]));
                g.setDtUltimaRegistroDia(formatarData(listMapGastoHoje.get(i).get(colunasBusca[1])));
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
        catch(Exception e){ e.printStackTrace(); } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

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
//    private Date formatarData(String data)
//    {
//        SimpleDateFormat df;
//        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String a = null;
//        Date dc;
//        try
//        {
//            //String a = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(data));
//            dc = (java.util.Date)df.parse(data);
//        }
//        catch (Exception e) {
//            dc = null;
//        }
//        return dc;
//    }
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
