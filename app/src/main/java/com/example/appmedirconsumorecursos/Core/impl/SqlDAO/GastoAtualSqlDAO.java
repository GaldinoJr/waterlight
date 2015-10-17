package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import android.content.Context;

import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;

import java.util.List;
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
    private List<EntidadeDominio> listGeral;
    private GastoAtual gastoAtual;

    public GastoAtualSqlDAO(Context context)
    {
        iniciar();
        db  = new SQL(context, DATABASE_NAME, nm_tabela,colunas, sqlCriarTabela );
    }
    @Override
    protected void iniciar() {
        http://tips.androidhive.info/2013/10/android-insert-datetime-value-in-sqlite-database/
        DATABASE_NAME = "watherLightDB";
        nm_tabela = NM_TABELA;
        sqlCriarTabela = "CREATE TABLE IF NOT EXISTS " + nm_tabela+ "( " +
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

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }
}
