package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.R;

public class Luz extends AbsRecurso {
	protected void setIdRecurso()
	{
		id = "2";
	}

	protected void setNome()
	{
		nome = "Luz";
	}
	
	protected void setIdImage()
	{
		idIcone = R.drawable.icone_luz75x113;
	}

}
