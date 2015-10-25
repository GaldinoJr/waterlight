package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;

import com.example.appmedirconsumorecursos.Dominio.GastoHora;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Galdino on 24/10/2015.
 */
public class GastoHoraViewHelper  implements IViewHelper {
    private GastoHora gastoHora;

    public EntidadeDominio getEntidade(Map request)
    {
        try {
            gastoHora = new GastoHora();
            gastoHora.setId((String) request.get(gastoHora.DF_ID));
            gastoHora.setDtInclusao(fomatarData((String) request.get(gastoHora.DF_dt_inclusao)));
            if(request.get(gastoHora.DF_vlrGastoAgua)!= null)
                gastoHora.setVlrGastoAgua(Double.parseDouble((String) request.get(gastoHora.DF_vlrGastoAgua)));
            if(request.get(gastoHora.DF_vlrGastoAgua)!= null)
                gastoHora.setVlrGastLuz(Double.parseDouble((String) request.get(gastoHora.DF_vlrGastLuz)));
            if(request.get(gastoHora.DF_vlrGastoAgua)!= null)
                gastoHora.setNrWatts(Double.parseDouble((String) request.get(gastoHora.DF_nrWatts)));
            if(request.get(gastoHora.DF_vlrGastoAgua)!= null)
                gastoHora.setNrMetroCubicoAgua(Double.parseDouble((String) request.get(gastoHora.DF_nrMetroCubicoAgua)));
            gastoHora.setCdResidencia(Integer.parseInt((String) request.get(gastoHora.DF_cdResidencia)));
            gastoHora.setsDtInclusao((String) request.get(gastoHora.DF_AUX_DATA));
            return gastoHora;
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
        Date dc;
        try
        {
            df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dc = df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
}