package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoHora;
import com.example.appmedirconsumorecursos.Dominio.Residencia;
import com.example.appmedirconsumorecursos.R;

import java.util.LinkedList;
import java.util.List;

public class Tela_funcao_implantacao extends Activity implements View.OnClickListener {
    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private Residencia residencia;
    private Controler controler;
    private Session session;
    private ConfiguracaoSistema configSistema;

    private GastoAtual gastoAtual;
    private GastoHoje gastoHoje;
    private GastoHora gastoHora;
    private static final String TAG = "Função implantação";

    private Button btnCarregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcao_implantacao);
        //
        btnCarregar = (Button)findViewById(R.id.btnCarregar);
        // indica que podem ser clicados os botões
        btnCarregar.setOnClickListener(this);
        session = Session.getInstance();
    }

    @Override
    public void onClick(View view)
    {
        if(view == btnCarregar)
        {
            // Vai carregar todo o banco interno com o conteudo do servidor
            atualizarGastoHora();
            atualizarGastoHoje();
            atualizarGastoAtual();
            Toast.makeText(Tela_funcao_implantacao.this, "Registros atualizados, app pronto para uso.", Toast.LENGTH_LONG).show();
        }
    }
    private void atualizarGastoHoje() {
        int i, count = 0;
        listEntDom = new LinkedList<EntidadeDominio>();
        gastoHoje = new GastoHoje();
        //gastoHoje.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
        gastoHoje.getMapInstance();
        gastoHoje.setMapCdResidencia(session.getResidencia().getId());
        gastoHoje.setMapFiltro_fgTodosRegistros("1");
        Log.i(TAG, "consultar gasto hoje externo");
        listEntDom = gastoHoje.operar(this, false, Controler.DF_CONSULTAR);
        if (listEntDom != null) // Achou algum registro no servidor?
        {
            for (i = 0; i < listEntDom.size(); i++) {
                GastoHoje g = (GastoHoje) listEntDom.get(i);
                g.getMapInstance();
                g.popularMap();
                // Então atualiza o banco interno
                g.operar(this, true, Controler.DF_SALVAR);
                count++;
            }
        }
        Log.i(TAG,"Qtd itens afetados: " + count);
    }
    private void atualizarGastoAtual()
    {
        int i, count = 0;
        listEntDom = new LinkedList<EntidadeDominio>();
        gastoAtual = new GastoAtual();
        //gastoAtual.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
        //gastoAtual.popularMap(gastoAtual, "consultar", GastoAtual.class.getName());
        //resultado = controler.doPost(gastoAtual.getMap());
        gastoAtual.getMapInstance();
        gastoAtual.setMapCdResidencia(session.getResidencia().getId());
        gastoAtual.setMapFiltro_fgTodosRegistros("1");
        Log.i(TAG, "consultar gasto atual externo");
        listEntDom = gastoAtual.operar(this,false, Controler.DF_CONSULTAR);
        if(listEntDom != null) // Achou algum registro no servidor?
        { // Sim
            // Então atualiza o banco interno
            for (i = 0; i < listEntDom.size(); i++) {
                GastoAtual g = (GastoAtual) listEntDom.get(i);
                g.getMapInstance();
                g.popularMap();
                // salva
                g.operar(this, true, Controler.DF_SALVAR);
                count++;
            }
        }
        Log.i(TAG,"Qtd itens afetados: " + count);
    }
    private void atualizarGastoHora()
    {
        int i, count = 0;
        listEntDom = new LinkedList<EntidadeDominio>();
        gastoHora = new GastoHora();
        //
        gastoHora.getMapInstance();
        gastoHora.setMapCdResidencia(session.getResidencia().getId());
        gastoHora.setMapFiltro_fgTodosRegistros("1");
        Log.i(TAG, "consultar EXTERNO gasto HORA");
        listEntDom = gastoHora.operar(this,false, Controler.DF_CONSULTAR);
        if(listEntDom != null) // Achou algum registro no servidor?
        {
            for (i = 0; i < listEntDom.size(); i++) {
                GastoHora g = (GastoHora) listEntDom.get(i);
                g.getMapInstance();
                g.popularMap();

                g.operar(this, true, Controler.DF_SALVAR);
                count++;
            }
        }
        Log.i(TAG,"Qtd itens afetados: " + count);
    }
    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a página anterior
        Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e depois a próxima tela( a que vai ser chamada)
        intent.setClass(Tela_funcao_implantacao.this, Tela_configuracao_aplicativo.class);
        //intent.putExtra("absClasse", absFactoryRecurso);
        startActivity(intent); // chama a próxima tela
        finish();
    }
}
