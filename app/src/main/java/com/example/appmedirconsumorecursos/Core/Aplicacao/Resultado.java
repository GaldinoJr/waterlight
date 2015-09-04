package com.example.appmedirconsumorecursos.Core.Aplicacao;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.util.List;

/**
 * Created by Galdino on 19/08/2015.
 */
public class Resultado extends EntidadeAplicacao {
    private String msg;
    private List<EntidadeDominio> entidades;
    /**
     * M?todo de recupera??o do campo msg
     *
     * @return valor do campo msg
     */
    public String getMsg() {
        return msg;
    }
    /**
     * Valor de msg atribu?do a msg
     *
     * @param msg Atributo da Classe
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    /**
     * M?todo de recupera??o do campo entidades
     *
     * @return valor do campo entidades
     */
    public List<EntidadeDominio> getEntidades() {
        return entidades;
    }
    /**
     * Valor de entidades atribu?do a entidades
     *
     * @param entidades Atributo da Classe
     */
    public void setEntidades(List<EntidadeDominio> entidades) {
        this.entidades = entidades;
    }
}
