package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;
import com.example.appmedirconsumorecursos.R;

import java.util.LinkedList;
import java.util.List;

public class TelaLogin extends Activity implements View.OnClickListener {
    private CheckBox cbLogarAutomaticamente;
    private EditText edtLogin,
             edtSenha;
    private Button btnLogin;
    private Intent dados;

    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private Residencia residencia;
    private Controler controler;
    private Session session;
    private ConfiguracaoSistema configSistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        //
        String logoff = null;
        // region vincula��o de componentes
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        cbLogarAutomaticamente = (CheckBox)findViewById(R.id.cbLogarAutomaticamente);
        // indica que o botao pode ser clicacdo
        btnLogin.setOnClickListener(this);
        //
        dados = getIntent(); // Recebe os dados da tela anterior
        logoff = dados.getStringExtra("logoff");
        //
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
            if(listEntDom != null) // Achou alguma casa cadastrada na config do sitema?
            {
                configSistema = (ConfiguracaoSistema)listEntDom.get(0);
                session.setConfiguracaoSistema(configSistema);
                if(configSistema.getFgLogarAutomaticamente() == 1 && logoff == null) // Vai logar automaticamente?
                { // sim
                    Toast.makeText(TelaLogin.this, "Bem vindo ao monitoramento da residencia: " + session.getResidencia().getNome(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(TelaLogin.this, TelaPrincipal.class);
                    startActivity(intent);
                    finish();
                }
            }
            edtLogin.setText(session.getResidencia().getNome());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_login, menu);
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
        if(view == btnLogin)
        {
            if(TextUtils.isEmpty(edtLogin.getText().toString()) || TextUtils.isEmpty(edtSenha.getText().toString()))
            {
                Toast.makeText(TelaLogin.this, "Prencha todos os campos para fazer o login.", Toast.LENGTH_LONG).show();
            }
            if(session.getResidencia() == null) // Residencia não esta gravada na banco interno?
            { // Não, então verifica no servidor
                instanciarClasses(false); // operação no servidor
                residencia.setSenha(edtSenha.getText().toString());
                residencia.setNome(edtLogin.getText().toString());
                residencia.popularMap(residencia, "consultar", Residencia.class.getName());
                // ******************************** validar um TIMEOUT *******************************
                // ******************************** colar um GIF de carregando e esconder o botão logar impossibilitando
                // que o usuário clique novamente, pois se o usuario clicar novamente ele abre duas telas**************************
                resultado = controler.doPost(residencia.getMap());
                listEntDom = resultado.getEntidades();
                if (listEntDom != null) // Achou alguma casa cadastrada com o login e senha digitado?
                {// então grava no banco interno a residencia
                    session.setResidencia((Residencia) listEntDom.get(0));
                    instanciarClasses(true); // operação no banco
                    residencia.popularMap(session.getResidencia(), "salvar", Residencia.class.getName());
                    resultado = controler.doPost(residencia.getMap());
                    if (resultado.getMsg() != null) {
                        Toast.makeText(TelaLogin.this, "Erro ao cadastrar login no banco interno do celular, favor contatar o suporte.", Toast.LENGTH_LONG).show();
                    }
                }
                else // não encontrou no banco interno nem no servidor
                {
                    Toast.makeText(TelaLogin.this, "Usuário não encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else // estava gravado no banco
            {
                // Verifica a senha
                if(!session.getResidencia().getSenha().equals(edtSenha.getText().toString()) || !session.getResidencia().getNome().equals(edtLogin.getText().toString()))
                {
                    Toast.makeText(TelaLogin.this, "Usuário não encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            // Verificar se vai logar automaticamente
            // Consulta
            configSistema = new ConfiguracaoSistema();
            configSistema.getMapInstance();
            configSistema.setMapId(session.getResidencia().getId());
            listEntDom = configSistema.operar(this, true, Controler.DF_CONSULTAR);
            // altera
            int chkLogar = cbLogarAutomaticamente.isChecked() ? 1 : 0;
            if (listEntDom != null) // Achou registro?
            {
                configSistema = (ConfiguracaoSistema)listEntDom.get(0);
                session.setConfiguracaoSistema(configSistema);
                if (configSistema.getFgLogarAutomaticamente() != chkLogar) // é diferente do cadastrado no banco?
                {// então atualiza
                    configSistema.getMapInstance();
                    configSistema.setMapId(session.getResidencia().getId()); // grava o ID na config
                    configSistema.setMapFgLogarAutomaticamente(chkLogar);
                    listEntDom = configSistema.operar(this,true, Controler.DF_ALTERAR);
                }
            }
            // inclui
            else {
                configSistema = new ConfiguracaoSistema();
                configSistema.getMapInstance();
                configSistema.setMapId(session.getResidencia().getId()); // grava o ID na config
                configSistema.setMapFgLogarAutomaticamente(chkLogar);
                listEntDom = configSistema.operar(this, true, Controler.DF_SALVAR);
            }
            if (resultado.getMsg() != null) {
                Toast.makeText(TelaLogin.this, "Erro ao cadastrar as configurações do aplicativo, favor contatar o suporte.", Toast.LENGTH_LONG).show();
            }

            // Se caiu aqui, a casa está cadastrada, login permitido
            Toast.makeText(TelaLogin.this, "Bem vindo ao monitoramento da residencia: " + session.getResidencia().getNome(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(TelaLogin.this, TelaPrincipal.class);
            startActivity(intent);
            finish();
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
        residencia = new Residencia();
        controler = new Controler();
    }
}
