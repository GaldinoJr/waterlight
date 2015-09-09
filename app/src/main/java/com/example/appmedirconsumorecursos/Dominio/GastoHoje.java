package com.example.appmedirconsumorecursos.Dominio;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoHoje extends EntidadeDominio {

    public final static String DF_dt_ultimo_registro_dia = "dt_ultimo_registro_dia",
            DF_vlrGastoAgua = "vlr_gasto_agua",
            DF_vlrGastLuz = "vlr_gasto_luz",
            DF_nrWatts = "nr_watts",
            DF_nrMetroCubicoAgua = "nr_metro_cubico_agua",
            DF_cdResidencia = "cd_residencia",
            DF_NOME_TABELA = "tb_gasto_hoje",
            DF_CD_TABELA = "cd_gasto_hoje",
            DF_NOME_PHP = "cadGastoHoje.php";

    private Date dtUltimaRegistroDia;

    private double vlrGastoAgua,
            vlrGastLuz,
            nrWatts,
            nrMetroCubicoAgua;

    private int cdResidencia;

    // SETS


    public void setDtUltimaRegistroDia(Date dtUltimaRegistroDia) {
        this.dtUltimaRegistroDia = dtUltimaRegistroDia;
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

    public void setCdResidencia(int cdResidencia) {
        this.cdResidencia = cdResidencia;
    }

    // GETS


    public Date getDtUltimaRegistroDia() {
        return dtUltimaRegistroDia;
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

    public int getCdResidencia() {
        return cdResidencia;
    }

    @Override
    public void popularMap(EntidadeDominio entidadeDominio, String acao, String nomeClasse) {
        GastoHoje gastoHoje = (GastoHoje)entidadeDominio;
        map = new HashMap<String, String>();
        map.put(gastoHoje.DF_ID, gastoHoje.getId());
        map.put(gastoHoje.DF_dt_ultimo_registro_dia, String.valueOf(gastoHoje.getDtUltimaRegistroDia()));
        map.put(gastoHoje.DF_vlrGastoAgua, String.valueOf(gastoHoje.getVlrGastoAgua()));
        map.put(gastoHoje.DF_vlrGastLuz,String.valueOf(gastoHoje.getVlrGastLuz()));
        map.put(gastoHoje.DF_nrWatts, String.valueOf(gastoHoje.getNrWatts()));
        map.put(gastoHoje.DF_nrMetroCubicoAgua, String.valueOf(gastoHoje.getNrMetroCubicoAgua()));
        map.put(gastoHoje.DF_cdResidencia, String.valueOf(gastoHoje.getCdResidencia()));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", nomeClasse);
    }
}
