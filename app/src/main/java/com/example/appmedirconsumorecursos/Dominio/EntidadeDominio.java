package com.example.appmedirconsumorecursos.Dominio;

import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public class EntidadeDominio implements IEntidade{
    public String DF_ID = "ID";
    protected String id;
    protected Map<String, String> map;

    // sets
    public void setId(String id) {
        this.id = id;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    // gets
    public String getId() {
        return id;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void popularMap(EntidadeDominio entidadeDominio, String acao, String nomeClasse) {

    }
}
