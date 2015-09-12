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

    private void popularMap(String acao)
    {
        map = new HashMap<String, String>();
        map.put(DF_ID,id);
        map.put(DF_FG_LOGAR_AUTOMATICAMENTE,String.valueOf(fg_logar_automaticamente));
        map.put(DF_IND_TIPO_ATUALIZACAO,String.valueOf(indTipoAtualizacao));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe",  ConfiguracaoSistema.class.getName());
    }

    public List<EntidadeDominio> operar(Context context, boolean fgSql, String operacao)
    {
        Session session = Session.getInstance();
        if (fgSql)
            session.setContext(context);
        else
            session.setContext(null);
        //
        Servlet servlet = new Servlet();
        List<EntidadeDominio> list = new LinkedList<EntidadeDominio>();
        popularMap(operacao);
       // popularMap(configuracaoSistema, Servlet.DF_CONSULTAR, ConfiguracaoSistema.class.getName());
        Resultado resultado = servlet.doPost(map);
        list = resultado.getEntidades();
        return list;
    }

}
