package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import android.text.TextUtils;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.Agua;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import java.util.Map;

/**
 * Created by Galdino on 24/08/2015.
 */
public class ResidenciaViewHelper implements IViewHelper {


    private Residencia residencia;

    public EntidadeDominio getEntidade(Map request) {
        String aux;
        residencia = new Residencia();
        residencia.setId((String) request.get(residencia.DF_ID));
        residencia.setNome((String) request.get(residencia.DF_NOME));
        residencia.setEndereco((String) request.get(residencia.DF_ENDERECO));
        residencia.setBairro((String) request.get(residencia.DF_BAIRRO));
        residencia.setCep((String) request.get(residencia.DF_CEP));
        residencia.setCidade((String) request.get(residencia.DF_CIDADE));
        residencia.setUf((String) request.get(residencia.DF_UF));
        residencia.setNrMorador((String) request.get(residencia.DF_MORADOR));
        residencia.setNrComodos((String) request.get(residencia.DF_COMODOS));
        residencia.setSenha((String) request.get(residencia.DF_SENHA));

        aux = ((String) request.get(residencia.DF_NUMERO));
        if(!TextUtils.isEmpty(aux))
            residencia.setNumero(Integer.parseInt(aux));
        aux = ((String)request.get(residencia.DF_FG_EXCLUIDO));
        if(!TextUtils.isEmpty(aux))
            residencia.setFgExcluido(Integer.parseInt(aux));
        return residencia;
    }

    public EntidadeDominio setView(EntidadeDominio entidade, Map response) {
        return null;
    }
}