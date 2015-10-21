package com.example.appmedirconsumorecursos.Dominio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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

    private Integer cdResidencia;

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

    public void setCdResidencia(Integer cdResidencia) {
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

    public Integer getCdResidencia() {
        return cdResidencia;
    }

    // SETS DO MAPA

    public void setMapDtInicioMedicao(String dtUltimoRegistroMedicao) {
        map.put(DF_dt_ultimo_registro_dia,String.valueOf(dtUltimoRegistroMedicao));
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
        map.put(DF_ID,id);
        map.put(DF_dt_ultimo_registro_dia, formatarData(dtUltimaRegistroDia));
        map.put(DF_vlrGastoAgua, String.valueOf(vlrGastoAgua));
        map.put(DF_vlrGastLuz,String.valueOf(vlrGastLuz));
        map.put(DF_nrWatts, String.valueOf(nrWatts));
        map.put(DF_nrMetroCubicoAgua, String.valueOf(nrMetroCubicoAgua));
        map.put(DF_cdResidencia, String.valueOf(cdResidencia));
    }
    public void getMapInstance()
    {
        map = new HashMap<String, String>();
        map.put("classe", GastoHoje.class.getName());
    }
    private String formatarData(Date data)
    {
        String sDate;
        Date newDate;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH);
        mes = mes + 1;
        int ano = calendar.get(GregorianCalendar.YEAR);

        int hora = calendar.get(GregorianCalendar.HOUR);
        // 0 = dia
        // 1 = noite
        int amPm = calendar.get(GregorianCalendar.AM_PM);

        if(amPm == 1)
        {
            if(hora>0 && hora < 13)
            {
                if(hora == 12)
                    hora = 0;
                else
                    hora += 12;
            }
        }
        String sHora = " ";
        if(hora<=9)
        {
            sHora += "0";
        }
        String sMin=":";
        int min = calendar.get(GregorianCalendar.MINUTE);
        if(min <10)
            sMin += "0";
        sMin += String.valueOf(min);
        sHora += String.valueOf(hora) + sMin + ":00";
        sDate = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(ano) + sHora;
        return sDate;

    }
}
