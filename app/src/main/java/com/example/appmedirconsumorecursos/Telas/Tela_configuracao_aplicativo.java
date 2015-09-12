package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Servlet.Servlet;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;
import com.example.appmedirconsumorecursos.R;

import java.util.LinkedList;
import java.util.List;

public class Tela_configuracao_aplicativo extends Activity implements View.OnClickListener {
    private RadioButton rbHora,
                        rbDia,
                        rbMes;
    private Button btnSalvarConfigApp;
    //
    private Session session;
    private ConfiguracaoSistema configSistema;
    private List<EntidadeDominio> listEntDom;
    //
    private int indTipoAtualizacao;
    private Context contextTelaParaVoltar;
    private Intent dados;
    private AbsRecurso absRecurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracao_aplicativo);
        //
        session = Session.getInstance();
        //
        rbHora = (RadioButton)findViewById(R.id.rbHora);
        rbDia = (RadioButton)findViewById(R.id.rbDia);
        rbMes = (RadioButton)findViewById(R.id.rbMes);
        btnSalvarConfigApp = (Button)findViewById(R.id.btnSalvarConfiguracao);
        btnSalvarConfigApp.setOnClickListener(this);
        contextTelaParaVoltar = session.getContext();
        dados = getIntent(); // Recebe os dados da tela anterior

        //
        /*
        final RadioGroup radioGroupTipoAtualizacao = (RadioGroup)findViewById(R.id.rgTipoAtualizacao);
        radioGroupTipoAtualizacao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override     public void onCheckedChanged(RadioGroup group, int id)
            {     // TODO Auto-generated method stub
                if(id == R.id.rbHora)
                    Toast.makeText(Tela_configuracao_aplicativo.this, "Hora id: "+id, Toast.LENGTH_LONG).show();
                else if(id==R.id.rbDia)
                    Toast.makeText(Tela_configuracao_aplicativo.this, "Diaid: "+id, Toast.LENGTH_LONG).show();
                else if(id==R.id.rbMes)
                    Toast.makeText(Tela_configuracao_aplicativo.this, "Mêsid: "+id, Toast.LENGTH_LONG).show();
            } });
        */
        configSistema = session.getConfiguracaoSistema();
        indTipoAtualizacao = configSistema.getIndTipoAtualizacao();
        switch(indTipoAtualizacao) {
            case 1:
                rbHora.setChecked(true);
                break;
            case 2:
                rbDia.setChecked(true);
                break;
            case 3:
                rbMes.setChecked(true);
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_configuracao_aplicativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSalvarConfigApp)
        {
            final RadioGroup radioGroupTipoAtualizacao = (RadioGroup)findViewById(R.id.rgTipoAtualizacao);
            int idRadioButton = radioGroupTipoAtualizacao.getCheckedRadioButtonId();
            indTipoAtualizacao = 0;
            if(idRadioButton == R.id.rbHora)
                indTipoAtualizacao = 1;
            else if(idRadioButton==R.id.rbDia)
                indTipoAtualizacao = 2;
            else if(idRadioButton==R.id.rbMes)
                indTipoAtualizacao = 3;

            session = Session.getInstance();
            configSistema = session.getConfiguracaoSistema();
            if(configSistema != null) // Achou alguma casa cadastrada na config do sitema?
            {
                configSistema.setIndTipoAtualizacao(indTipoAtualizacao);
                listEntDom = configSistema.operar(this,true,Servlet.DF_ALTERAR);
                Toast.makeText(Tela_configuracao_aplicativo.this, "Configurações gravadas com sucesso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Tela_configuracao_aplicativo.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        }
    }
    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a p?gina anterior
        Intent intent = new Intent();
        // Volta para a tela que fez a solicitação
        intent.setClass(Tela_configuracao_aplicativo.this, contextTelaParaVoltar.getClass());
        if(contextTelaParaVoltar.getClass() == TelaMenu.class)
        {
            absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
            intent.putExtra("absClasse", absRecurso);
        }
        startActivity(intent); // chama a pr?xima tela
        finish();
    }
}
