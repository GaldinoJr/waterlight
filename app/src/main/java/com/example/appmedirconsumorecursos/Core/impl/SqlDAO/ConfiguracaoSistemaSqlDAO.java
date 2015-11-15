package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;
import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Galdino on 02/09/2015.
 */
public class ConfiguracaoSistemaSqlDAO extends AbstractSqlDAO{
    private static final String Col_cd_residencia = "cd_residencia";
    private static final String Col_fg_logar_automaticamente = "fg_logar_automaticamente";
    private static final String Col_fg_atualizar_automaticamente = "fg_atualizar_automaticamente";
    private static final String Col_ind_tipo_atualizacao = "ind_tipo_atualizacao";
    private static final String Col_ind_tipo_voltagem = "ind_tipo_voltagem";
    private static final String Col_vlr_tarifa_agua = "vlr_tarifa_agua";
    private static final String Col_vlr_tarifa_luz = "vlr_tarifa_luz";
    //
    private static final String[] colunas = { Col_fg_logar_automaticamente, Col_fg_atualizar_automaticamente,Col_ind_tipo_atualizacao, Col_ind_tipo_voltagem, Col_vlr_tarifa_agua, Col_vlr_tarifa_luz};
    private static final String[] colunasBusca = {Col_cd_residencia, Col_fg_logar_automaticamente, Col_fg_atualizar_automaticamente, Col_ind_tipo_atualizacao, Col_ind_tipo_voltagem, Col_vlr_tarifa_agua, Col_vlr_tarifa_luz};
    private SQL db;
    private Map<String, String> mapGeral;
    private List<EntidadeDominio> listGeral;
    private ConfiguracaoSistema configSistema;

    public ConfiguracaoSistemaSqlDAO(Context context)
    {
        iniciar();
        db  = new SQL(context, DATABASE_NAME, nomeTabela,colunas, sqlCriarTabela );
    }

    @Override
    protected void iniciar() {
        DATABASE_NAME = "watherLightDB";
        nomeTabela = "tb_configuracao_sistema";
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nomeTabela+ "( " +
                Col_cd_residencia + " INTEGER PRIMARY KEY, " +
                Col_fg_logar_automaticamente + " INTEGER, " +
                Col_fg_atualizar_automaticamente + " INTEGER, " +
                Col_ind_tipo_voltagem + " INTEGER, " +
                Col_vlr_tarifa_agua + " REAL, " +
                Col_vlr_tarifa_luz + " REAL, " +
                Col_ind_tipo_atualizacao + " INTEGER);";
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        mapGeral = new HashMap<String, String>();
        configSistema =  (ConfiguracaoSistema)entidade;
        try {
            mapGeral.put(Col_fg_logar_automaticamente, String.valueOf(configSistema.getFgLogarAutomaticamente()));
            mapGeral.put(Col_fg_atualizar_automaticamente, String.valueOf(configSistema.getFgAtualizarAutomaticamente()));
            mapGeral.put(Col_ind_tipo_voltagem, String.valueOf(configSistema.getIndTipoVoltagem()));
            mapGeral.put(Col_vlr_tarifa_agua, String.valueOf(configSistema.getVlrTarifaAgua()));
            mapGeral.put(Col_vlr_tarifa_luz, String.valueOf(configSistema.getVlrTarifaLuz()));
            mapGeral.put(Col_ind_tipo_atualizacao, String.valueOf(configSistema.getIndTipoAtualizacao()));
            db.addRegistro(mapGeral);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        configSistema =  (ConfiguracaoSistema)entidade;
        try
        {
            int i;
            mapGeral = new HashMap<String, String>();
            mapGeral.put(Col_fg_logar_automaticamente, String.valueOf(configSistema.getFgLogarAutomaticamente()));
            mapGeral.put(Col_fg_atualizar_automaticamente, String.valueOf(configSistema.getFgAtualizarAutomaticamente()));
            mapGeral.put(Col_ind_tipo_voltagem, String.valueOf(configSistema.getIndTipoVoltagem()));
            mapGeral.put(Col_vlr_tarifa_agua, String.valueOf(configSistema.getVlrTarifaAgua()));
            mapGeral.put(Col_vlr_tarifa_luz, String.valueOf(configSistema.getVlrTarifaLuz()));
            mapGeral.put(Col_ind_tipo_atualizacao, String.valueOf(configSistema.getIndTipoAtualizacao()));
            i = db.alterarRegistro(mapGeral,Col_cd_residencia, configSistema.getId());

        }
        catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        int i;
        configSistema =  (ConfiguracaoSistema)entidade;
        try
        {
            String query = "SELECT " + Col_cd_residencia;
            for(i = 1; i < colunasBusca.length; i++)
            {
                query += ", " + colunasBusca[i];
            }
            query += " FROM " + nomeTabela + " WHERE 1 = 1";
            if (!TextUtils.isEmpty(configSistema.getId()))
                query += " AND cd_residencia = '" + configSistema.getId() + "'";

            listGeral = new ArrayList<EntidadeDominio>();
            List<Map<String, String>> listMapGeral = new LinkedList<Map<String, String>>();
            listMapGeral = db.pesquisarComSelect(query, colunasBusca);
            for(i = 0; i< listMapGeral.size();i++)
            {
                ConfiguracaoSistema g = new ConfiguracaoSistema();
                // ******************* TEM QUE SER A MESMA SEQUENCIA DA LISTA(colunasBusca)***********************
                g.setId(listMapGeral.get(i).get(colunasBusca[0]));
                g.setFgLogarAutomaticamente(Integer.parseInt(listMapGeral.get(i).get(colunasBusca[1])));
                g.setFgAtualizarAutomaticamente(Integer.parseInt(listMapGeral.get(i).get(colunasBusca[2])));
                g.setIndTipoAtualizacao(Integer.parseInt(listMapGeral.get(i).get(colunasBusca[3])));
                g.setIndTipoVoltagem(Integer.parseInt(listMapGeral.get(i).get(colunasBusca[4])));
                g.setVlrTarifaAgua(Double.parseDouble(listMapGeral.get(i).get(colunasBusca[5])));
                g.setVlrTarifaLuz(Double.parseDouble(listMapGeral.get(i).get(colunasBusca[6])));
                listGeral.add(g);
            }
            if(listGeral.size() > 0)
                return listGeral;
            else
                return null;
        }
        catch(Exception e){ e.printStackTrace(); }

        return null;
    }
}
