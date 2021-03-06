package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsFactoryRecurso;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoHora;
import com.example.appmedirconsumorecursos.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class TelaRelatorio1 extends Activity {
    private ProgressBar progressBar;
    private TextView txtNomeRecurso;
    private Spinner spDia,
                    spMes,
                    spAno;
    private ListView lvRelatorio;
    private ArrayAdapter<String> aaMes;
    private ArrayAdapter<Integer> aaAno;
    private ArrayAdapter<String> aaDia;
    private String[] vetSmes = {"",
            "Janeiro" , "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
            "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    //
    boolean retorno;
    String data;
    private int teste = 0;
    //
    private Integer[] vetNano;
    private String[] vetDia;
    private ImageView imgRecurso;
    private Intent dados;
    private AbsFactoryRecurso absFactoryRecurso;
    private Integer idRecurso;

    // sempre usar com requisições
    private Session session;
    private List<EntidadeDominio> listEntDom;
    int dia, mes, ano;
    private String sDia, sMes, sAno;
    private int testeSpDia;
    //
    private GastoHoje gastoHoje;
    private GastoHora gastoHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorio);
        int i, j = 2015;
        //
        //txtNomeRecurso = (TextView)findViewById(R.id.txtNomeRecurso);
        spDia = (Spinner)findViewById(R.id.spDia);
        spMes = (Spinner)findViewById(R.id.spMes);
        spAno = (Spinner)findViewById(R.id.spAno1);
        lvRelatorio = (ListView)findViewById(R.id.lvRelatorio);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        //
        imgRecurso = (ImageView)findViewById(R.id.imgRecurso);
        //
        dados = getIntent(); // Recebe os dados da tela anterior
        absFactoryRecurso = (AbsFactoryRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
        //txtNomeRecurso.setText(absFactoryRecurso.getNome()); // Recebe o nome do recurso e manda pra tela
        //imgRecurso.setImageResource(absFactoryRecurso.getIdIcone()); // Recebe o id da imagem e manda pra tela
        idRecurso = Integer.parseInt(absFactoryRecurso.getIdRecurso());
        if(idRecurso == 1)
            imgRecurso.setImageResource(R.drawable.icone_agua_transparente_azul_300x500);
        else if(idRecurso == 2)
            imgRecurso.setImageResource(R.drawable.icone_lampada_azul_transparente_azul358x640);
        //
        //
        vetNano = new Integer[85];
        for(i= 0; i < 85; i++, j++) // Popula o sppiner de ano
            vetNano[i] = j;
        //
        vetDia = new String[32];
        vetDia[0] = "";
        for(i = 1; i< 32;i++) // Popula o sppiner de dias
        {
            vetDia[i] = String.valueOf(i);
        }

        // passa para o arrayList o conteudo dos vetores
        aaMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vetSmes);
        aaAno = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vetNano);
        aaDia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vetDia);
        // Passa o arrayList para o Spinner
        spMes.setAdapter(aaMes);
        spAno.setAdapter(aaAno);
        spDia.setAdapter(aaDia);
        //
        // Quando selecionar um item da lista de MESES do SPINNER
        session = Session.getInstance();
        session.setContext(this);
        // SPINNER DE MÊS
        spMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (teste != 0) {
                    //Toast.makeText(TelaDeHistorico.this, "posição: " + position + " \nnome: " + spMes.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    try
                    {
                        mes = spMes.getSelectedItemPosition();
                        if (mes > 0) // selecionou o mes?
                        {
                            dia = spDia.getSelectedItemPosition();
                            ano = spAno.getSelectedItemPosition();
                            ano += 2015;
                            sAno = String.valueOf(ano);
                            sDia = "";
                            sMes = String.valueOf(position);
                            if (dia > 0)
                            {
                                if (dia < 10)
                                {
                                    sDia = "0";
                                }
                                sDia += String.valueOf(dia);
                                data = sDia + "/" + sMes + "/" + String.valueOf(ano);
                                //data += " 00:00:00";
                                retorno = pesquisarGastoNoDia(data);
                                if (!retorno)
                                {
                                    Toast.makeText(TelaRelatorio1.this, "Dia " + sDia + " de " + spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
                                    lvRelatorio.setAdapter(null); // limpa a listView
                                }
                            }
                            else
                            {
                                sDia = "01";
                                data = sDia + "/" + sMes + "/" + String.valueOf(ano);
                                //retorno = pesquisarGastoNoMes(data);
                                //                            new Runnable() {
                                //                                public void run() {
                                //                                    progressBar.setVisibility(View.VISIBLE);
                                retorno = pesquisarGastoNoMes(data);
                                //                                }
                                //                            }.run();

                                progressBar.setVisibility(View.INVISIBLE);
                                if (retorno == false)
                                {
                                    Toast.makeText(TelaRelatorio1.this, spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
                                    lvRelatorio.setAdapter(null); // limpa a listView
                                    //  limparCampos();
                                }
                            }
                        }
                        else
                            lvRelatorio.setAdapter(null); // limpa a listView
                    }
                    catch (Exception e) {
                        Toast.makeText(TelaRelatorio1.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
                        lvRelatorio.setAdapter(null); // limpa a listView
                    }
                } else {
                    teste++;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // SPINNER DIA
        spDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (testeSpDia != 0) {
                    //Toast.makeText(TelaDeHistorico.this, "posição: " + position + " \nnome: " + spMes.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    try
                    {
                        // Pega o mes selecionado
                        mes = spMes.getSelectedItemPosition();
                        // dia selecionado
                        dia = position;
                        if (mes > 0) // selecionou o mes?
                        {
                            sMes = "";
                            if (mes < 10)
                            {
                                sMes += "0";
                            }
                            sMes += String.valueOf(mes);
                            ano = spAno.getSelectedItemPosition();
                            ano += 2015;
                            sAno = String.valueOf(ano); // ano selecionado
                            sDia = "";
                            if (dia > 0)  // selecionou o dia?
                            {
                                if (dia < 10)
                                {
                                    sDia += "0";
                                }
                                sDia += String.valueOf(dia);
                                data = sDia + "/" + sMes + "/" + String.valueOf(ano);
                                //data += " 00:00:00";
                                retorno = pesquisarGastoNoDia(data);
                                if (!retorno) {
                                    Toast.makeText(TelaRelatorio1.this, "Dia " + sDia + " de " + spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
                                    lvRelatorio.setAdapter(null); // limpa a listView
                                }
                            }
                            else
                            {
                                sDia += "01";
                                data = sDia + "/" + sMes + "/" + String.valueOf(ano);

                                retorno = pesquisarGastoNoMes(data);
                                if (!retorno) {
                                    Toast.makeText(TelaRelatorio1.this, spMes.getSelectedItem().toString() + " de " + sAno + " não contem registros.", Toast.LENGTH_SHORT).show();
                                    lvRelatorio.setAdapter(null); // limpa a listView
                                }
                            }
                        }
                        else
                        {
                            if (dia > 0)  // selecionou o dia?
                                Toast.makeText(TelaRelatorio1.this, "Para fazer a busca, primeiro selecione o mês.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TelaRelatorio1.this, "ERROR: #1", Toast.LENGTH_SHORT).show();
                        lvRelatorio.setAdapter(null); // limpa a listView
                    }
                }
                else
                {
                    testeSpDia++;
                }
                // spGrupo.getSelectedItem().toString(); ********************pegar o conteudo em texto do spinner
                //spNomes.setAdapter((SpinnerAdapter) aExPeito);
                //spNomes.setAdapter(aExPeito); // Devolve a lista de exercicios de peito
                //}
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean pesquisarGastoNoDia(String data)
    {
        gastoHora = new GastoHora();
        gastoHora.getMapInstance();
        gastoHora.setMapCdResidencia(session.getResidencia().getId());
        gastoHora.setMapsDtInclusao(data);
        gastoHora.setMapFitro_indTipoComparacaoMaiorConsumo("0");
        gastoHora.setMapFiltro_fgTodosRegistros("1");
        // Mudar a consulta par a
        //listEntDom = gastoHoje.operar(session.getContext(), true, Controler.DF_CONSULTAR); // busca no banco interno
        listEntDom = gastoHora.operar(session.getContext(), false, Controler.DF_CONSULTAR);
        if (listEntDom != null) {
            //gastoHoje = (GastoHoje) listEntDom.get(0);
            // Verificar se é necessário**************
            String sDataMedicao;
            String linha;
            String[] vetSGasto = new String[listEntDom.size()+1];
            //
            double nrTotalConsumo = 0,
                    vlrTotalDinheiro = 0,
                    perConsumo;
            int i,
                qtdRegistros;
            //
            qtdRegistros = listEntDom.size();
            NumberFormat formatarNumero = new DecimalFormat(".##");
            for(i = 0; i < qtdRegistros; i++)
            {
                GastoHora g = (GastoHora) listEntDom.get(i);
                if(idRecurso == 1) // agua?
                {
                    vlrTotalDinheiro += g.getVlrGastoAgua();
                    nrTotalConsumo += g.getNrMetroCubicoAgua();
                }
                else if(idRecurso == 2) // luz?
                {
                    vlrTotalDinheiro += g.getVlrGastLuz();
                    nrTotalConsumo += g.getNrWatts();
                }
            }
            //
            for (i = 0; i < qtdRegistros; i++) {
                GastoHora g = (GastoHora) listEntDom.get(i);
                linha = "";
                sDataMedicao ="";
                //sDataMedicao = extrairDiaDoMes(g.getDtUltimaRegistroDia());
                if(idRecurso == 1) // agua?
                {
                    perConsumo = (g.getNrMetroCubicoAgua() * 100)/nrTotalConsumo;
                    linha = "Consumo da hora: "+extrairHora(g.getDtInclusao()) +"\n"+
                            "M³/h: " + g.getNrMetroCubicoAgua() + "\n" +
                            "R$     : " + g.getVlrGastoAgua() + "\n" +
                            "%       : " + formatarNumero.format(perConsumo);
                }
                else if(idRecurso == 2) // luz?
                {
                    perConsumo = (g.getNrWatts() * 100)/nrTotalConsumo;
                    linha = "Consumo da hora: "+extrairHora(g.getDtInclusao()) +"\n"+
                            "Kw/h: " + g.getNrWatts() + "\n" +
                            "R$     : " + g.getVlrGastLuz() + "\n" +
                            "%       : " + formatarNumero.format(perConsumo);
                }
                sDataMedicao = converterDataParaString(String.valueOf(g.getDtInclusao()));
                vetSGasto[i] = linha + "\nData: " +sDataMedicao;
            }
            linha = "TOTAL\n"+
                    "Qtde de horas analizadas: " + qtdRegistros  + "\n";
            if(idRecurso== 1)
                linha+="M³: ";
            else if(idRecurso ==2)
                linha+="Kw: ";
            String auxTotalConsumo;
            if(nrTotalConsumo < 1)
                auxTotalConsumo = "0" + formatarNumero.format(nrTotalConsumo);
            else
                auxTotalConsumo =formatarNumero.format(nrTotalConsumo);

            String auxTotalValor;
            if(vlrTotalDinheiro < 1)
                auxTotalValor = "0" + formatarNumero.format(vlrTotalDinheiro);
            else
                auxTotalValor =formatarNumero.format(vlrTotalDinheiro);
            linha += auxTotalConsumo + "\n" +
                    "R$:  " +auxTotalValor;
            vetSGasto[qtdRegistros] = linha;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, vetSGasto);

            lvRelatorio.setAdapter(adapter);
            return true;
        }
        else
            return false;
    }

    private boolean pesquisarGastoNoMes(String data)
    {
        gastoHoje = new GastoHoje();
        gastoHoje.getMapInstance();
        gastoHoje.setMapCdResidencia(session.getResidencia().getId());
        gastoHoje.setMapDtInicialBusca(descobrirPrimeiroDiaData(data));
        gastoHoje.setMapDtFinalBusca(descobrirUltimoDiaData(data));
        gastoHoje.setMapFitro_indTipoComparacaoMaiorConsumo("0");
        gastoHoje.setMapFiltro_fgTodosRegistros("1");
        // Mudar a consulta par a
        //listEntDom = gastoHoje.operar(session.getContext(), true, Controler.DF_CONSULTAR); // busca no banco interno
        listEntDom = gastoHoje.operar(session.getContext(), false, Controler.DF_CONSULTAR);
        if (listEntDom != null) {
            //gastoHoje = (GastoHoje) listEntDom.get(0);
            // Verificar se é necessário**************
            String sDataMedicao;
            String linha;
            String[] vetSGasto = new String[listEntDom.size()+1];
            //
            double nrTotalConsumo = 0,
                    vlrTotalDinheiro = 0,
                    perConsumo;
            int i,
                qtdRegistros;
            qtdRegistros = listEntDom.size();
            NumberFormat formatarNumero = new DecimalFormat(".##");
            for(i = 0; i < qtdRegistros; i++)
            {
                GastoHoje g = (GastoHoje) listEntDom.get(i);
                if(idRecurso == 1) // agua?
                {
                    vlrTotalDinheiro += g.getVlrGastoAgua();
                    nrTotalConsumo += g.getNrMetroCubicoAgua();
                }
                else if(idRecurso == 2) // luz?
                {
                    vlrTotalDinheiro += g.getVlrGastLuz();
                    nrTotalConsumo += g.getNrWatts();
                }
            }
            //
            for (i = 0; i < qtdRegistros; i++) {
                GastoHoje g = (GastoHoje) listEntDom.get(i);
                linha = "";
                sDataMedicao ="";
                //sDataMedicao = extrairDiaDoMes(g.getDtUltimaRegistroDia());
                if(idRecurso == 1) // agua?
                {
                    perConsumo = (g.getNrMetroCubicoAgua() * 100)/nrTotalConsumo;
                    linha = "Consumo do dia "+ extrairDia(g.getDtUltimaRegistroDia()) +"\n"+
                            "M³/h: " + g.getNrMetroCubicoAgua() + "\n" +
                            "R$     : " + g.getVlrGastoAgua() + "\n" +
                            "%       : " + formatarNumero.format(perConsumo);
                }
                else if(idRecurso == 2) // luz?
                {
                    perConsumo = (g.getNrWatts() * 100)/nrTotalConsumo;
                    linha = "Consumo do dia " + extrairDia(g.getDtUltimaRegistroDia()) + "\n"+
                            "Kw/h: " + g.getNrWatts() + "\n" +
                            "R$     : " + g.getVlrGastLuz() + "\n" +
                            "%       : " + formatarNumero.format(perConsumo);
                }
                sDataMedicao = converterDataParaString(String.valueOf(g.getDtUltimaRegistroDia()));
                vetSGasto[i] = linha + "\nData: " +sDataMedicao;
            }

            linha = "TOTAL\n"+
                       "Qtde de dias analizados: " + qtdRegistros  + "\n";
            if(idRecurso== 1)
                linha+="M³: ";
            else if(idRecurso ==2)
                linha+="Kw: ";
            linha += formatarNumero.format(nrTotalConsumo) + "\n" +
                    "R$:  " +formatarNumero.format(vlrTotalDinheiro);
            vetSGasto[qtdRegistros] = linha;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, vetSGasto);

            lvRelatorio.setAdapter(adapter);
            return true;
        }
        else
            return false;
    }

    private String descobrirPrimeiroDiaData(String sData)
    {
        sData = sAno+"-"+sMes+"-01";
        return sData;
    }
    private String descobrirUltimoDiaData(String sData)
    {
        int d;
        Date data = formatarData(sData);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        d = calendar.getMaximum(GregorianCalendar.DAY_OF_MONTH);
        sDia = String.valueOf(d);
        sData = sAno + "-" + sMes + "-" + sDia;
        return sData;
    }
    private String extrairDia(Date dt)
    {
        String sD = "";
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dt);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        if (dia > 0) {
            if (dia < 10) {
                sD = "0";
            }
        }
        sD += dia;
        return  sD;
    }
    private String extrairHora(Date dt)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dt);
        int hora = calendar.get(GregorianCalendar.HOUR);
        // amPm
        // 0 = dia
        // 1 = noite
        int amPm = calendar.get(GregorianCalendar.AM_PM);

        if(amPm == 1)
        {
            if(hora>=0 && hora < 13)
            {
                if(hora == 12)
                    hora = 0;
                else
                    hora += 12;
            }
        }
        String sHora = " ";
        if(hora<=9)
        {
            sHora += "0";
        }
        sHora +=hora;
        return sHora;
    }
    private Date formatarData(String data)
    {
        SimpleDateFormat df;
        Date dc;
        try
        {
            df = new SimpleDateFormat("dd/MM/yyyy");
            dc = df.parse(data);
        }
        catch (Exception e) {
            dc = null;
        }
        return dc;
    }

    private String converterDataParaString(String sDate) {

        String dia,
                mes,
                ano,
                hora;
        dia = sDate.substring(8, 10);
        mes = sDate.substring(4, 7);
        ano = sDate.substring(24, 28);
        hora = sDate.substring(10, 19);
        try {
            mes = new SimpleDateFormat("MM").format(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(mes));
        } catch (Exception e2) {
            mes = null;
        }
        sDate = dia +"-"+mes+"-"+ ano + " ás " + hora;

        return sDate;
    }

    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a página anterior
        Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e dpois a próxima tela( a que vai ser chamada)
        intent.setClass(TelaRelatorio1.this, TelaMenu.class);
        intent.putExtra("absClasse", absFactoryRecurso);
        startActivity(intent); // chama a pr�xima tela
        finish();
    }
}
