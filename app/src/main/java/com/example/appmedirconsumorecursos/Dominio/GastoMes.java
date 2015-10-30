package com.example.appmedirconsumorecursos.Dominio;

import android.content.Context;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Galdino on 14/09/2015.
 */
public class GastoMes extends EntidadeDominio {

    public final static String
            DF_vlrGastoAgua = "vlr_gasto_agua",
            DF_vlrGastLuz = "vlr_gasto_luz",
            DF_nrWatts = "nr_watts",
            DF_nrMetroCubicoAgua = "nr_metro_cubico_agua",
            DF_dt_inclusao = "dt_inclusao",
            DF_cdResidencia = "cd_residencia",
            DF_NOME_TABELA = "tb_gasto_mes",
            DF_CD_TABELA = "cd_gasto_mes",
            DF_NOME_PHP = "cadGastoMes.php";

    private Date dtInclusao;

    private double vlrGastoAgua,
            vlrGastLuz,
            nrWatts,
            nrMetroCubicoAgua;

    private Integer cdResidencia;

    private String sdt_inclusao;


    // SETS

    public void setSdt_inclusao(String sdt_inclusao) {
        this.sdt_inclusao = sdt_inclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public void setVlrGastoAgua(double vlrGastoAgua) {
        this.vlrGastoAgua = vlrGastoAgua;
    }

    public void setVlrGastLuz(double vlrGastLuz) {
        this.vlrGastLuz = vlrGastLuz;
    }

    public void setNrWatts(double nrWatts) {
        this.nrWatts = nrWatts;
    }

    public void setNrMetroCubicoAgua(double nrMetroCubicoAgua) {
        this.nrMetroCubicoAgua = nrMetroCubicoAgua;
    }

    public void setCdResidencia(Integer cdResidencia) {
        this.cdResidencia = cdResidencia;
    }

    // GETS

    public String getSdt_inclusao() {
        return sdt_inclusao;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public double getVlrGastoAgua() {
        return vlrGastoAgua;
    }

    public double getVlrGastLuz() {
        return vlrGastLuz;
    }

    public double getNrWatts() {
        return nrWatts;
    }

    public double getNrMetroCubicoAgua() {
        return nrMetroCubicoAgua;
    }

    public Integer getCdResidencia() {
        return cdResidencia;
    }

    private void popularMap(String acao) {
        map = new HashMap<String, String>();
        map.put(DF_ID, id);
        map.put(DF_vlrGastoAgua, String.valueOf(vlrGastoAgua));
        map.put(DF_vlrGastLuz,String.valueOf(vlrGastLuz));
        map.put(DF_nrWatts, String.valueOf(nrWatts));
        map.put(DF_nrMetroCubicoAgua, String.valueOf(nrMetroCubicoAgua));
        map.put(DF_dt_inclusao,sdt_inclusao);
        map.put(DF_cdResidencia, String.valueOf(cdResidencia));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", GastoMes.class.getName());
    }

    public List<EntidadeDominio> operar(Context context, boolean fgSql, String operacao)
    {
        Session session = Session.getInstance();
        if (fgSql)
            session.setContext(context);
        else
            session.setContext(null);
        //
        Controler controler = new Controler();
        List<EntidadeDominio> list = new LinkedList<EntidadeDominio>();
        popularMap(operacao);
        // popularMap(configuracaoSistema, Controler.DF_CONSULTAR, ConfiguracaoSistema.class.getName());
        Resultado resultado = controler.doPost(map);
        list = resultado.getEntidades();
        return list;
    }
}
