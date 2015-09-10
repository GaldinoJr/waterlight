package com.example.appmedirconsumorecursos.Telas;

import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Controle.Servlet.Servlet;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.R;
import com.example.appmedirconsumorecursos.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class TelaMenu extends Activity implements OnClickListener {
	private TextView txtNome,
			txtGastoHj,
			txtGastoAtual,
			txtMediaFinal,
			txtValorGastoHj,
			txtValorGastoAtual,
			txtValorMediaFinal;
	private Button btnAtualizar,
			btnHistorico;
	private ImageView imgRecurso;
	private Intent dados;
	private AbsRecurso absRecurso;
	//
	private GastoAtual gastoAtual;
	private GastoHoje gastoHoje;

	private Servlet servlet;
	private Session session;
	private List<EntidadeDominio> listEntDom;
	private Resultado resultado;
	private ConfiguracaoSistema configSistema;
	//
	private Integer idRecurso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_menu);
		// associa os objetos
		imgRecurso = (ImageView)findViewById(id.imgRecurso);
		txtNome = (TextView)findViewById(id.txtNomeRecurso);
		//
		txtGastoHj = (TextView)findViewById(id.txtGastoHj);
		txtGastoAtual = (TextView)findViewById(id.txtGastoAtua);
		txtMediaFinal = (TextView)findViewById(id.txtMediaFina);
		txtValorGastoHj = (TextView)findViewById(id.txtR$GastoHj);
		txtValorGastoAtual = (TextView)findViewById(id.txtR$GastoAtual);
		txtValorMediaFinal = (TextView)findViewById(id.txtR$MediaFinal);
		//
		btnAtualizar = (Button)findViewById(id.btnAtualizarDados);
		btnHistorico = (Button)findViewById(id.btnHistorico);
		// indica que os botoes podem ser clicados
		btnAtualizar.setOnClickListener(this);
		btnHistorico.setOnClickListener(this);
		//
		dados = getIntent(); // Recebe os dados da tela anterior
		absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
		txtNome.setText(absRecurso.getNome()); // Recebe o nome do recurso e manda pra tela
		imgRecurso.setImageResource(absRecurso.getIdIcone()); // Recebe o id da imagem e manda pra tela
		idRecurso = Integer.parseInt(absRecurso.getIdRecurso());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_menu, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == btnAtualizar) // vai atualizar com novos dados?
		{
			instanciarClasses(false); // consulta no banco interno
			gastoHoje.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
			gastoHoje.popularMap(gastoHoje, "consultar", GastoHoje.class.getName());
			resultado = servlet.doPost(gastoHoje.getMap());
			listEntDom = resultado.getEntidades();
			if(listEntDom != null) // Achou algum registro?
			{
				// erro neste php
				gastoHoje = (GastoHoje) listEntDom.get(0);
				if(idRecurso == 1) // agua?
				{
					txtGastoHj.setText(String.valueOf(gastoHoje.getNrMetroCubicoAgua()));
					txtValorGastoHj.setText(String.valueOf(gastoHoje.getVlrGastoAgua()));
				}
				if(idRecurso == 2) { // luz?
					txtGastoHj.setText(String.valueOf(gastoHoje.getNrWatts()));
					txtValorGastoHj.setText(String.valueOf(gastoHoje.getVlrGastLuz()));
				}
			}
			instanciarClasses(false); // consulta no banco interno
			gastoAtual.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
			gastoAtual.popularMap(gastoAtual, "consultar", GastoAtual.class.getName());
			resultado = servlet.doPost(gastoAtual.getMap());
			listEntDom = resultado.getEntidades();
			if(listEntDom != null) // Achou algum registro?
			{
				gastoAtual = (GastoAtual) listEntDom.get(0);
				if(idRecurso == 1) // agua?
				{
					txtGastoAtual.setText(String.valueOf(gastoAtual.getNrMetroCubicoAgua()));
					txtValorGastoAtual.setText(String.valueOf(gastoAtual.getVlrGastoAgua()));
				}
				if(idRecurso == 2)  // luz?
				{
					txtGastoAtual.setText(String.valueOf(gastoAtual.getNrWatts()));
					txtValorGastoAtual.setText(String.valueOf(gastoAtual.getVlrGastLuz()));
				}
			}
			Toast.makeText(TelaMenu.this, "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
		}
		if(view == btnHistorico) // Abre a pagina de estatisticas?
		{
			Intent intent = new Intent();
			intent.setClass(TelaMenu.this, TelaDeHistorico.class);
			intent.putExtra("absClasse", absRecurso);
			startActivity(intent);
			finish();
		}
	}

	public void onBackPressed() // precionou o voltar do telefone?
	{ // Sim, volta para a página anterior 
		Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e dpois a próxima tela( a que vai ser chamada)
        intent.setClass(TelaMenu.this, TelaPrincipal.class);
        intent.putExtra("absClasse", absRecurso);
		startActivity(intent); // chama a próxima tela
        finish();
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
		gastoAtual = new GastoAtual();
		gastoHoje = new GastoHoje();
		configSistema = new ConfiguracaoSistema();
	}
}
