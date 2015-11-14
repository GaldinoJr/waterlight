package com.example.appmedirconsumorecursos.Servicos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Galdino on 13/11/2015.
 */

//**** exemplo ***
//http://javatechig.com/android/android-service-example
public class AtualizarAutomatico extends Service {

    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private Residencia residencia;
    private Controler controler;
    private Session session;
    private ConfiguracaoSistema configSistema;

    private GastoAtual gastoAtual;
    private GastoHoje gastoHoje;

    private static final String TAG = "Atualizar automático";
    private boolean isRunning  = false;

    private int qtdTempoParaAtualizar;

    public void onCreate() {

        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int deslisgar;
        deslisgar=(Integer) intent.getExtras().get("delisgarServico");
//        dados = intent.getExtras();
//        deslisgar = dados.getInt("delisgarServico");
        if(deslisgar == 1) {
            stopSelf();
            return 0;
        }
        Log.i(TAG, "Service onStartCommand");
        if(isRunning) {
            qtdTempoParaAtualizar = tempoParaAtualizacao();
            //Creating new thread for my service
            //Always write your long running tasks in a separate thread, to avoid ANR
            if (qtdTempoParaAtualizar > 0)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        //Your logic that service will perform will be placed here
                        //In this example we are just looping and waits for 1000 milliseconds in each loop.
                        //for (int i = 0; i < 5; i++)
                        // ***** como colocar um while true sem dar pau
                        // while(true)
                            while (!Thread.currentThread().isInterrupted()) {
                                if(isRunning) {
                                    try {
                                        // Atualiza o banco interno
                                        atualizarGastos();
                                        // Espera o tempo da configuração para atualizar novamente
                                        Thread.sleep(qtdTempoParaAtualizar);

                                    } catch (Exception e) {

                                    }
                                    Log.i(TAG, "Service running");
                                }
                                else {
                                    Log.i(TAG, "Serviço pausado");
                                    Thread.currentThread().interrupt();
                                    //this.finish();
                                }
                            }
                        //Stop service once it finishes its task
                        // stopSelf();
                    }
                }).start();
            }
        }
        else
            Log.i(TAG, "Serviço pausado");
        return Service.START_STICKY;
    }

    private int tempoParaAtualizacao()
    {
        int tempo;
        // ind_tipo_atualizacao = 1 - Hora
        //                        2 - Dia
        //                        3 - Mês
        int indTipoAtualizacao = carregarTipoAtualizacao();
        switch (indTipoAtualizacao)
        {
            case 1:
              //  tempo = 3600000;
                tempo = 10000;
                break;
            case 2:
                tempo = 20000;
              //  tempo = 86400000;
                break;
            case 3:
                tempo = 1000 * descobrirQtdDiasMes();
              //  tempo = 86400000 * descobrirQtdDiasMes();
                break;
            default:
                tempo = 0;
                break;
        }
        return tempo;
    }

    private void atualizarGastos()
    {
        List<EntidadeDominio> listAux;
        instanciarClasses(); // consulta no banco interno
        //gastoHoje.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
        gastoHoje.getMapInstance();
        gastoHoje.setMapCdResidencia(session.getResidencia().getId());
        Log.i(TAG, "consultar 1");
        listEntDom = gastoHoje.operar(this,false, Controler.DF_CONSULTAR);
        if(listEntDom != null) // Achou algum registro no servidor?
        {
            gastoHoje = (GastoHoje) listEntDom.get(0);
            gastoHoje.getMapInstance();
            gastoHoje.popularMap();
            // Verifica se é um novo registro
            listAux = new LinkedList<EntidadeDominio>();
            GastoHoje gastoHojeAux = new GastoHoje();
            gastoHojeAux.getMapInstance();
            gastoHojeAux.setMapCdResidencia(session.getResidencia().getId());
            Log.i(TAG, "consultar 2");
            listAux = gastoHojeAux.operar(this,true, Controler.DF_CONSULTAR);
            Log.i(TAG, "Salvar");
            if(listAux != null) // Achou algum registro no banco interno?
            {
                gastoHojeAux = (GastoHoje) listAux.get(0);
                gastoHojeAux.getMapInstance();
                gastoHojeAux.popularMap();
                if (gastoHoje.getDtUltimaRegistroDia().compareTo(gastoHojeAux.getDtUltimaRegistroDia()) != 0) {
                    // Então atualiza o banco interno
                    gastoHoje.operar(this, true, Controler.DF_SALVAR);
                }
            }
            else
                gastoHoje.operar(this, true, Controler.DF_SALVAR);
        }
        instanciarClasses(); // consulta no banco interno
        //gastoAtual.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
        //gastoAtual.popularMap(gastoAtual, "consultar", GastoAtual.class.getName());
        //resultado = controler.doPost(gastoAtual.getMap());
        gastoAtual.getMapInstance();
        gastoAtual.setMapCdResidencia(session.getResidencia().getId());
        Log.i(TAG, "AAAA");
        listEntDom = gastoAtual.operar(this,false, Controler.DF_CONSULTAR);
        if(listEntDom != null) // Achou algum registro no servidor?
        { // Sim
            // Então atualiza o banco interno
            gastoAtual = (GastoAtual) listEntDom.get(0);
            gastoAtual.getMapInstance();
            gastoAtual.popularMap();
//				// Verifica se é um novo registro
            listAux = new LinkedList<EntidadeDominio>();;
            GastoAtual gastoAtualAux = new GastoAtual();
            gastoAtualAux.getMapInstance();
            gastoAtualAux.setMapCdResidencia(session.getResidencia().getId());
            listAux = gastoAtualAux.operar(this,true, Controler.DF_CONSULTAR);
            if(listAux != null) // Achou algum registro no servidor?
            {
                gastoAtualAux = (GastoAtual) listAux.get(0);
                gastoAtualAux.getMapInstance();
                gastoAtualAux.popularMap();
                if (gastoAtual.getDtUltimaMedicao().compareTo(gastoAtualAux.getDtUltimaMedicao()) != 0) {
                    // Então atualiza o banco interno
                    gastoAtual.operar(this, true, Controler.DF_SALVAR);
                }
            }
            else
                gastoAtual.operar(this, true, Controler.DF_SALVAR);
        }
    }

    private int descobrirQtdDiasMes()
    {
        int qtdDias;
        Date data = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        qtdDias = calendar.getMaximum(GregorianCalendar.DAY_OF_MONTH);
        return qtdDias;
    }
    private int carregarTipoAtualizacao()
    {

        // Verifica se o usuário já foi gravado no banco interno
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
            listEntDom = configSistema.operar(this, true, Controler.DF_CONSULTAR);
            if (listEntDom != null) // Achou alguma casa cadastrada na config do sitema?
            {
                configSistema = (ConfiguracaoSistema) listEntDom.get(0);
                return configSistema.getIndTipoAtualizacao();
            }
        }
        return 0;
    }

    private void instanciarClasses(boolean fgSql)
    {
        session = Session.getInstance();
        if(fgSql)
            session.setContext(this);
        else
            session.setContext(null);
        listEntDom = new LinkedList<EntidadeDominio>();
        resultado = new Resultado();
        residencia = new Residencia();
        controler = new Controler();
    }
    private void instanciarClasses()
    {
        listEntDom = new LinkedList<EntidadeDominio>();
        gastoAtual = new GastoAtual();
        gastoHoje = new GastoHoje();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
