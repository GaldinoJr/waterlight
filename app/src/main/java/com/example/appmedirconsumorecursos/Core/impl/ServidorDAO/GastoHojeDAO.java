package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Galdino on 07/09/2015.
 */
public class GastoHojeDAO extends AbstractServerDAO {
    private List<EntidadeDominio> listGastoHoje;
    private GastoHoje gastoHoje;

    // para o grafico
    private int qtdResidencias = 0;

    private List<EntidadeDominio> listEntDom;
    private Resultado resultado;
    private Residencia residencia;
    private Controler controler;
    private Session session;

    public GastoHojeDAO() {
        super( GastoHoje.DF_NOME_TABELA, GastoHoje.DF_CD_TABELA, GastoHoje.DF_NOME_PHP);
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        String retornoJason;
        gastoHoje = (GastoHoje) entidade;
        String query;
        if(gastoHoje.getFitro_indTipoComparacaoMaiorConsumo() == 0 )
        {
            retornoJason = "";
            JSONObject jo = new JSONObject();
            try {
                query = "SELECT * FROM " + GastoHoje.DF_NOME_TABELA + " WHERE 1 = 1";
                if (gastoHoje.getCdResidencia() != null)
                    query += " AND cd_residencia = " + String.valueOf(gastoHoje.getCdResidencia());
                if (gastoHoje.getsDtUltimoRegistroDia() != null)
                {
                   // query += " AND dt_ultimo_registro_dia = STR_TO_DATE( '" + gastoHoje.getsDtUltimoRegistroDia() + "' ,'%Y-%m-%d %H:%i:%s')";
                    query += " AND DATE_FORMAT(dt_ultimo_registro_dia,'%Y-%m-%d') = '" + gastoHoje.getsDtUltimoRegistroDia() + "'";
                }
                if (gastoHoje.getsDtInicialBusca() != null && gastoHoje.getsDtFinalBusca() != null) {
                    query += " AND dt_ultimo_registro_dia " +
                            " BETWEEN '" + gastoHoje.getsDtInicialBusca() + "' AND '" + gastoHoje.getsDtFinalBusca() + "' " +
                            " AND concat(DATE_FORMAT(dt_ultimo_registro_dia ,'%H'), ':', DATE_FORMAT(dt_ultimo_registro_dia,  '%i')) BETWEEN '23:00' AND '23:59'";
                }
                //            if(!TextUtils.isEmpty(residencia.getNome()))
                //                query += " AND ds_nome = '" + residencia.getNome() +"'";


                if(gastoHoje.getFiltro_fgTodosRegistros() == 1) // usado para indicar que vai trazer todos os registros
                    query += " ORDER BY dt_ultimo_registro_dia ASC;";
                else
                    query += " ORDER BY dt_ultimo_registro_dia DESC LIMIT 1;";
                //
                jo.put("query", query);
                String json = jo.toString();
                //
                retornoJason = openConnection("get-json", json);
                //
                listGastoHoje = new ArrayList<EntidadeDominio>();
                JSONArray jsonArray = new JSONArray(retornoJason);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = new JSONObject(jsonArray.getString(i));
                    GastoHoje g = new GastoHoje();
                    g.setId(jsonObject.getString(g.DF_CD_TABELA));
                    g.setDtUltimaRegistroDia(fomatarData(jsonObject.getString(g.DF_dt_ultimo_registro_dia)));
                    g.setVlrGastoAgua(jsonObject.getDouble(g.DF_vlrGastoAgua));
                    g.setVlrGastLuz(jsonObject.getDouble(g.DF_vlrGastLuz));
                    g.setNrWatts(jsonObject.getDouble(g.DF_nrWatts));
                    g.setNrMetroCubicoAgua(jsonObject.getDouble(g.DF_nrMetroCubicoAgua));
                    g.setCdResidencia(jsonObject.getInt(g.DF_cdResidencia));
                    listGastoHoje.add(g);
                }
                if (listGastoHoje.size() > 0)
                    return listGastoHoje;
                else
                    return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            String colunaParaComparacao = "";
            String dataParaComparacao = "";
            int cdResidenciaPesquisa;
            listGastoHoje = new ArrayList<EntidadeDominio>();
            if(gastoHoje.getFiltro_idRecurso() == 1) // Água?
                colunaParaComparacao = "nr_metro_cubico_agua";
            else if(gastoHoje.getFiltro_idRecurso() == 2) // luz
                colunaParaComparacao = "nr_watts";
            if(gastoHoje.getFitro_fgCompararOutrasResidencias() == 1) // vai comparar com outras residencias?
            {
                instanciarClasses(false); // operação no servidor
                residencia.popularMap(residencia, "consultar", Residencia.class.getName());
                resultado = controler.doPost(residencia.getMap());
                listEntDom = resultado.getEntidades();
                qtdResidencias = listEntDom.size();
            }
            else
                qtdResidencias = 1;
            for(int j = 0; j<qtdResidencias; j++) {
                try {
                    JSONObject jo = new JSONObject();
                    retornoJason = "";
                    conexao = null; // ZERA A CONEXÃO INDICANDO QUE FARÁ UMA NOVA
                    cdResidenciaPesquisa = 0;
                    if(j == 0)
                        cdResidenciaPesquisa = gastoHoje.getCdResidencia();
                    else
                    {
                        if(gastoHoje.getFitro_fgCompararOutrasResidencias() == 1)
                        {
                            residencia = (Residencia)listEntDom.get(j);
                            if(Integer.parseInt(residencia.getId()) != gastoHoje.getCdResidencia())
                                cdResidenciaPesquisa = Integer.parseInt(residencia.getId());
                        }
                    }
                    if(cdResidenciaPesquisa != 0)
                    {
                        // Seleciona o maior valor de cada casa, no periodo escolhido
                        query = "SELECT gh.cd_gasto_hoje, gh.nr_watts, gh.nr_metro_cubico_agua, gh.vlr_gasto_agua, gh.vlr_gasto_luz, gh.cd_residencia, gh.dt_ultimo_registro_dia";
                        query += " FROM " + GastoHoje.DF_NOME_TABELA + " gh";
                        if((cdResidenciaPesquisa != gastoHoje.getCdResidencia()) &&
                                (gastoHoje.getFitro_nrComodo() > 0 || gastoHoje.getFitro_nrMorador() > 0))
                        {
                            query += " INNER JOIN tb_residencia r ON r.cd_residencia = gh.cd_residencia";
                            if(gastoHoje.getFitro_nrComodo() > 0)
                                query += " AND r.nr_comodos = " + String.valueOf(gastoHoje.getFitro_nrComodo());
                            if(gastoHoje.getFitro_nrMorador() > 0)
                                query += " AND r.nr_morador = " + gastoHoje.getFitro_nrMorador();
                        }
                        query += " WHERE 1 = 1";
                        if(cdResidenciaPesquisa > 0)
                            query += " AND gh.cd_residencia = " + String.valueOf(cdResidenciaPesquisa);
                        if(j == 0) // Na primeira vez, busca os dados da residencia que usa o app
                        {
                            if (gastoHoje.getsDtInicialBusca() != null && gastoHoje.getsDtFinalBusca() != null)
                            {
                                // Validade data e hora(TEM QUE SER A PRIMEIRA HORA DO DIA)
                                query += " AND gh.dt_ultimo_registro_dia" +
                                        " BETWEEN '" + gastoHoje.getsDtInicialBusca() + "' AND '" + gastoHoje.getsDtFinalBusca() + "' " +
                                        " AND concat(DATE_FORMAT(gh.dt_ultimo_registro_dia ,'%H'), ':', DATE_FORMAT(gh.dt_ultimo_registro_dia,  '%i')) BETWEEN '23:00' AND '23:59'";


                                if (gastoHoje.getFitro_indTipoComparacaoMaiorConsumo() != 0) {
                                    query +=
                                            " AND gh." + colunaParaComparacao + " = " +
                                                    "(" +
                                                    " SELECT MAX(" + colunaParaComparacao + ") FROM tb_gasto_hoje WHERE dt_ultimo_registro_dia " +
                                                    " BETWEEN '" + gastoHoje.getsDtInicialBusca() + "' AND '" + gastoHoje.getsDtFinalBusca() + "' " +
                                                    "AND concat(DATE_FORMAT(dt_ultimo_registro_dia ,'%H'), ':', DATE_FORMAT( dt_ultimo_registro_dia,  '%i'))" +
                                                    "BETWEEN '23:00' AND '23:59'";
                                    if (cdResidenciaPesquisa > 0)
                                        query += " AND cd_residencia = " + String.valueOf(cdResidenciaPesquisa);

                                    if ((cdResidenciaPesquisa != gastoHoje.getCdResidencia()) &&
                                            (gastoHoje.getFitro_nrComodo() > 0 || gastoHoje.getFitro_nrMorador() > 0)) {
                                        if (gastoHoje.getFitro_nrComodo() > 0)
                                            query += " AND r.nr_comodos = " + String.valueOf(gastoHoje.getFitro_nrComodo());
                                        if (gastoHoje.getFitro_nrMorador() > 0)
                                            query += " AND r.nr_morador = " + gastoHoje.getFitro_nrMorador();
                                    }

                                    query += ")";
                                }
                            }
                        }
                        else
                        {
                            if (gastoHoje.getsDtInicialBusca() != null && gastoHoje.getsDtFinalBusca() != null)
                            {
                                // Validade data e hora(TEM QUE SER A PRIMEIRA HORA DO DIA)
                                query += " AND DATE_FORMAT(gh.dt_ultimo_registro_dia, '%Y-%m-%d') = STR_TO_DATE('" + dataParaComparacao + "', '%Y-%m-%d')" +
                                        " AND concat(DATE_FORMAT(gh.dt_ultimo_registro_dia ,'%H'), ':', DATE_FORMAT(gh.dt_ultimo_registro_dia,  '%i')) BETWEEN '23:00' AND '23:59'";
                            }

                            if (gastoHoje.getFitro_indTipoComparacaoMaiorConsumo() != 0)
                            {
                                if (cdResidenciaPesquisa > 0)
                                    query += " AND cd_residencia = " + String.valueOf(cdResidenciaPesquisa);

                                if ((cdResidenciaPesquisa != gastoHoje.getCdResidencia()) &&
                                        (gastoHoje.getFitro_nrComodo() > 0 || gastoHoje.getFitro_nrMorador() > 0)) {
                                    if (gastoHoje.getFitro_nrComodo() > 0)
                                        query += " AND r.nr_comodos = " + String.valueOf(gastoHoje.getFitro_nrComodo());
                                    if (gastoHoje.getFitro_nrMorador() > 0)
                                        query += " AND r.nr_morador = " + gastoHoje.getFitro_nrMorador();
                                }
                            }
                        }
                        //query += " ORDER BY dt_ultimo_registro_dia DESC LIMIT 1;";

                        //
                        jo.put("query", query);
                        String json = jo.toString();
                        //
                        retornoJason = openConnection("get-json", json);
                        //

                        JSONArray jsonArray = new JSONArray(retornoJason);
                        JSONObject jsonObject;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = new JSONObject(jsonArray.getString(i));
                            GastoHoje g = new GastoHoje();
                            g.setId(jsonObject.getString(g.DF_CD_TABELA));
                            g.setDtUltimaRegistroDia(fomatarData(jsonObject.getString(g.DF_dt_ultimo_registro_dia)));
                            g.setVlrGastoAgua(jsonObject.getDouble(g.DF_vlrGastoAgua));
                            g.setVlrGastLuz(jsonObject.getDouble(g.DF_vlrGastLuz));
                            g.setNrWatts(jsonObject.getDouble(g.DF_nrWatts));
                            g.setNrMetroCubicoAgua(jsonObject.getDouble(g.DF_nrMetroCubicoAgua));
                            g.setCdResidencia(jsonObject.getInt(g.DF_cdResidencia));
                            if(j == 0)
                                dataParaComparacao = jsonObject.getString(g.DF_dt_ultimo_registro_dia);
                            listGastoHoje.add(g);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } // for
            if (listGastoHoje.size() > 0)
                return listGastoHoje;
            else
                return null;
        }
        return null;
    }
    private Date fomatarData(String data)
    {
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dc;
        try
        {
            dc = (java.util.Date)df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }
    private void instanciarClasses(boolean fgSql)
    {
        session = Session.getInstance();
        if(!fgSql)
            session.setContext(null);
        listEntDom = new LinkedList<EntidadeDominio>();
        resultado = new Resultado();
        residencia = new Residencia();
        controler = new Controler();
    }
}
