package com.example.appmedirconsumorecursos.Controle.ViewHelper2;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public interface IViewHelper {
    public EntidadeDominio getEntidade(Map<String, String> request);

    public EntidadeDominio setView(EntidadeDominio entidade,
                                   Map<String, String> response);
}
