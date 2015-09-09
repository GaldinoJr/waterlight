package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoHojeViewHelper implements IViewHelper {
    private GastoHoje gastoHoje;

    public EntidadeDominio getEntidade(Map request)
    {
        try {
            gastoHoje = new GastoHoje();
            gastoHoje.setId((String) request.get(gastoHoje.DF_ID));
            gastoHoje.setDtUltimaRegistroDia(fomatarData((String) request.get(gastoHoje.DF_dt_ultimo_registro_dia)));
            gastoHoje.setVlrGastoAgua(Double.parseDouble((String) request.get(gastoHoje.DF_vlrGastoAgua)));
            gastoHoje.setVlrGastLuz(Double.parseDouble((String) request.get(gastoHoje.DF_vlrGastLuz)));
            gastoHoje.setNrWatts(Double.parseDouble((String) request.get(gastoHoje.DF_nrWatts)));
            gastoHoje.setNrMetroCubicoAgua(Double.parseDouble((String) request.get(gastoHoje.DF_nrMetroCubicoAgua)));
            gastoHoje.setCdResidencia(Integer.parseInt((String) request.get(gastoHoje.DF_cdResidencia)));
            return gastoHoje;
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
