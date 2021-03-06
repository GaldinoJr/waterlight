package com.example.appmedirconsumorecursos.Dominio;

import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

// Serializable para poder passar o objeto entre as telas
public abstract class AbsFactoryRecurso extends EntidadeDominio implements Serializable
{
	// Padrão design pattern: abstract Factory
	protected String nome;
	protected int idIcone;
//	protected String id;

	protected abstract void setNome();
	protected abstract void setIdImage();
	protected abstract void setIdRecurso();

	public String getNome()
	{
		setNome();
		return nome;
	}
	
	public int getIdIcone() {
		setIdImage();
		return idIcone;
	}

	public String getIdRecurso() {
		setIdRecurso();
		return id;
	}
	/*
	 * responsível por popular os campos do objeto desseralizado.
	private void readObject(ObjectInputStream in) 
	          throws IOException, ClassNotFoundException {
	    in.defaultReadObject();
	    this.nome = getNome();
	  }
	  */
}
