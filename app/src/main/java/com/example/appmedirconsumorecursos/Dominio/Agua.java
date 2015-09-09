package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.AbsRecurso;
import com.example.appmedirconsumorecursos.R;

public class Agua extends AbsRecurso {
	
	protected void setNome()
	{
		nome = "Água";
	}
	
	protected void setIdImage()
	{
		idIcone = R.drawable.icone_agua;
	}

	@Override
	public void popularMap(EntidadeDominio entidadeDominio, String acao, String nomeClasse) {

	}
}
