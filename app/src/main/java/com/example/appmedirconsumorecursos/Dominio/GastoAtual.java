package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoAtual  extends EntidadeDominio {
    private Session session;
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
    private String  sDtInicioMedicao,
                    sDtUltimaMedicao;

    //
    public final static String DF_FILTRO_fgTodosRegistros = "filtro_fgTodosRegistros";
    private int filtro_fgTodosRegistros;

    // SETS

    public void setFiltro_fgTodosRegistros(int filtro_fgTodosRegistros) {
        this.filtro_fgTodosRegistros = filtro_fgTodosRegistros;
    }

    public void setsDtInicioMedicao(String sDtInicioMedicao) {
        this.sDtInicioMedicao = sDtInicioMedicao;
    }

    public void setsDtUltimaMedicao(String sDtUltimaMedicao) {
        this.sDtUltimaMedicao = sDtUltimaMedicao;
    }

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


    public int getFiltro_fgTodosRegistros() {
        return filtro_fgTodosRegistros;
    }

    public String getsDtInicioMedicao() {
        return sDtInicioMedicao;
    }

    public String getsDtUltimaMedicao() {
        return sDtUltimaMedicao;
    }

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

    public void setMapFiltro_fgTodosRegistros(String filtro_fgTodosRegistros) {
        map.put(DF_FILTRO_fgTodosRegistros,String.valueOf(filtro_fgTodosRegistros));
    }

    public void setMapsDtInicioMedicao(String dtInicioMedicao) {
        map.put(DF_dtInicioMedicao, sDtInicioMedicao);
    }

    public void setMapsDtUltimaMedicao(String dtUltimaMedicao) {
        map.put(DF_dtUltimaMedicao,sDtUltimaMedicao);
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
        map.put(DF_FILTRO_fgTodosRegistros, String.valueOf(filtro_fgTodosRegistros));
    }

    public void getMapInstance()
    {
        map = new HashMap<String, String>();
        map.put("classe", GastoAtual.class.getName());
        session = Session.getInstance();
        session.setNameInstanceClass(GastoAtual.class.getName());
    }
    private String formatarData(Date data)
    {
        //String a = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(data));
        String sDate;
        String sDia = "";
        String sMes = "";

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        if (dia > 0) {
            if (dia < 10) {
                sDia = "0";
            }
        }
        sDia += dia;

        int mes = calendar.get(GregorianCalendar.MONTH);
        mes = mes + 1;
        if (mes > 0) {
            if (mes < 10) {
                sMes = "0";
            }
        }
        sMes += mes;

        int ano = calendar.get(GregorianCalendar.YEAR);

        int hora = calendar.get(GregorianCalendar.HOUR);
        // 0 = dia
        // 1 = noite
        int amPm = calendar.get(GregorianCalendar.AM_PM);

        if(amPm == 1)
        {
            if(hora>0 && hora < 13)
            {
                if(hora > 11)
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
        sDate = String.valueOf(ano) + "-" + sMes + "-" + sDia + sHora;
        return sDate;

    }
}
