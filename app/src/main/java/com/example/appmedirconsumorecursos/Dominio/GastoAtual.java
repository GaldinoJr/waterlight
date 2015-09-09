package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoAtual  extends EntidadeDominio {
    public final static String DF_dtInicioMedicao = "dt_inicio_medicao",
            DF_dtUltimaMedicao = "dt_ultima_medicao",
            DF_vlrGastoAgua = "vlr_gasto_agua",
            DF_vlrGastLuz = "vlr_gasto_luz",
            DF_nrWatts = "nr_watts",
            DF_nrMetroCubicoAgua = "nr_metro_cubico_agua",
            DF_cdResidencia = "cd_residencia",
            DF_NOME_TABELA = "tb_gasto_atual",
            DF_CD_TABELA = "cd_gasto_atual",
            DF_NOME_PHP = "cadGastoAtual.php";
    private Date dtInicioMedicao,
         dtUltimaMedicao;
    private double vlrGastoAgua,
           vlrGastLuz,
           nrWatts,
           nrMetroCubicoAgua;
    private Integer cdResidencia;

    // SETS


    public void setDtInicioMedicao(Date dtInicioMedicao) {
        this.dtInicioMedicao = dtInicioMedicao;
    }

    public void setDtUltimaMedicao(Date dtUltimaMedicao) {
        this.dtUltimaMedicao = dtUltimaMedicao;
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

    public Date getDtInicioMedicao() {
        return dtInicioMedicao;
    }

    public Date getDtUltimaMedicao() {
        return dtUltimaMedicao;
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

    @Override
    public void popularMap(EntidadeDominio entidadeDominio, String acao, String nomeClasse) {
        GastoAtual gastoAtual = (GastoAtual)entidadeDominio;
        map = new HashMap<String, String>();
        map.put(gastoAtual.DF_ID, gastoAtual.getId());
        map.put(gastoAtual.DF_dtInicioMedicao, String.valueOf(gastoAtual.getDtInicioMedicao()));
        map.put(gastoAtual.DF_dtUltimaMedicao, String.valueOf(gastoAtual.getDtUltimaMedicao()));
        map.put(gastoAtual.DF_vlrGastoAgua, String.valueOf(gastoAtual.getVlrGastoAgua()));
        map.put(gastoAtual.DF_vlrGastLuz,String.valueOf(gastoAtual.getVlrGastLuz()));
        map.put(gastoAtual.DF_nrWatts, String.valueOf(gastoAtual.getNrWatts()));
        map.put(gastoAtual.DF_nrMetroCubicoAgua, String.valueOf(gastoAtual.getNrMetroCubicoAgua()));
        map.put(gastoAtual.DF_cdResidencia, String.valueOf(gastoAtual.getCdResidencia()));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", nomeClasse);
    }
}
