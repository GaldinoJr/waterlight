package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoMes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Galdino on 14/09/2015.
 */
public class GastoMesViewHelper implements IViewHelper {
    private GastoMes gastoMes;

    public EntidadeDominio getEntidade(Map request)
    {
        try {
            gastoMes = new GastoMes();
            gastoMes.setId((String) request.get(gastoMes.DF_ID));
            gastoMes.setVlrGastoAgua(Double.parseDouble((String) request.get(gastoMes.DF_vlrGastoAgua)));
            gastoMes.setVlrGastLuz(Double.parseDouble((String) request.get(gastoMes.DF_vlrGastLuz)));
            gastoMes.setNrWatts(Double.parseDouble((String) request.get(gastoMes.DF_nrWatts)));
            gastoMes.setNrMetroCubicoAgua(Double.parseDouble((String) request.get(gastoMes.DF_nrMetroCubicoAgua)));
            gastoMes.setDtInclusao(fomatarData((String) request.get(gastoMes.DF_dt_inclusao)));
            gastoMes.setCdResidencia(Integer.parseInt((String) request.get(gastoMes.DF_cdResidencia)));
            return gastoMes;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public EntidadeDominio setView(EntidadeDominio entidade, Map response) {
        return null;
    }
    private Date fomatarData(String data)
    {
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        Date dc;
        try
        {
            dc = df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
}
