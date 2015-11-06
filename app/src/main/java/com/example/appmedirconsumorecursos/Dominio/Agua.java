package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.R;

public class Agua extends AbsFactoryRecurso {
	protected void setIdRecurso()
	{
		id = "1";
	}

	protected void setNome()
	{
		nome = "Água";
	}
	
	protected void setIdImage()
	{
		idIcone = R.drawable.icon_agua_75x125;
	}

}
