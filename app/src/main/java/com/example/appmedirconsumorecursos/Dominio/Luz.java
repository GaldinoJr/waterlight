package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.AbsRecurso;
import com.example.appmedirconsumorecursos.R;

public class Luz extends AbsRecurso {
	protected void setNome()
	{
		nome = "Luz";
	}
	
	protected void setIdImage()
	{
		idIcone = R.drawable.icone_luz;
	}
}
