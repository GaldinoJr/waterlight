package com.example.appmedirconsumorecursos.Telas;

import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.Agua;
import com.example.appmedirconsumorecursos.Dominio.Luz;
import com.example.appmedirconsumorecursos.R;
import com.example.appmedirconsumorecursos.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelaPrincipal extends Activity implements OnClickListener {
	private Button btnAgua, 
				   btnLuz;
	private AbsRecurso absRecurso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal);
		// 
		btnAgua = (Button)findViewById(id.btnAgua);
		btnLuz = (Button)findViewById(id.btnLuz);
		// 
		btnAgua.setOnClickListener(this);
		btnLuz.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_principal, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v == btnAgua)
		{
			absRecurso = new Agua();
			chamarTelaCorrespondente();
		}
		if(v == btnLuz)
		{
			absRecurso = new Luz();
			chamarTelaCorrespondente();
		}
	}
	private void chamarTelaCorrespondente()//Class<?> classe)
	{	
		Intent intent = new Intent();
		intent.setClass(TelaPrincipal.this, TelaMenu.class);
		intent.putExtra("absClasse", absRecurso);
		startActivity(intent);
		finish();
	}

}
