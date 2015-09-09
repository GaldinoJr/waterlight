package com.example.appmedirconsumorecursos.Core.impl.Controle;

import android.content.Context;

import com.example.appmedirconsumorecursos.Dominio.Residencia;

/**
 * Created by Galdino on 29/08/2015.
 */
//http://pt.stackoverflow.com/questions/54588/como-criar-manter-variavel-global-em-java-para-login
public class Session {
    // padr�o design pattern: singleton.
    private static Session session;
    private static Context context;
    private Residencia residencia;

    // Construtor privado (suprime o construtor p�blico padr�o).
    private Session() {}

    // M�todo p�blico est�tico de acesso �nico ao objeto!
    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    // gets
    public Context getContext() {
        return context;
    }

    public Residencia getResidencia() {
        return residencia;
    }

    // sets

    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
