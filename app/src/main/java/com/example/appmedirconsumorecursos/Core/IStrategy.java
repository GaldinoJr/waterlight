package com.example.appmedirconsumorecursos.Core;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

/**
 * Created by Galdino on 20/08/2015.
 */
public interface IStrategy {
    public String processar(EntidadeDominio entidade);
}
