package com.example.appmedirconsumorecursos.Dominio;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Galdino on 24/10/2015.
 */
public class GastoHora extends EntidadeDominio {
    public final static String
            DF_vlrGastoAgua = "vlr_gasto_agua",
            DF_vlrGastLuz = "vlr_gasto_luz",
            DF_nrWatts = "nr_watts",
            DF_nrMetroCubicoAgua = "nr_metro_cubico_agua",
            DF_dt_inclusao = "dt_inclusao",
            DF_cdResidencia = "cd_residencia",
            DF_NOME_TABELA = "tb_gasto_hora",
            DF_CD_TABELA = "cd_gasto_hora",
            DF_NOME_PHP = "cadGastoHora.php";

    public final static String DF_AUX_DATA = "AUX_dt_inclusao";

    // Filtros
    public final static String  DF_FILTRO_indTipoComparacaoMaiorConsumo = "indTipoComparacaoMaiorConsumo",
            DF_FILTRO_fgCompararOutrasResidencias = "fgCompararOutrasResidencias",
            DF_FILTRO_nrMorador = "nrMorador",
            DF_FILTRO_nrComodo = "nrComodo",
            DF_FILTRO_fgTodosRegistros = "filtro_fgTodosRegistros",
            DF_FILTRO_idRecurso = "filtro_idRecurso";

    private Date dtInclusao;
    private double vlrGastoAgua,
            vlrGastLuz,
            nrWatts,
            nrMetroCubicoAgua;
    private Integer cdResidencia;

    private String sDtInclusao;

    // Filtros
    private int fitro_indTipoComparacaoMaiorConsumo,
            fitro_fgCompararOutrasResidencias,
            fitro_nrMorador,
            fitro_nrComodo,
            filtro_fgTodosRegistros,
            filtro_idRecurso;

    // SETS

    public void setsDtInclusao(String sDtInclusao) {
        this.sDtInclusao = sDtInclusao;
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

    // Filtros

    public void setFitro_indTipoComparacaoMaiorConsumo(int fitro_indTipoComparacaoMaiorConsumo) {
        this.fitro_indTipoComparacaoMaiorConsumo = fitro_indTipoComparacaoMaiorConsumo;
    }

    public void setFiltro_fgTodosRegistros(int filtro_fgTodosRegistros) {
        this.filtro_fgTodosRegistros = filtro_fgTodosRegistros;
    }

    public void setFiltro_idRecurso(int filtro_idRecurso) {
        this.filtro_idRecurso = filtro_idRecurso;
    }

    public void setFitro_fgCompararOutrasResidencias(int fitro_fgCompararOutrasResidencias) {
        this.fitro_fgCompararOutrasResidencias = fitro_fgCompararOutrasResidencias;
    }

    public void setFitro_nrMorador(int fitro_nrMorador) {
        this.fitro_nrMorador = fitro_nrMorador;
    }

    public void setFitro_nrComodo(int fitro_nrComodo) {
        this.fitro_nrComodo = fitro_nrComodo;
    }

    // GETS

    public String getsDtInclusao() {
        return sDtInclusao;
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

    // Filtros

    public int getFitro_fgCompararOutrasResidencias() {
        return fitro_fgCompararOutrasResidencias;
    }

    public int getFitro_nrMorador() {
        return fitro_nrMorador;
    }

    public int getFitro_nrComodo() {
        return fitro_nrComodo;
    }

    public int getFiltro_idRecurso() {
        return filtro_idRecurso;
    }

    public int getFitro_indTipoComparacaoMaiorConsumo() {
        return fitro_indTipoComparacaoMaiorConsumo;
    }

    public int getFiltro_fgTodosRegistros() {
        return filtro_fgTodosRegistros;
    }

    // SETS DO MAPA

    public void setMapsDtInclusao(String sDtInclusao)
    {
        map.put(DF_AUX_DATA, sDtInclusao);
    }

    public void setMapDtInclusao(String dtInicioMedicao) {
        map.put(DF_dt_inclusao,String.valueOf(dtInicioMedicao));
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

    // Filtros

    public void setMapFitro_indTipoComparacaoMaiorConsumo(String Fitro_indTipoComparacaoMaiorConsumo) {
        map.put(DF_FILTRO_indTipoComparacaoMaiorConsumo,String.valueOf(Fitro_indTipoComparacaoMaiorConsumo));
    }

    public void setMapFitro_fgCompararOutrasResidencias(String fitro_fgCompararOutrasResidencias) {
        map.put(DF_FILTRO_fgCompararOutrasResidencias,String.valueOf(fitro_fgCompararOutrasResidencias));
    }

    public void setMapFitro_nrMorador(String Fitro_nrMorador) {
        map.put(DF_FILTRO_nrMorador,String.valueOf(Fitro_nrMorador));
    }

    public void setMapFitro_nrComodo(String Fitro_nrComodo) {
        map.put(DF_FILTRO_nrComodo,String.valueOf(Fitro_nrComodo));
    }

    public void setMapFiltro_fgTodosRegistros(String filtro_fgTodosRegistros) {
        map.put(DF_FILTRO_fgTodosRegistros,String.valueOf(filtro_fgTodosRegistros));
    }

    public void setMapFiltro_idRecurso(String filtro_idRecurso) {
        map.put(DF_FILTRO_idRecurso,String.valueOf(filtro_idRecurso));
    }


    public void popularMap() {
        map.put(DF_ID, id);

        map.put(DF_dt_inclusao, formatarData(dtInclusao));
        map.put(DF_vlrGastoAgua, String.valueOf(vlrGastoAgua));
        map.put(DF_vlrGastLuz,String.valueOf(vlrGastLuz));
        map.put(DF_nrWatts, String.valueOf(nrWatts));
        map.put(DF_nrMetroCubicoAgua, String.valueOf(nrMetroCubicoAgua));
        map.put(DF_cdResidencia, String.valueOf(cdResidencia));
    }

    public void getMapInstance()
    {
        map = new HashMap<String, String>();
        map.put("classe", GastoHora.class.getName());
    }
    private String formatarData(Date data)
    {
        //String a = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(data));
        String sDate;
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
        sDate = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(ano) + sHora;
        return sDate;

    }
}
