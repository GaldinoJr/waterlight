package com.example.appmedirconsumorecursos.Core.command.Impl;

import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.util.List;

/**
 * Created by Galdino on 20/08/2015.
 */
public class AlterarCommand extends AbstractCommand {
    public Resultado execute(EntidadeDominio entidade) {
        return fachada.alterar(entidade);
    }
    public List<EntidadeDominio> consulta(EntidadeDominio entidade) {
        return null;
    }
}
