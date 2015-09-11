package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
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

    private Servlet servlet;
    private Session session;
    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private ConfiguracaoSistema configSistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracao_aplicativo);
        //
        rbHora = (RadioButton)findViewById(R.id.rbHora);
        rbDia = (RadioButton)findViewById(R.id.rbDia);
        rbMes = (RadioButton)findViewById(R.id.rbMes);
        btnSalvarConfigApp = (Button)findViewById(R.id.btnSalvarConfiguracao);
        btnSalvarConfigApp.setOnClickListener(this);
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
            int indTipoAtualizacao = 0;
            if(idRadioButton == R.id.rbHora)
                indTipoAtualizacao = 1;
            else if(idRadioButton==R.id.rbDia)
                indTipoAtualizacao = 2;
            else if(idRadioButton==R.id.rbMes)
                indTipoAtualizacao = 3;

            instanciarClasses(true); // consulta no banco interno
            configSistema = new ConfiguracaoSistema();
            configSistema.setId(session.getResidencia().getId());
            configSistema.popularMap(configSistema, "consultar", ConfiguracaoSistema.class.getName());
            resultado = servlet.doPost(configSistema.getMap());
            listEntDom = resultado.getEntidades();
            if(listEntDom != null) // Achou alguma casa cadastrada na config do sitema?
            {
                configSistema = (ConfiguracaoSistema) listEntDom.get(0);

                instanciarClasses(true); // operação no banco
                configSistema.setIndTipoAtualizacao(indTipoAtualizacao);
                configSistema.popularMap(configSistema, "alterar", ConfiguracaoSistema.class.getName());
                resultado = servlet.doPost(configSistema.getMap());
                Toast.makeText(Tela_configuracao_aplicativo.this, "Configurações gravadas com sucesso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Tela_configuracao_aplicativo.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        }
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
        servlet = new Servlet();
    }
}
