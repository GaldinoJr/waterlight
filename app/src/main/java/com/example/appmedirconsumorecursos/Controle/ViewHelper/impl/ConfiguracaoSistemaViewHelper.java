package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;

import java.util.Map;

/**
 * Created by Galdino on 02/09/2015.
 */
public class ConfiguracaoSistemaViewHelper implements IViewHelper {


    private ConfiguracaoSistema configSistema;

    public EntidadeDominio getEntidade(Map request)
    {
        try {
            configSistema = new ConfiguracaoSistema();
            configSistema.setId((String) request.get(configSistema.DF_ID));
            if(request.get(configSistema.DF_FG_LOGAR_AUTOMATICAMENTE) != null)
                configSistema.setFgLogarAutomaticamente(Integer.parseInt((String) request.get(configSistema.DF_FG_LOGAR_AUTOMATICAMENTE)));
            if(request.get(configSistema.DF_IND_TIPO_ATUALIZACAO) != null)
                configSistema.setIndTipoAtualizacao(Integer.parseInt((String) request.get(configSistema.DF_IND_TIPO_ATUALIZACAO)));

            if(request.get(configSistema.DF_IND_TIPO_VOLTAGEM) != null)
                configSistema.setIndTipoVoltagem(Integer.parseInt((String) request.get(configSistema.DF_IND_TIPO_VOLTAGEM)));
            if(request.get(configSistema.DF_VLR_TARIFA_AGUA) != null)
                configSistema.setVlrTarifaAgua(Double.parseDouble((String) request.get(configSistema.DF_VLR_TARIFA_AGUA)));
            if(request.get(configSistema.DF_VLR_TARIFA_LUZ) != null)
                configSistema.setVlrTarifaLuz(Double.parseDouble((String) request.get(configSistema.DF_VLR_TARIFA_LUZ)));
            if(request.get(configSistema.DF_FG_ATUALIZAR_AUTOMATICAMENTE) != null)
                configSistema.setFgAtualizarAutomaticamente(Integer.parseInt((String) request.get(configSistema.DF_FG_ATUALIZAR_AUTOMATICAMENTE)));
            return configSistema;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public EntidadeDominio setView(EntidadeDominio entidade, Map response) {
        return null;
    }
}