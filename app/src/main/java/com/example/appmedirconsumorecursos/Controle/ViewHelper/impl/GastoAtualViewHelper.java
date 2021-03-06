package com.example.appmedirconsumorecursos.Controle.ViewHelper.impl;

import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoAtualViewHelper implements IViewHelper {
    private GastoAtual gastoAtual;

    public EntidadeDominio getEntidade(Map request)
    {
        try {
            gastoAtual = new GastoAtual();
            gastoAtual.setId((String) request.get(gastoAtual.DF_ID));
            gastoAtual.setDtInicioMedicao(fomatarData((String) request.get(gastoAtual.DF_dtInicioMedicao)));
            gastoAtual.setDtUltimaMedicao(fomatarData((String) request.get(gastoAtual.DF_dtUltimaMedicao)));
            gastoAtual.setsDtUltimaMedicao((String) request.get(gastoAtual.DF_dtUltimaMedicao));
            gastoAtual.setsDtInicioMedicao((String) request.get(gastoAtual.DF_dtInicioMedicao));
            if(request.get(gastoAtual.DF_vlrGastoAgua)!= null)
                gastoAtual.setVlrGastoAgua(Double.parseDouble((String) request.get(gastoAtual.DF_vlrGastoAgua)));
            if(request.get(gastoAtual.DF_vlrGastLuz)!= null)
                gastoAtual.setVlrGastLuz(Double.parseDouble((String) request.get(gastoAtual.DF_vlrGastLuz)));
            if(request.get(gastoAtual.DF_nrWatts)!= null)
                gastoAtual.setNrWatts(Double.parseDouble((String) request.get(gastoAtual.DF_nrWatts)));
            if(request.get(gastoAtual.DF_nrMetroCubicoAgua)!= null)
                gastoAtual.setNrMetroCubicoAgua(Double.parseDouble((String) request.get(gastoAtual.DF_nrMetroCubicoAgua)));
            if(request.get(gastoAtual.DF_cdResidencia)!= null)
                gastoAtual.setCdResidencia(Integer.parseInt((String) request.get(gastoAtual.DF_cdResidencia)));

            if(request.get(gastoAtual.DF_FILTRO_fgTodosRegistros)!= null)
                gastoAtual.setFiltro_fgTodosRegistros(Integer.parseInt((String) request.get(gastoAtual.DF_FILTRO_fgTodosRegistros)));
            return gastoAtual;
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