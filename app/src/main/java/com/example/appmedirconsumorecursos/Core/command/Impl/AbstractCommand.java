package com.example.appmedirconsumorecursos.Core.command.Impl;

import com.example.appmedirconsumorecursos.Core.IFachada;
import com.example.appmedirconsumorecursos.Core.command.ICommand;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Fachada;

/**
 * Created by Galdino on 19/08/2015.
 */
public abstract class AbstractCommand implements ICommand {
    protected IFachada fachada = new Fachada();
}
