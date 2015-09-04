package com.example.appmedirconsumorecursos.Core.impl.SqlDAO;

import com.example.appmedirconsumorecursos.Core.IDAO;

/**
 * Created by Galdino on 29/08/2015.
 */
public abstract class AbstractSqlDAO implements IDAO{
        protected String DATABASE_NAME;
        protected String nm_tabela; // sempre colocar IF NOT EXISTS na criacao da tabela
        protected String sqlCriarTabela;
        protected String[] colunas; // colunas da tabela fora o id
        protected String[] colunasBusca;
        protected abstract void iniciar();
}
