package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsFactoryRecurso;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.R;
import com.example.appmedirconsumorecursos.R.id;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class TelaMenu extends Activity implements OnClickListener {
	private TextView txtNome,
			txtGastoHj,
			txtGastoAtual,
			txtMediaFinal,
			txtValorGastoHj,
			txtValorGastoAtual,
			txtValorMediaFinal,
			txtDataUltimaAtualizacaoGastoAtual,
			txtDataUltimaAtualizacaoGastoHoje,
			txtUnMedidaGastoHoje,
			txtUnMediaGastoAtual,
			txtUnMedidaMediaFinal;
	private Button btnAtualizar,
			btnHistorico,
			btnRelatorio;
	private ImageView imgRecurso;
	private Intent dados;
	private AbsFactoryRecurso absFactoryRecurso;
	//
	private GastoAtual gastoAtual;
	private GastoHoje gastoHoje;

	private Controler controler;
	private Session session;
	private List<EntidadeDominio> listEntDom;
	private Resultado resultado;
	private ConfiguracaoSistema configSistema;
	//
	private Integer idRecurso;

//	public boolean onOptionsItemSelected(MenuItem item) {
////		Intent intent;
////		session = Session.getInstance();
////		switch (item.getItemId()) {
////			case id.menu_configuracao_tela_menu:
////				session.setContext(this);
////				intent = new Intent();
////				intent.setClass(TelaMenu.this, Tela_configuracao_aplicativo.class);
////				intent.putExtra("absClasse", absFactoryRecurso);
////				startActivity(intent);
////				finish();
////				return true;
////			default:
////				return super.onOptionsItemSelected(item);
////		}
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_menu);
		// associa os objetos
		imgRecurso = (ImageView)findViewById(id.imgRecurso);
		//txtNome = (TextView)findViewById(id.txtNomeRecurso);
		//
		txtGastoHj = (TextView)findViewById(id.txtGastoHj);
		txtGastoAtual = (TextView)findViewById(id.txtGastoAtua);
		txtMediaFinal = (TextView)findViewById(id.txtMediaFina);
		txtValorGastoHj = (TextView)findViewById(id.txtR$GastoHj);
		txtValorGastoAtual = (TextView)findViewById(id.txtR$GastoAtual);
		txtValorMediaFinal = (TextView)findViewById(id.txtR$MediaFinal);
		txtDataUltimaAtualizacaoGastoAtual  = (TextView)findViewById(id.txtDataUltimaAtualizacaoGastoAtual);
		txtDataUltimaAtualizacaoGastoHoje = (TextView)findViewById(id.txtDataUltimaAtualizacaoGastoHoje);
		txtUnMedidaGastoHoje  = (TextView)findViewById(id.txtUnMedidaGastoHoje);
		txtUnMediaGastoAtual  = (TextView)findViewById(id.txtUnMediaGastoAtual);
		txtUnMedidaMediaFinal  = (TextView)findViewById(id.txtUnMedidaMediaFinal);
		//
		btnAtualizar = (Button)findViewById(id.btnAtualizarDados);
		btnHistorico = (Button)findViewById(id.btnHistorico);
		btnRelatorio = (Button)findViewById(id.btnRelatorio);

		// indica que os botoes podem ser clicados
		btnAtualizar.setOnClickListener(this);
		btnHistorico.setOnClickListener(this);
		btnRelatorio.setOnClickListener(this);

		//
		dados = getIntent(); // Recebe os dados da tela anterior
		absFactoryRecurso = (AbsFactoryRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
		//txtNome.setText(absFactoryRecurso.getNome()); // Recebe o nome do recurso e manda pra tela
		imgRecurso.setImageResource(absFactoryRecurso.getIdIcone()); // Recebe o id da imagem e manda pra tela
		idRecurso = Integer.parseInt(absFactoryRecurso.getIdRecurso());
		if(idRecurso == 2) // luz ?
		{
			txtUnMedidaGastoHoje.setText("Kw/h");
			txtUnMediaGastoAtual.setText("Kw/h");
			txtUnMedidaMediaFinal.setText("Kw/h");
		}
		// Consulta a ultima medição registrada para apresentá-la
		session = Session.getInstance();
		// Gasto no dia
		instanciarClasses(); // consulta no banco interno
		gastoHoje.getMapInstance();
		gastoHoje.setMapCdResidencia(session.getResidencia().getId());
		listEntDom = gastoHoje.operar(this,true, Controler.DF_CONSULTAR);
		if(listEntDom != null) //Achou alguma coisa?
		{
			gastoHoje = (GastoHoje) listEntDom.get(0);
			if(idRecurso == 1) // agua?
			{
				txtGastoHj.setText(String.valueOf(gastoHoje.getNrMetroCubicoAgua()));
				txtValorGastoHj.setText(String.valueOf(gastoHoje.getVlrGastoAgua()));
			}
			if(idRecurso == 2)  // luz?
			{
				txtGastoHj.setText(String.valueOf(gastoHoje.getNrWatts()));
				txtValorGastoHj.setText(String.valueOf(gastoHoje.getVlrGastLuz()));
			}
			txtDataUltimaAtualizacaoGastoHoje.setText(String.valueOf(formatarDataParaApresentacao(gastoHoje.getDtUltimaRegistroDia())));
		}
		// Gasto atual
		instanciarClasses(); // consulta no banco interno
		gastoAtual.getMapInstance();
		gastoAtual.setMapCdResidencia(session.getResidencia().getId());
		listEntDom = gastoAtual.operar(this,true, Controler.DF_CONSULTAR);
		if(listEntDom != null) //Achou alguma coisa?
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
			calcularPrevisaoGasto(gastoAtual.getDtUltimaMedicao());
			txtDataUltimaAtualizacaoGastoAtual.setText(String.valueOf(formatarDataParaApresentacao(gastoAtual.getDtUltimaMedicao())));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_menu, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		if(view == btnAtualizar) // vai atualizar com novos dados?
		{
			List<EntidadeDominio> listAux;
			instanciarClasses(); // consulta no banco interno
			//gastoHoje.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
			gastoHoje.getMapInstance();
			gastoHoje.setMapCdResidencia(session.getResidencia().getId());
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
				listAux = gastoHojeAux.operar(this,true, Controler.DF_CONSULTAR);
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
			instanciarClasses(); // consulta no banco interno
			//gastoAtual.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
			//gastoAtual.popularMap(gastoAtual, "consultar", GastoAtual.class.getName());
			//resultado = controler.doPost(gastoAtual.getMap());
			gastoAtual.getMapInstance();
			gastoAtual.setMapCdResidencia(session.getResidencia().getId());
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
				calcularPrevisaoGasto(gastoAtual.getDtUltimaMedicao());
				txtDataUltimaAtualizacaoGastoAtual.setText(String.valueOf(formatarDataParaApresentacao(gastoAtual.getDtUltimaMedicao())));
			}
			Toast.makeText(TelaMenu.this, "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
		}
		if(view == btnHistorico) // Abre a pagina de estatisticas?
		{
			Intent intent = new Intent();
			intent.setClass(TelaMenu.this, TelaDeHistorico.class);
			intent.putExtra("absClasse", absFactoryRecurso);
			startActivity(intent);
			finish();
		}
		if(view == btnRelatorio)
		{
			Intent intent = new Intent();
			intent.setClass(TelaMenu.this, TelaRelatorio1.class);
			intent.putExtra("absClasse", absFactoryRecurso);
			startActivity(intent);
			finish();
		}
	}

	public void onBackPressed() // precionou o voltar do telefone?
	{ // Sim, volta para a página anterior
		Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e dpois a próxima tela( a que vai ser chamada)
        intent.setClass(TelaMenu.this, TelaPrincipal.class);
        intent.putExtra("absClasse", absFactoryRecurso);
		startActivity(intent); // chama a próxima tela
        finish();
	}
	private void instanciarClasses()
	{
		listEntDom = new LinkedList<EntidadeDominio>();
		gastoAtual = new GastoAtual();
		gastoHoje = new GastoHoje();
	}
	private void calcularPrevisaoGasto(Date date)
	{
		double vlrTarifa = 0,
				consumo = 0,
				vlrGastoAtual = 0;
		if(idRecurso == 1) {
			vlrTarifa = session.getConfiguracaoSistema().getVlrTarifaAgua();
			consumo = gastoAtual.getNrMetroCubicoAgua();
			//vlrGastoAtual = gastoAtual.getVlrGastoAgua();
		}
		else if(idRecurso == 2) {
			vlrTarifa = session.getConfiguracaoSistema().getVlrTarifaLuz();
			consumo = gastoAtual.getNrWatts();
			//vlrGastoAtual = gastoAtual.getVlrGastLuz();
		}
		if(vlrTarifa ==0)
			Toast.makeText(TelaMenu.this, "Para calcular a previsão primeiro cadastre o valor da tarifa nas configurações.", Toast.LENGTH_LONG).show();

		vlrGastoAtual = consumo * vlrTarifa;
		// calcular média final
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int diaCorrente = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		//***************hora corrente***************************
		int horaCorrente = calendar.get(GregorianCalendar.HOUR);
		// amPm
		// 0 = dia
		// 1 = noite
		int amPm = calendar.get(GregorianCalendar.AM_PM);

		if(amPm == 1)
		{
			if(horaCorrente>0 && horaCorrente < 13)
			{
				if(horaCorrente == 12)
					horaCorrente = 0;
				else
					horaCorrente += 12;
			}
		}
		int qtdHorasPassadas = (diaCorrente - 1) * 24;
		qtdHorasPassadas += horaCorrente;
		//*******************************************************
		//int mes = calendar.get(GregorianCalendar.MONTH);
		int qtdDiasMes = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		//******************horas faltantes**************************
		int diasFaltantes = qtdDiasMes - diaCorrente;
		int qtdHorasFaltantes = diasFaltantes * 24;
		//***********************************************************
		//
//		double mediaHoraConsumo = (consumo / diaCorrente) / 24;
//		double mediaFinalMesConsumo = (diasFaltantes * mediaHoraConsumo) * 24;
		double mediaHoraConsumo = (consumo /qtdHorasPassadas);
		double mediaFinalMesConsumo = mediaHoraConsumo * qtdHorasFaltantes;
		mediaFinalMesConsumo += consumo;
		//********************vai vim do banco
//		double mediaFinalMesValor = ((mediaHoraConsumo * vlrTarifa)*24) * diasFaltantes;
//		mediaFinalMesValor += vlrGastoAtual;
		double mediaFinalMesValor = mediaFinalMesConsumo * vlrTarifa;
		NumberFormat formatarNumero = new DecimalFormat(".##");
		String aux="";
		if(mediaFinalMesConsumo<1)
			aux = "0";
		aux+=formatarNumero.format(mediaFinalMesConsumo);
		txtMediaFinal.setText(aux);
		txtValorMediaFinal.setText(String.valueOf(formatarNumero.format(mediaFinalMesValor)));
	}
	private String formatarDataParaApresentacao(Date data)
	{
		if(data == null)
			return null;
		else
		{
			int dia, mes, ano, hora, min;
			String sData;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(data);
			dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			mes = calendar.get(GregorianCalendar.MONTH);
			mes++;
			ano = calendar.get(GregorianCalendar.YEAR);
			hora = calendar.get(GregorianCalendar.HOUR);
			// amPm
			// 0 = dia
			// 1 = noite
			int amPm = calendar.get(GregorianCalendar.AM_PM);

			if(amPm == 1)
			{
				if(hora>0 && hora < 13)
				{
					if(hora == 12)
						hora = 0;
					else
						hora += 12;
				}
			}
			min = calendar.get(GregorianCalendar.MINUTE);
			sData = dia + "/" + mes + "/" + ano + " " + hora + ":" + min;
			return sData;
		}
	}
}

