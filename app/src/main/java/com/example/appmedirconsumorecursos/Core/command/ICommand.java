package com.example.appmedirconsumorecursos.Core.command;

import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

/**
 * Created by Galdino on 19/08/2015.
 */
public interface ICommand {
    public Resultado execute(EntidadeDominio entidade);
}
