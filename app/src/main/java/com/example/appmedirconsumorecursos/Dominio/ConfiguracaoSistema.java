package com.example.appmedirconsumorecursos.Dominio;

import android.content.Context;

import com.example.appmedirconsumorecursos.Controle.Servlet.Servlet;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Galdino on 02/09/2015.
 */
public class ConfiguracaoSistema extends EntidadeDominio{
    public static final String DF_FG_LOGAR_AUTOMATICAMENTE = "fg_logar_automaticamente";
    public static final String DF_IND_TIPO_ATUALIZACAO = "ind_tipo_atualizacao";
    // Atualiza o app automaticamente conforme parametrização do sistema
    // ind_tipo_atualizacao = 1 - Hora
    //                        2 - Dia
    //                        3 - Mês
    //
    private int fg_logar_automaticamente;
    private int indTipoAtualizacao;

    // sets

    public void setIndTipoAtualizacao(int indTipoAtualizacao) {
        this.indTipoAtualizacao = indTipoAtualizacao;
    }

    public void setFgLogarAutomaticamente(int fg_logar_automaticamente) {
        this.fg_logar_automaticamente = fg_logar_automaticamente;
    }

    // gets
    public int getIndTipoAtualizacao() {
        return indTipoAtualizacao;
    }

    public int getFgLogarAutomaticamente() {
        return fg_logar_automaticamente;
    }

    // Gets e sets do mapa
    // sets
    public void setMapIndTipoAtualizacao(int indTipoAtualizacao) {
        map.put(DF_IND_TIPO_ATUALIZACAO,String.valueOf(indTipoAtualizacao));
    }

    public void setMapFgLogarAutomaticamente(int fg_logar_automaticamente) {
        map.put(DF_FG_LOGAR_AUTOMATICAMENTE,String.valueOf(fg_logar_automaticamente));
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
