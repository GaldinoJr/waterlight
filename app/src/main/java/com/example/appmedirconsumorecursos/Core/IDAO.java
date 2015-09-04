package com.example.appmedirconsumorecursos.Core;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.util.List;

/**
 * Created by Galdino on 20/08/2015.
 */
public interface IDAO {
    public void salvar(EntidadeDominio entidade);
    public void alterar(EntidadeDominio entidade);
    public void excluir(EntidadeDominio entidade);
    public List<EntidadeDominio> consultar(EntidadeDominio entidade);
}
