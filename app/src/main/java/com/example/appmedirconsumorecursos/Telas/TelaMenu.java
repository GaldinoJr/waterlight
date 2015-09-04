package com.example.appmedirconsumorecursos.Telas;

import com.example.appmedirconsumorecursos.AbsRecurso;
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
}
