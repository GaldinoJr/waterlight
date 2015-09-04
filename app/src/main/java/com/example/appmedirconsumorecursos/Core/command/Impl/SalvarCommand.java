package com.example.appmedirconsumorecursos.Core.command.Impl;

import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

/**
 * Created by Galdino on 20/08/2015.
 */
public class SalvarCommand extends AbstractCommand {
    public Resultado execute(EntidadeDominio entidade)
    {
        return fachada.salvar(entidade);
    }
}
