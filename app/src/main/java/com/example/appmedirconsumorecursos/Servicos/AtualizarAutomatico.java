package com.example.appmedirconsumorecursos.Servicos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Galdino on 13/11/2015.
 */

//**** exemplo ***
//http://javatechig.com/android/android-service-example
public class AtualizarAutomatico extends Service {

    private static final String TAG = "Atualizar automático";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (int i = 0; i < 5; i++) {
                    try {
                        // colocar o tempo conforme a config do sistema*********
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                       // Toast.makeText(Tela_configuracao_aplicativo., "Serviço rodando!", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Service running");
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
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
