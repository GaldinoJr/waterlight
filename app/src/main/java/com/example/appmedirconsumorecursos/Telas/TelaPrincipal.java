package com.example.appmedirconsumorecursos.Telas;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.Agua;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Luz;
import com.example.appmedirconsumorecursos.R;
import com.example.appmedirconsumorecursos.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class TelaPrincipal extends Activity implements OnClickListener {
	private ImageButton btnAgua,
				   		btnLuz,
						btnConfig,
						btnLogoff;
	//
	private Session session;
	private ConfiguracaoSistema configSistema;
	private List<EntidadeDominio> listEntDom;
	//
	private AbsRecurso absRecurso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal);
		// 
		btnAgua = (ImageButton)findViewById(id.btnAgua);
		btnLuz = (ImageButton)findViewById(id.btnLuz);
		btnConfig = (ImageButton)findViewById(id.btnConfig);
		btnLogoff = (ImageButton)findViewById(id.btnLogoff);
		// 
		btnAgua.setOnClickListener(this);
		btnLuz.setOnClickListener(this);
		btnConfig.setOnClickListener(this);
		btnLogoff.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_principal, menu);
		return true;
	}
	//@Override
	//public boolean onOptionsItemSelected(MenuItem item) {
		//Intent intent;
		//session = Session.getInstance();
//		switch (item.getItemId()) {
//			case id.menu_logoff:
//				configSistema = session.getConfiguracaoSistema();
//				configSistema.getMapInstance();
//				configSistema.setMapFgLogarAutomaticamente(0);
//				listEntDom = configSistema.operar(this, true, Controler.DF_ALTERAR);
//				//
//				intent = new Intent();
//				intent.setClass(TelaPrincipal.this, TelaLogin.class);
//				intent.putExtra("logoff", "1");
//				startActivity(intent);
//				finish();
////				Toast.makeText(this, "MENU EDIT", Toast.LENGTH_LONG).show();
//				return true;
//			case id.menu_configuracao:
//				session.setContext(this);
//				intent = new Intent();
//				intent.setClass(TelaPrincipal.this, Tela_configuracao_aplicativo.class);
//				startActivity(intent);
//				finish();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
	//}
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
		if(v == btnConfig)
		{
			Intent intent = new Intent();
			intent.setClass(TelaPrincipal.this, Tela_configuracao_aplicativo.class);
			startActivity(intent);
			finish();
		}
		if(v == btnLogoff)
		{
			session = Session.getInstance();
			configSistema = session.getConfiguracaoSistema();
			configSistema.getMapInstance();
			configSistema.setMapFgLogarAutomaticamente(0);
			listEntDom = configSistema.operar(this, true, Controler.DF_ALTERAR);
			//
			Intent intent = new Intent();
			intent.setClass(TelaPrincipal.this, TelaLogin.class);
			intent.putExtra("logoff", "1");
			startActivity(intent);
			finish();
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
