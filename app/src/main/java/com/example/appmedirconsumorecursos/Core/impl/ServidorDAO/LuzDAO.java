package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.util.List;

/**
 * Created by Galdino on 19/08/2015.
 */
public class LuzDAO extends AbstractJdbcDAO
{

    public LuzDAO()
    {
        super("tb_clientes", "id_cliente", "");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }
}
