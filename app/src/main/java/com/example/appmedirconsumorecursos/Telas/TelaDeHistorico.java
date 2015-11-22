package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsFactoryRecurso;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoMes;
import com.example.appmedirconsumorecursos.R;
import com.example.appmedirconsumorecursos.R.id;

import java.util.List;

public class TelaDeHistorico extends Activity implements View.OnClickListener {

	private Spinner spDia,
					spMes,
					spAno;

	private TextView txtNomeRecurso,
					 txtGastoHj,
					 txtRsGastoHj,
					 txtGastoAtual,
					 txtRsGastoAtual,
					 txtMediaFinal,
					 txtRsMediaFinal;

	private RadioButton rbConsumo,
						rbVlrGasto,
						rbMes,
						rbDia;

	private CheckBox chkCompararMaiorConsumo,
			chkCruzarDados,
			chkCompararOutrasResidencias,
			chkIgualNrMorador,
			chkIgualNrComodo;

	private EditText edtNrComodos,
			edtNrMorador;
	private Button btnGrafico;
	private int teste = 0,
			teste2 = 0, // testar se foi selecionado algo no sppiner
			testeSpDia = 0;
	private ArrayAdapter<String> aaMes;
	private ArrayAdapter<Integer> aaAno;
	private ArrayAdapter<String> aaDia;
	private String[] vetSmes = {"",
			"Janeiro" , "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
			"Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	private Integer[] vetNano;
	private String[] vetDia;
	private ImageView imgRecurso;
	private Intent dados;
	private AbsFactoryRecurso absFactoryRecurso;
	private Integer idRecurso;
	// sempre usar com requisições
	private Session session;
	private List<EntidadeDominio> listEntDom;
	//
	boolean retorno;
	String data;
	int dia, mes, ano;
	String sDia, sMes, sAno;
	//
	private GastoMes gastoMes;
	private GastoHoje gastoHoje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int i, j = 2015;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_de_historico);
		//
		//txtNomeRecurso = (TextView)findViewById(id.txtNomeRecurso);
		txtGastoHj = (TextView)findViewById(id.txtGastoHj);
		txtRsGastoHj = (TextView)findViewById(id.txtR$GastoHj);
		txtGastoAtual = (TextView)findViewById(id.txtGastoAtua);
		txtRsGastoAtual = (TextView)findViewById(id.txtR$GastoAtual);
		txtMediaFinal = (TextView)findViewById(id.txtMediaFina);
		txtRsMediaFinal = (TextView)findViewById(id.txtR$MediaFinal);
		btnGrafico = (Button)findViewById(id.btnGrafico);
		btnGrafico.setOnClickListener(this);
		rbConsumo = (RadioButton)findViewById(id.rbConsumo);
		spDia = (Spinner)findViewById(id.spDia);
		spMes = (Spinner)findViewById(id.spMes);
		spAno = (Spinner)findViewById(id.spAno1);
		chkCompararMaiorConsumo = (CheckBox)findViewById(R.id.chkCompararMaiorConsumo);
		chkCruzarDados = (CheckBox)findViewById(id.chkCruzarDados);
		chkCompararOutrasResidencias = (CheckBox)findViewById(id.chkCompararOutrasResidencias);
		chkIgualNrMorador = (CheckBox)findViewById(id.chkIgualNrMorador);
		chkIgualNrComodo = (CheckBox)findViewById(id.chkIgualNrComodo);
		edtNrComodos = (EditText)findViewById(id.edtNrComodos);
		edtNrMorador = (EditText)findViewById(id.edtNrMorador);

		rbConsumo.setChecked(true);
		
		imgRecurso = (ImageView)findViewById(id.imgRecurso);
		//
		dados = getIntent(); // Recebe os dados da tela anterior
		absFactoryRecurso = (AbsFactoryRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
		//txtNomeRecurso.setText(absFactoryRecurso.getNome()); // Recebe o nome do recurso e manda pra tela
		imgRecurso.setImageResource(absFactoryRecurso.getIdIcone()); // Recebe o id da imagem e manda pra tela
		idRecurso = Integer.parseInt(absFactoryRecurso.getIdRecurso());
		//
		vetNano = new Integer[85];
		for(i= 0; i < 85; i++, j++) // Popula o sppiner de ano
			vetNano[i] = j;
		//
		vetDia = new String[32];
		vetDia[0] = "";
		for(i = 1; i< 32;i++) // Popula o sppiner de dias
		{
			vetDia[i] = String.valueOf(i);
		}

		// passa para o arrayList o conteudo dos vetores
		aaMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vetSmes);
		aaAno = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vetNano);
		aaDia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vetDia);
		// Passa o arrayList para o Spinner
		spMes.setAdapter(aaMes);
		spAno.setAdapter(aaAno);
		spDia.setAdapter(aaDia);
		//
		// Quando selecionar um item da lista de MESES do SPINNER
		session = Session.getInstance();
		session.setContext(this);
		// SPINNER DE MÊS
		spMes.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// Verificar se selecionou algo
				//if(position == 1)  // Selecionou o primeiro item da lista?(janeiro)
				//{
				if (teste != 0) {
					//Toast.makeText(TelaDeHistorico.this, "posição: " + position + " \nnome: " + spMes.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
					try
					{
						mes = position;
						if (mes > 0) // selecionou o mes?
						{
							dia = spDia.getSelectedItemPosition();
							ano = spAno.getSelectedItemPosition();
							ano += 2015;
							sAno = String.valueOf(ano);
							sDia = "";
							if (dia > 0) {
								if (dia < 10) {
									sDia = "0";
								}
								sDia += String.valueOf(dia);
								data = sDia + "/" + position + "/" + String.valueOf(ano);
								data += " 00:00:00";
								retorno = pesquisarMes(data);
								if (retorno == false) {
									Toast.makeText(TelaDeHistorico.this, "Dia " + sDia + " de " + spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
									limparCampos();
								}
							}
							else
							{
								sDia = "01";
								data = sDia + "/" + mes + "/" + String.valueOf(ano);
								retorno = pesquisarMes(data);
								if (retorno == false) {
									Toast.makeText(TelaDeHistorico.this, spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
									limparCampos();
								}
							}
						}
						else
							limparCampos();
					} catch (Exception e) {
						Toast.makeText(TelaDeHistorico.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
						limparCampos();
					}
				} else {
					teste++;
				}
				// spGrupo.getSelectedItem().toString(); ********************pegar o conteudo em texto do spinner
				//spNomes.setAdapter((SpinnerAdapter) aExPeito);
				//spNomes.setAdapter(aExPeito); // Devolve a lista de exercicios de peito
				//}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
			
		// Quando selecionar um item da lista de ANOS do SPINNER 
//		spAno.setOnItemSelectedListener(new  OnItemSelectedListener()
//		{
//            public  void  onItemSelected(AdapterView<?> parent, View view, int  position, long  id)
//            {
//            	// Verificar se selecionou algo
//            	if(teste2 !=0)
//            		// carregar o primeiro com o ano corrente(2015)
//            		Toast.makeText(TelaDeHistorico.this, "posição: " + position + " \nnome: " + spAno.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            	else
//            		teste2++;
//        		// spGrupo.getSelectedItem().toString(); ********************pegar o conteudo em texto do spinner
//        		//spNomes.setAdapter((SpinnerAdapter) aExPeito);
//        		//spNomes.setAdapter(aExPeito); // Devolve a lista de exercicios de peito
//            }
//            public  void  onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });

		// SPINNER DE DIA
		spDia.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (testeSpDia != 0) {
					//Toast.makeText(TelaDeHistorico.this, "posição: " + position + " \nnome: " + spMes.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
					gastoMes = new GastoMes();
					try
					{
						// dia selecionado
						dia = position;
						// Pega o mes selecionado
						mes = spMes.getSelectedItemPosition();
						if (mes > 0) // selecionou o mes?
						{
							sMes = "";
							if (mes < 10) {
								sMes += "0";
							}
							sMes += String.valueOf(mes);
							ano = spAno.getSelectedItemPosition();
							ano += 2015;
							sAno = String.valueOf(ano); // ano selecionado
							sDia = "";
							if (dia > 0)  // selecionou o dia?
							{
								//dia += 1;
								if (dia < 10) {
									sDia += "0";
								}
								sDia += String.valueOf(dia);
								data = String.valueOf(ano) + "-" + sMes + "-" + sDia;
								data += " 00:00:00";
								retorno = pesquisarDia(data);
								if(retorno == false) {
									Toast.makeText(TelaDeHistorico.this, "Dia " + sDia + " de " + spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
									limparCampos();
								}
							}
							else
							{
								sDia += "01";
								data = sDia + "/" + sMes + "/" + String.valueOf(ano);

								retorno = pesquisarMes(data);
								if (retorno == false)
								{
									Toast.makeText(TelaDeHistorico.this, spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
									limparCampos();
								}
							}
						}
						else
						{
							if (dia > 0)  // selecionou o dia?
								Toast.makeText(TelaDeHistorico.this, "Para fazer a busca, primeiro selecione o mês.", Toast.LENGTH_SHORT).show();
						}

					}
					catch (Exception e)
					{
						Toast.makeText(TelaDeHistorico.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
						limparCampos();
					}
				} else
				{
					testeSpDia++;
				}
				// spGrupo.getSelectedItem().toString(); ********************pegar o conteudo em texto do spinner
				//spNomes.setAdapter((SpinnerAdapter) aExPeito);
				//spNomes.setAdapter(aExPeito); // Devolve a lista de exercicios de peito
				//}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_de_estatisticas, menu);
		return true;
		//
		
	}

	private void limparCampos()
	{
		txtGastoHj.setText("");
		txtRsGastoHj.setText("");
	}

	private boolean pesquisarMes(String data)
	{
		gastoMes = new GastoMes();
		gastoMes.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
		gastoMes.setSdt_inclusao(data);
		// Mudar a consulta par a
		listEntDom = gastoMes.operar(session.getContext(), false, Controler.DF_CONSULTAR);
		if (listEntDom != null) {
			gastoMes = (GastoMes) listEntDom.get(0);
			if (idRecurso == 1) // agua?
			{
				txtGastoHj.setText(String.valueOf(gastoMes.getNrMetroCubicoAgua()));
				txtRsGastoHj.setText(String.valueOf(gastoMes.getVlrGastoAgua()));
			}
			if (idRecurso == 2) // luz?
			{
				txtGastoHj.setText(String.valueOf(gastoMes.getNrWatts()));
				txtRsGastoHj.setText(String.valueOf(gastoMes.getVlrGastLuz()));
			}
			return true;
		}
		else
			return false;
	}
	private boolean pesquisarDia(String data)
	{
		gastoHoje = new GastoHoje();
		gastoHoje.getMapInstance();
		gastoHoje.setMapCdResidencia(session.getResidencia().getId());
		gastoHoje.setMapsDtUltimoRegistroDia(data);
		// Mudar a consulta par a
		listEntDom = gastoHoje.operar(session.getContext(), false, Controler.DF_CONSULTAR);
		if (listEntDom != null) {
			gastoHoje = (GastoHoje) listEntDom.get(0);
			if (idRecurso == 1) // agua?
			{
				txtGastoHj.setText(String.valueOf(gastoHoje.getNrMetroCubicoAgua()));
				txtRsGastoHj.setText(String.valueOf(gastoHoje.getVlrGastoAgua()));
			}
			if (idRecurso == 2) // luz?
			{
				txtGastoHj.setText(String.valueOf(gastoHoje.getNrWatts()));
				txtRsGastoHj.setText(String.valueOf(gastoHoje.getVlrGastLuz()));
			}
			return true;
		}
		else
			return false;
	}

	private int descobrirTipoGrafico()
	{
		int tipoGrafico = 0;
		final RadioGroup radioGroupTipoGrafico = (RadioGroup)findViewById(id.rgTipoGrafico);
		int idRadioButton = radioGroupTipoGrafico.getCheckedRadioButtonId();
		if (idRadioButton == id.rbConsumo)
			tipoGrafico = 1;
		if(idRadioButton == id.rbValorGasto)
			tipoGrafico = 2;

		return tipoGrafico;
	}

	private int descobrirPeriodoComparacaoMaiorConsumo()
	{
		int tipoGrafico;
		if (mes > 0) // selecionou o mes?
		{
			if(dia > 0)
				tipoGrafico = 2;
			else
				tipoGrafico = 1;
		}
		else
			tipoGrafico = -1;
		return tipoGrafico;
	}
	private int validarInt(String sInt)
	{
		int inteiro;
		try
		{
			inteiro = Integer.parseInt(sInt);
		}
		catch (Exception e)
		{
			inteiro = 0;
		}
		return inteiro;
	}
	@Override
	public void onClick(View view) {
		if(view == btnGrafico) {
			mes = spMes.getSelectedItemPosition();
			if (mes > 0) // selecionou o mes?
			{
				// Pega o mes selecionado
				sMes = "";
				if (mes < 10) {
					sMes += "0";
				}
				sMes += String.valueOf(mes);
				ano = spAno.getSelectedItemPosition();
				ano += 2015;
				sAno = String.valueOf(ano); // ano selecionado
				dia = spDia.getSelectedItemPosition();
				sDia = "";
				if (dia > 0) {
					if (dia < 10) {
						sDia = "0";
					}
				}
				sDia += dia;
				int tipoGrafico = descobrirTipoGrafico();

				// 0 = nada
				// 1 = mes
				// 2 = dia
				int indTipoComparacaoMaiorConsumo = 0;
				if(chkCompararMaiorConsumo.isChecked())
					indTipoComparacaoMaiorConsumo = descobrirPeriodoComparacaoMaiorConsumo();
				if(indTipoComparacaoMaiorConsumo == -1)
				{
					Toast.makeText(TelaDeHistorico.this, "Para gerar o gráfico baseado no maior consumo, é preciso selecionar o período.", Toast.LENGTH_LONG).show();
					return;
				}
				int fgCompararOutrasResidencias = chkCompararOutrasResidencias.isChecked() ? 1 : 0;

				int vincularAguaLuz = chkCruzarDados.isChecked() ? 1 : 0;

				int indIgualNrMordador;
				int indIgualNrComodo;

				if(chkIgualNrMorador.isChecked())
					indIgualNrMordador = Integer.parseInt(session.getResidencia().getNrMorador());
				else
					indIgualNrMordador = validarInt(edtNrMorador.getText().toString());


				if(chkIgualNrComodo.isChecked())
					indIgualNrComodo = Integer.parseInt(session.getResidencia().getNrComodos());
				else
					indIgualNrComodo = validarInt(edtNrComodos.getText().toString());

				Intent intent = new Intent();
				intent.setClass(TelaDeHistorico.this, TelaGrafico.class);
				intent.putExtra("absClasse", absFactoryRecurso);
				intent.putExtra("dia", sDia);
				intent.putExtra("mes", sMes);
				intent.putExtra("ano", sAno);
				// filtros
				intent.putExtra("tipoGrafico", tipoGrafico);
				intent.putExtra("indTipoComparacaoMaiorConsumo", indTipoComparacaoMaiorConsumo);
				intent.putExtra("fgCompararOutrasResidencias",fgCompararOutrasResidencias);
				intent.putExtra("nrMorador",indIgualNrMordador);
				intent.putExtra("nrComodo",indIgualNrComodo);
				intent.putExtra("vincularAguaLuz",vincularAguaLuz);
				intent.putExtra("fgTelaHistorico",1);
				//
				startActivity(intent);
				finish();
			}
			else
				Toast.makeText(TelaDeHistorico.this, "Para gerar o gráfico, selecione ao menos o mês.", Toast.LENGTH_SHORT).show();
		}
	}


	public void onBackPressed() // precionou o voltar do telefone?
	{ // Sim, volta para a página anterior
		Intent intent = new Intent();
		// Para chamar a próxima tela tem que dizer qual e a tela atual, e dpois a próxima tela( a que vai ser chamada)
		intent.setClass(TelaDeHistorico.this, TelaMenu.class);
		intent.putExtra("absClasse", absFactoryRecurso);
		startActivity(intent); // chama a próxima tela
		finish();
	}
}
