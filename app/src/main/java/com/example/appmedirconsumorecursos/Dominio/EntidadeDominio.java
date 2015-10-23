package com.example.appmedirconsumorecursos.Dominio;

import android.content.Context;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;

import java.util.LinkedList;
import java.util.List;
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
    // Gets e Sets do mapa
    // sets
    public void setMapId(String id) {
        map.put(DF_ID,id);
    }

    public List<EntidadeDominio> operar(Context context, boolean fgSql, String operacao)
    {
        map.put("operacao", operacao);          // indica a operação que está sendo realizada
        Session session = Session.getInstance();
        if (fgSql)
            session.setContext(context);
        else
            session.setContext(null);
        //
        Controler controler = new Controler();
        List<EntidadeDominio> list = new LinkedList<EntidadeDominio>();
        Resultado resultado = controler.doPost(map);
        list = resultado.getEntidades();
        return list;
    }
}
