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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
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
	private AbsRecurso absRecurso;
	private Integer idRecurso;
	// sempre usar com requisições
	private Session session;
	private List<EntidadeDominio> listEntDom;

	//
	private GastoMes gastoMes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int i, j = 2015;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_de_historico);
		//
		txtNomeRecurso = (TextView)findViewById(id.txtNomeRecurso);
		txtGastoHj = (TextView)findViewById(id.txtGastoHj);
		txtRsGastoHj = (TextView)findViewById(id.txtR$GastoHj);
		txtGastoAtual = (TextView)findViewById(id.txtGastoAtua);
		txtRsGastoAtual = (TextView)findViewById(id.txtR$GastoAtual);
		txtMediaFinal = (TextView)findViewById(id.txtMediaFina);
		txtRsMediaFinal = (TextView)findViewById(id.txtR$MediaFinal);
		btnGrafico = (Button)findViewById(id.btnGrafico);
		btnGrafico.setOnClickListener(this);
		spDia = (Spinner)findViewById(id.spDia);
		spMes = (Spinner)findViewById(id.spMes);
		spAno = (Spinner)findViewById(id.spAno1);
		
		imgRecurso = (ImageView)findViewById(id.imgRecurso);
		//
		dados = getIntent(); // Recebe os dados da tela anterior
		absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
		txtNomeRecurso.setText(absRecurso.getNome()); // Recebe o nome do recurso e manda pra tela
		imgRecurso.setImageResource(absRecurso.getIdIcone()); // Recebe o id da imagem e manda pra tela
		idRecurso = Integer.parseInt(absRecurso.getIdRecurso());
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
					gastoMes = new GastoMes();
					try {
						int dia = spDia.getSelectedItemPosition();
						int ano = spAno.getSelectedItemPosition();
						ano += 2015;
						String sAno = String.valueOf(ano);
						String sDia = "";
						if (dia > 0)
						{
							if (dia < 10) {
								sDia = "0";
							}
							sDia += String.valueOf(dia);
						} else
							sDia = "01";

						gastoMes.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
						String data = sDia + "/" + position + "/" + String.valueOf(ano);
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
						}
						else
						{
							if (dia > 0)
								Toast.makeText(TelaDeHistorico.this,"Dia " + sDia + " de " + spMes.getSelectedItem().toString() + " de "+ sAno +" não contem registros.", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(TelaDeHistorico.this,  spMes.getSelectedItem().toString()+" de "+ sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
							limparCampos();
						}
					} catch (Exception e) {
						Toast.makeText(TelaDeHistorico.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
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
						int dia = position;
						String sDia = "";
						if (dia > 0)  // selecionou o dia?
						{
							//dia += 1;
							if (dia < 10) {
								sDia += "0";
							}
							sDia += String.valueOf(dia);

						}
						else
							sDia += "01";
						int mes = spMes.getSelectedItemPosition();
						if (mes > 0) // selecionou o mes?
						{
							String sMes = "";
							if (mes < 10) {
								sMes += "0";
							}
							sMes += String.valueOf(mes);
							int ano = spAno.getSelectedItemPosition();
							ano += 2015;
							String sAno = String.valueOf(ano);
							gastoMes.setCdResidencia(Integer.parseInt(session.getResidencia().getId()));
							String data = sDia + "/" + sMes + "/" + String.valueOf(ano);
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
							}
							else
							{
								if (dia > 0)  // selecionou o dia?
									Toast.makeText(TelaDeHistorico.this, "Dia " + sDia + " de " + spMes.getSelectedItem().toString() +" de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(TelaDeHistorico.this, spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
								limparCampos();
							}
						}
						else
						{
							Toast.makeText(TelaDeHistorico.this, "Para fazer a busca, primeiro selecione o mês.", Toast.LENGTH_SHORT).show();
						}

					}
					catch (Exception e)
					{
						Toast.makeText(TelaDeHistorico.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
					}
				}
				else
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

	public void onBackPressed() // precionou o voltar do telefone?
	{ // Sim, volta para a p�gina anterior 
		Intent intent = new Intent();
		// Para chamar a pr�xima tela tem que dizer qual e a tela atual, e dpois a pr�xima tela( a que vai ser chamada)
		intent.setClass(TelaDeHistorico.this, TelaMenu.class);
		intent.putExtra("absClasse", absRecurso);
		startActivity(intent); // chama a pr�xima tela
		finish();
	}

	@Override
	public void onClick(View view) {
		if(view == btnGrafico)
		{
			Intent intent = new Intent();
			intent.setClass(TelaDeHistorico.this, TelaGrafico.class);
			intent.putExtra("absClasse", absRecurso);
			startActivity(intent);
			finish();
		}
	}
	private void limparCampos()
	{
		txtGastoHj.setText("");
		txtRsGastoHj.setText("");
	}
}
