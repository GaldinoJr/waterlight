package com.example.appmedirconsumorecursos.Servicos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Galdino on 14/11/2015.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, AtualizarAutomatico.class);
        context.startService(startServiceIntent);
    }
}
