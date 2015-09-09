package com.example.appmedirconsumorecursos.Dominio;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galdino on 02/09/2015.
 */
public class ConfiguracaoSistema extends EntidadeDominio{
    public static final String DF_FG_LOGAR_AUTOMATICAMENTE = "fg_logar_automaticamente";
    private int fg_logar_automaticamente;

    // sets
    public void setFgLogarAutomaticamente(int fg_logar_automaticamente) {
        this.fg_logar_automaticamente = fg_logar_automaticamente;
    }

    // gets
    public int getFgLogarAutomaticamente() {
        return fg_logar_automaticamente;
    }

    public void popularMap(EntidadeDominio entidadeDominio,String acao, String nomeClasse)
    {
        ConfiguracaoSistema configSistema = (ConfiguracaoSistema)entidadeDominio;
        map = new HashMap<String, String>();
        map.put(configSistema.DF_ID, configSistema.getId());
        map.put(configSistema.DF_FG_LOGAR_AUTOMATICAMENTE,String.valueOf(configSistema.getFgLogarAutomaticamente()));
        map.put("operacao", acao);          // indica a operação que está sendo realizada
        map.put("classe", nomeClasse);
    }

}
