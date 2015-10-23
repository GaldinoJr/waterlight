package com.example.appmedirconsumorecursos.Core.impl.Controle;

import android.content.Context;

import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

/**
 * Created by Galdino on 29/08/2015.
 */
//http://pt.stackoverflow.com/questions/54588/como-criar-manter-variavel-global-em-java-para-login
public class Session {
    // padrão design pattern: singleton.
    private static Session session;
    private static Context context; //Para dizer na FACHADA se á uma requisição interna ou externa
    private Residencia residencia;
    private ConfiguracaoSistema configuracaoSistema;

    // Construtor privado (suprime o construtor público padrão).
    private Session() {}

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

    public ConfiguracaoSistema getConfiguracaoSistema() {
        return configuracaoSistema;
    }

    // sets

    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setConfiguracaoSistema(ConfiguracaoSistema configuracaoSistema) {
        this.configuracaoSistema = configuracaoSistema;
    }
}
