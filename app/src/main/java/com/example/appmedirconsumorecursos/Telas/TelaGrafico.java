package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TelaGrafico extends Activity {

    private Intent dados;
    private AbsRecurso absRecurso;
    private Integer idRecurso;
    private String  sDia,
                    sMes,
                    sAno;

    private double[] vetNrGastoAgua;
    private double[] vetNrGastoLuz;
    private double[] vetNrVlrGastoAgua;
    private double[] vetNrVlrGastoLuz;

    // Variáveis do gráfico real
    // Creating an  XYSeries for Income
    private XYSeries aguaSeries;
    // Creating an  XYSeries for Income
    private XYSeries luzSeries;
    private GraphicalView graphicalView;
    private XYMultipleSeriesDataset msDataset;

    // Variáveis do gráfico de teste
    private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;
    //
    private List<EntidadeDominio> listEntDom;
    private Session session;
    private GastoHoje gastoHoje;
    //
    private int tipoGrafico;
    private int dia;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_grafico_anual);

        dados = getIntent(); // Recebe os dados da tela anterior
        absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
        idRecurso = Integer.parseInt(absRecurso.getIdRecurso());
        tipoGrafico = (dados.getIntExtra("tipoGrafico",0));
        sDia = dados.getStringExtra("dia");
        sMes = dados.getStringExtra("mes");
        sAno = dados.getStringExtra("ano");
        session = Session.getInstance();
        session.setContext(this);

        dia = Integer.parseInt(sDia);
        if(dia > 0) // Vai pesquisar o gasto das horas do dia?
        {
            data = sDia + "/" + sMes + "/" + sAno;
            //data += " 00:00:00";
            //pesquisarDia(data);
        }
        else // Vai pesquisar o gasto dos dias do mês
        {
            data = "01/" + sMes + "/" + sAno;
            data += " 00:00:00";
            pesquisarGastoNoMes(data);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_grafico_anual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void pesquisarGastoNoMes(String data)
    {
        gastoHoje = new GastoHoje();
        gastoHoje.getMapInstance();
        gastoHoje.setMapCdResidencia(session.getResidencia().getId());
        gastoHoje.setMapDtInicialBusca(descobrirPrimeiroDiaData(data));
        gastoHoje.setMapDtFinalBusca(descobrirUltimoDiaData(data));
        // Mudar a consulta par a
        listEntDom = gastoHoje.operar(session.getContext(), true, Controler.DF_CONSULTAR);
        if (listEntDom != null) {
            //gastoHoje = (GastoHoje) listEntDom.get(0);
            if(idRecurso == 1) // agua?
            {
                aguaSeries = new XYSeries("Agua");
                if(tipoGrafico == 1)
                    vetNrGastoAgua = new double[listEntDom.size()];
                else if(tipoGrafico == 2)
                    vetNrVlrGastoAgua = new double[listEntDom.size()];
            }
            else if(idRecurso == 2)
            {
                luzSeries = new XYSeries("Luz");
                if(tipoGrafico == 1)
                    vetNrGastoLuz = new double[listEntDom.size()];
                else if(tipoGrafico == 2)
                    vetNrVlrGastoLuz = new double[listEntDom.size()];
            }
            int diaParaGrafico;
            for (int i = 0; i < listEntDom.size(); i++) {
                GastoHoje g = (GastoHoje) listEntDom.get(i);
                diaParaGrafico = extrairDiaDoMes(g.getDtUltimaRegistroDia());
                if(idRecurso == 1) // agua?
                {
                    if(tipoGrafico == 1) // consumo?
                    {
                        vetNrGastoAgua[i] = g.getNrMetroCubicoAgua();
                        aguaSeries.add(diaParaGrafico,vetNrGastoAgua[i]);
                    }
                    else if(tipoGrafico == 2) // valor gasto
                    {
                        vetNrVlrGastoAgua[i] = g.getVlrGastoAgua();
                        aguaSeries.add(diaParaGrafico,vetNrVlrGastoAgua[i]);
                    }
                }
                else if(idRecurso == 2) // luz?
                {
                    if(tipoGrafico == 1) // Consumo ?
                    {
                        vetNrGastoLuz[i] = g.getNrWatts();
                        luzSeries.add(diaParaGrafico, vetNrGastoLuz[i]);
                    }
                    else if(tipoGrafico == 2) // valor gasto?
                    {
                        vetNrVlrGastoLuz[i] = g.getVlrGastLuz();
                        luzSeries.add(diaParaGrafico,vetNrVlrGastoLuz[i]);
                    }
                }
            }

            if(idRecurso == 1) // agua?
            {
                //msDataset.addSeries(aguaSeries);
                criarGrafico(aguaSeries);
            }
            else if(idRecurso == 2) {
                //  msDataset.addSeries(luzSeries);
                criarGrafico(luzSeries);
            }

        }

    }

    private void criarGrafico(XYSeries series)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_chart);

        if (graphicalView == null) {

            iniciarGrafico(series);

            graphicalView = ChartFactory.getCubeLineChartView(this, msDataset, mRenderer, 0.3f);

            layout.addView(graphicalView);

        }
        else
        {

            graphicalView.repaint();

        }
    }

    private void iniciarGrafico(XYSeries series)
    {
        msDataset = new XYMultipleSeriesDataset();
        msDataset.addSeries(series);

        mCurrentRenderer = new XYSeriesRenderer();

        mCurrentRenderer.setColor(Color.RED); // cor da linha do grafico
        // Pontos no grafico
        mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer.setPointStrokeWidth(3);
        mCurrentRenderer.setDisplayChartValues(true);
        //

        mRenderer.addSeriesRenderer(mCurrentRenderer);
        // We want to avoid black border

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins

        // Disable Pan on two axis

        mRenderer.setPanEnabled(false, false);

        // ****************TORNAR ISSO DINÂMICO DE ACORDO COM O MAIOR E O MENOR DADO QUE VEM DO BANCO
        mRenderer.setYAxisMax(10);

        mRenderer.setYAxisMin(0);

        String xTituto = "",
                yTitulo = "Gasto";

        if(dia > 0) // Vai pesquisar o gasto das horas do dia?
            xTituto = "Horas";
        else
            xTituto = "Dias";
        if(tipoGrafico == 1) // consumo?
        {
            if (idRecurso == 1)// Agua
                yTitulo += "(m³)";
            else
                yTitulo += "(kw/h)";
        }
        else if(tipoGrafico == 2)
        {
            yTitulo += "(R$)";
        }



        mRenderer.setXTitle(xTituto);
        mRenderer.setYTitle(yTitulo);
        mRenderer.setShowGrid(true); // we show the grid
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

    private int extrairDiaDoMes(Date data)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        return dia;
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
    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a página anterior
        Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e depois a próxima tela( a que vai ser chamada)
        intent.setClass(TelaGrafico.this, TelaDeHistorico.class);
        intent.putExtra("absClasse", absRecurso);
        startActivity(intent); // chama a próxima tela
        finish();
    }
}
