package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.Agua;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Luz;

import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public class AguaViewHelper implements IViewHelper {


    private Agua agua;

    public EntidadeDominio getEntidade(Map request) {
        agua = new Agua();
        agua.setId((String) request.get(agua.DF_ID));
        return agua;
    }

    public EntidadeDominio setView(EntidadeDominio entidade, Map response) {
        return null;
    }
}
