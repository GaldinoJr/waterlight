package com.example.appmedirconsumorecursos.Core.impl.Controle;

import android.content.Context;

import com.example.appmedirconsumorecursos.Dominio.Residencia;

/**
 * Created by Galdino on 29/08/2015.
 */
//http://pt.stackoverflow.com/questions/54588/como-criar-manter-variavel-global-em-java-para-login
public class Session {
    // padrão design pattern: singleton.
    private static Session session;
    private static Context context;
    private Residencia residencia;

    // Construtor privado (suprime o construtor público padrão).
    private Session() {}

    // Método público estático de acesso único ao objeto!
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
