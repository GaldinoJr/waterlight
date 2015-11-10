package com.example.appmedirconsumorecursos.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Galdino on 09/11/2015.
 */
public class BroadcastPadrao extends BroadcastReceiver {
    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private Residencia residencia;
    private Controler controler;
    private Session session;
    private ConfiguracaoSistema configSistema;
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
       this.context = context;
       // Usuario objUsuario = new Usuario();
        //objUsuario.carregar(context);
        //intent.putExtra("codigoUsuario", objUsuario.getCodigoUsuario());
        instanciarClasses(true); // consulta no banco interno
        residencia.popularMap(residencia,"consultar", Residencia.class.getName());
        resultado = controler.doPost(residencia.getMap());
        listEntDom = resultado.getEntidades();
        if(listEntDom != null) // Achou alguma casa cadastrada com o login e senha digitado?
        {
            session.setResidencia((Residencia) listEntDom.get(0));
            configSistema = new ConfiguracaoSistema();
            configSistema.getMapInstance();
            configSistema.setMapId(session.getResidencia().getId());
            listEntDom = configSistema.operar(context, true, Controler.DF_CONSULTAR);
            if(listEntDom != null) // Achou alguma casa cadastrada na config do sitema?
            {
                configSistema = (ConfiguracaoSistema) listEntDom.get(0);
                // ******VERIFICAR NA CONFIG SE VAI ATUALIZAR POR DIA, HORA, OU MES

                intent = new Intent("SERVICO_NOVIDADES");

                context.startService(intent);
            }
        }
    }
    private void instanciarClasses(boolean fgSql)
    {
        session = Session.getInstance();
        if(fgSql)
            session.setContext(context);
        else
            session.setContext(null);
        listEntDom = new LinkedList<EntidadeDominio>();
        resultado = new Resultado();
        residencia = new Residencia();
        controler = new Controler();
    }
}
