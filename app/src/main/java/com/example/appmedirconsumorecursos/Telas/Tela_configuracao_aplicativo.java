package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.R;

import java.util.List;

public class Tela_configuracao_aplicativo extends Activity implements View.OnClickListener {
    private RadioButton rbHora,
                        rbDia,
                        rbMes,
                        rb110,
                        rb220;
    private Button btnSalvarConfigApp;
    private EditText edtVlrTarifaAgua,
                     edtVlrTarifaLuz;
    //
    private Session session;
    private ConfiguracaoSistema configSistema;
    private List<EntidadeDominio> listEntDom;
    //
    private int indTipoAtualizacao;
    private int indTipoVoltagem;
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
        edtVlrTarifaAgua = (EditText)findViewById(R.id.edtVlrTarifaAgua);
        edtVlrTarifaLuz = (EditText)findViewById(R.id.edtVlrTarifaLuz);
        rbHora = (RadioButton)findViewById(R.id.rbHora);
        rbDia = (RadioButton)findViewById(R.id.rbDia);
        rbMes = (RadioButton)findViewById(R.id.rbMes);
        rb110 = (RadioButton)findViewById(R.id.rb110);
        rb220 = (RadioButton)findViewById(R.id.rb220);
        btnSalvarConfigApp = (Button)findViewById(R.id.btnSalvarConfiguracao);
        btnSalvarConfigApp.setOnClickListener(this);
        contextTelaParaVoltar = session.getContext();
        dados = getIntent(); // Recebe os dados da tela anterior

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

        indTipoVoltagem = configSistema.getIndTipoVoltagem();
        switch(indTipoVoltagem)
        {
            case 1:
                rb110.setChecked(true);
                break;
            case 2:
                rb220.setChecked(true);
                break;
        }

        edtVlrTarifaAgua.setText(String.valueOf(configSistema.getVlrTarifaAgua()));
        edtVlrTarifaLuz.setText(String.valueOf(configSistema.getVlrTarifaLuz()));
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

            final RadioGroup rgTipoVoltagem = (RadioGroup)findViewById(R.id.rgTipoVoltagem);
            idRadioButton = rgTipoVoltagem.getCheckedRadioButtonId();
            indTipoVoltagem = 0;
            if(idRadioButton == R.id.rb110)
                indTipoVoltagem = 1;
            else if(idRadioButton == R.id.rb220)
                indTipoVoltagem = 2;


            double vlrTarifaAgua, vlrTarifaLuz;
            vlrTarifaAgua = validarDouble(edtVlrTarifaAgua.getText().toString());
            vlrTarifaLuz = validarDouble(edtVlrTarifaLuz.getText().toString());

            session = Session.getInstance();
            configSistema = session.getConfiguracaoSistema();
            if(configSistema != null) // Achou alguma casa cadastrada na config do sitema?
            {
                // Add ao mapa
                configSistema.getMapInstance();
                configSistema.setMapId(configSistema.getId());
                configSistema.setMapIndTipoAtualizacao(indTipoAtualizacao);
                configSistema.setMapIndTipoVoltagem(indTipoVoltagem);
                configSistema.setMapVlrTarifaAgua(vlrTarifaAgua);
                configSistema.setMapVlrTarifaLuz(vlrTarifaLuz);
                configSistema.operar(this, true, Controler.DF_ALTERAR);
                // add a classe
                configSistema.setIndTipoAtualizacao(indTipoAtualizacao);
                configSistema.setIndTipoVoltagem(indTipoVoltagem);
                configSistema.setVlrTarifaAgua(vlrTarifaAgua);
                configSistema.setVlrTarifaLuz(vlrTarifaLuz);
                session.setConfiguracaoSistema(configSistema);
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
        // Volta para a tela que fez a solicita��o
        intent.setClass(Tela_configuracao_aplicativo.this, contextTelaParaVoltar.getClass());
        if(contextTelaParaVoltar.getClass() == TelaMenu.class)
        {
            absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
            intent.putExtra("absClasse", absRecurso);
        }
        startActivity(intent); // chama a pr?xima tela
        finish();
    }
    private double validarDouble(String sValor)
    {
        double dValor= 0;
        if(sValor != null ) {
            try {
                dValor = Double.parseDouble(sValor);
            } catch (Exception e) {
                dValor = 0;
            }
        }
        return dValor;
    }
}
