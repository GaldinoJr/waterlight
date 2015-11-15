package com.example.appmedirconsumorecursos.Dominio;

import java.util.HashMap;

/**
 * Created by Galdino on 02/09/2015.
 */
public class ConfiguracaoSistema extends EntidadeDominio{
    //
    public static final String DF_FG_ATUALIZAR_AUTOMATICAMENTE = "fg_atualizar_automaticamente";
    public static final String DF_FG_LOGAR_AUTOMATICAMENTE = "fg_logar_automaticamente";
    public static final String DF_IND_TIPO_ATUALIZACAO = "ind_tipo_atualizacao";
    // Atualiza o app automaticamente conforme parametrização do sistema
    // ind_tipo_atualizacao = 1 - Hora
    //                        2 - Dia
    //                        3 - Mês
    //
    // 1 = 110
    // 2 = 220
    public static final String DF_IND_TIPO_VOLTAGEM = "ind_tipo_voltagem";
    public static final String DF_VLR_TARIFA_AGUA = "vlr_tarifa_agua";
    public static final String DF_VLR_TARIFA_LUZ = "vlr_tarifa_luz";
    //
    private int fgAtualizarAutomaticamente;
    private int fg_logar_automaticamente;
    private int indTipoAtualizacao;
    private int indTipoVoltagem;
    private double vlrTarifaAgua;
    private double vlrTarifaLuz;

    // sets

    public void setFgAtualizarAutomaticamente(int fgAtualizarAutomaticamente) {
        this.fgAtualizarAutomaticamente = fgAtualizarAutomaticamente;
    }

    public void setIndTipoAtualizacao(int indTipoAtualizacao) {
        this.indTipoAtualizacao = indTipoAtualizacao;
    }

    public void setFgLogarAutomaticamente(int fg_logar_automaticamente) {
        this.fg_logar_automaticamente = fg_logar_automaticamente;
    }

    public void setIndTipoVoltagem(int indTipoVoltagem) {
        this.indTipoVoltagem = indTipoVoltagem;
    }

    public void setVlrTarifaLuz(double vlrTarifaLuz) {
        this.vlrTarifaLuz = vlrTarifaLuz;
    }

    public void setVlrTarifaAgua(double vlrTarifaAgua) {
        this.vlrTarifaAgua = vlrTarifaAgua;
    }

    // gets


    public int getFgAtualizarAutomaticamente() {
        return fgAtualizarAutomaticamente;
    }

    public int getIndTipoAtualizacao() {
        return indTipoAtualizacao;
    }

    public int getFgLogarAutomaticamente() {
        return fg_logar_automaticamente;
    }

    public int getIndTipoVoltagem() {
        return indTipoVoltagem;
    }

    public double getVlrTarifaAgua() {
        return vlrTarifaAgua;
    }

    public double getVlrTarifaLuz() {
        return vlrTarifaLuz;
    }

    // Gets e sets do mapa
    // sets
    public void setMapFgAtualizarAutomaticamente(int fgAtualizarAutomaticamente) {
        map.put(DF_FG_ATUALIZAR_AUTOMATICAMENTE,String.valueOf(fgAtualizarAutomaticamente));
    }

    public void setMapIndTipoAtualizacao(int indTipoAtualizacao) {
        map.put(DF_IND_TIPO_ATUALIZACAO,String.valueOf(indTipoAtualizacao));
    }

    public void setMapFgLogarAutomaticamente(int fg_logar_automaticamente) {
        map.put(DF_FG_LOGAR_AUTOMATICAMENTE,String.valueOf(fg_logar_automaticamente));
    }

    public void setMapIndTipoVoltagem(int IndTipoVoltagem) {
        map.put(DF_IND_TIPO_VOLTAGEM,String.valueOf(IndTipoVoltagem));
    }
    public void setMapVlrTarifaAgua(double VlrTarifaAgua) {
        map.put(DF_VLR_TARIFA_AGUA,String.valueOf(VlrTarifaAgua));
    }
    public void setMapVlrTarifaLuz(double VlrTarifaLuz) {
        map.put(DF_VLR_TARIFA_LUZ,String.valueOf(VlrTarifaLuz));
    }

    public void getMapInstance()
    {
        map = new HashMap<String, String>();
//        map.put(DF_ID,"");
//        map.put(DF_FG_LOGAR_AUTOMATICAMENTE,"");
//        map.put(DF_IND_TIPO_ATUALIZACAO,"");
//        map.put("operacao", "");          // indica a operação que está sendo realizada
        map.put("classe",  ConfiguracaoSistema.class.getName());
    }
}
