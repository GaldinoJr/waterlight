package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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

// SETS DO MAPA

    public void setMapDtInicioMedicao(String dtInicioMedicao) {
        map.put(DF_dtInicioMedicao,String.valueOf(dtInicioMedicao));
    }

    public void setMapDtUltimaMedicao(String dtUltimaMedicao) {
        map.put(DF_dtUltimaMedicao,String.valueOf(dtUltimaMedicao));
    }

    public void setMapVlrGastoAgua(String vlrGastoAgua) {
        map.put(DF_vlrGastoAgua,String.valueOf(vlrGastoAgua));
    }

    public void setMapVlrGastLuz(String vlrGastLuz) {
        map.put(DF_vlrGastLuz,String.valueOf(vlrGastLuz));
    }

    public void setMapNrWatts(String nrWatts) {
        map.put(DF_nrWatts,String.valueOf(nrWatts));
    }

    public void setMapNrMetroCubicoAgua(String nrMetroCubicoAgua) {
        map.put(DF_nrMetroCubicoAgua,String.valueOf(nrMetroCubicoAgua));
    }

    public void setMapCdResidencia(String cdResidencia) {
        map.put(DF_cdResidencia,String.valueOf(cdResidencia));
    }

    public void popularMap() {
        map.put(DF_ID, id);

        map.put(DF_dtInicioMedicao, formatarData(dtInicioMedicao));
        map.put(DF_dtUltimaMedicao,formatarData(dtUltimaMedicao));
        map.put(DF_vlrGastoAgua, String.valueOf(vlrGastoAgua));
        map.put(DF_vlrGastLuz,String.valueOf(vlrGastLuz));
        map.put(DF_nrWatts, String.valueOf(nrWatts));
        map.put(DF_nrMetroCubicoAgua, String.valueOf(nrMetroCubicoAgua));
        map.put(DF_cdResidencia, String.valueOf(cdResidencia));
    }

    public void getMapInstance()
    {
        map = new HashMap<String, String>();
        map.put("classe", GastoAtual.class.getName());
    }
    private String formatarData(Date data)
    {
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");


        String sDate;
        Date newDate;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH);
        int ano = calendar.get(GregorianCalendar.YEAR);
        sDate = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(ano);
//            try {
//                newDate = df.parse(sDate);
//
//            } catch (Exception e) {
//                newDate = null;
//            }
//            return newDate.toString();
        return sDate;

    }
}
