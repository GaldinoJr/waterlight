package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Luz;

import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public class LuzViewHelper implements IViewHelper{


    private Luz luz;

    public EntidadeDominio getEntidade(Map request) {
        luz = new Luz();
        luz.setId((String) request.get(luz.DF_ID));
        return luz;
    }

    public EntidadeDominio setView(EntidadeDominio entidade, Map response) {
        return null;
    }
}
