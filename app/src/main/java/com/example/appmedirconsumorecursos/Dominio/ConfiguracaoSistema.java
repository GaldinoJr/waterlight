package com.example.appmedirconsumorecursos.Dominio;

import java.util.HashMap;
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

    public void popularMap(EntidadeDominio entidadeDominio,String acao, String nomeClasse)
    {
        ConfiguracaoSistema configSistema = (ConfiguracaoSistema)entidadeDominio;
        map = new HashMap<String, String>();
        map.put(DF_ID, configSistema.getId());
        map.put(DF_FG_LOGAR_AUTOMATICAMENTE,String.valueOf(configSistema.getFgLogarAutomaticamente()));
        map.put(DF_IND_TIPO_ATUALIZACAO,String.valueOf(configSistema.getIndTipoAtualizacao()));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", nomeClasse);
    }

}
