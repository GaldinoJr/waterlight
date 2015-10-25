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
    private int dia;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_grafico_anual);

        dados = getIntent(); // Recebe os dados da tela anterior
        absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
        idRecurso = Integer.parseInt(absRecurso.getIdRecurso());
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


//        if( idRecurso == 1)
//            drawChart2();
//        else
//            drawChart();
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
    private void iniciarGrafico(XYSeries series)
    {
        msDataset = new XYMultipleSeriesDataset();
        msDataset.addSeries(series);

        mCurrentRenderer = new XYSeriesRenderer();

        mCurrentRenderer.setColor(Color.RED); // cor da linha do grafico
        // Pontos no grafico
        mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer.setPointStrokeWidth(3);
        //

        mRenderer.addSeriesRenderer(mCurrentRenderer);
        // We want to avoid black border

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins

        // Disable Pan on two axis

        mRenderer.setPanEnabled(false, false);

        // ****************TORNAR ISSO DINÂMICO DE ACORDO COM O MAIOR E O MENOR DADO QUE VEM DO BANCO
        mRenderer.setYAxisMax(10);

        mRenderer.setYAxisMin(0);

        mRenderer.setXTitle("Dias");

        mRenderer.setYTitle("Gasto");

        mRenderer.setShowGrid(true); // we show the grid
    }
    private void initChart() {

        mCurrentSeries = new XYSeries("GASTO ANUAL");

        mDataset.addSeries(mCurrentSeries);

        mCurrentRenderer = new XYSeriesRenderer();

        mCurrentRenderer.setColor(Color.RED); // cor da linha do grafico
        // Pontos no grafico
        mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer.setPointStrokeWidth(3);
        //

        mRenderer.addSeriesRenderer(mCurrentRenderer);
        // We want to avoid black border

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins

        // Disable Pan on two axis

        mRenderer.setPanEnabled(false, false);

        mRenderer.setYAxisMax(35);

        mRenderer.setYAxisMin(0);

        mRenderer.setXTitle("Dias");

        mRenderer.setYTitle("Gasto");

        mRenderer.setShowGrid(true); // we show the grid
    }

    private void addSampleData() {

        mCurrentSeries.add(1, 2);

        mCurrentSeries.add(2, 3);

        mCurrentSeries.add(3, 2);

        mCurrentSeries.add(4, 5);

        mCurrentSeries.add(5, 4);

    }

    private void drawChart(){
        int[] x = { 1,2,3,4,5,6,7,8 };
        int[] happyness = { 5,2,4,1,0,4,5,0};
        int[] energy = { 5,4,3,1,4,3,5,0};
        int[] strenth = { 2,0,1,3,2,4,3,0};
        int[] endurance = { 0,2,1,4,3,5,2,0};

        String[] mMonth = {"Janeiro" , "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
                "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        // Creating an  XYSeries for Income
        XYSeries happynessSeries = new XYSeries("Happyness");
        // Creating an  XYSeries for Income
        XYSeries energySeries = new XYSeries("Energy");
        // Adding data to Income and Expense Series
        XYSeries strenthSeries = new XYSeries("Strenth");
        XYSeries enduranceSeries = new XYSeries("Endurance");

        for(int i=0;i<x.length;i++){
            happynessSeries.add(x[i], happyness[i]);
            energySeries.add(x[i],energy[i]);
            strenthSeries.add(x[i],strenth[i]);
            enduranceSeries.add(x[i],endurance[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(happynessSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(energySeries);
        dataset.addSeries(strenthSeries);
        dataset.addSeries(enduranceSeries);


        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer happynessRenderer = new XYSeriesRenderer();
        happynessRenderer.setColor(Color.parseColor("#f4ed21"));//chart border color
        happynessRenderer.setPointStyle(PointStyle.CIRCLE);
        happynessRenderer.setFillPoints(true);
        happynessRenderer.setLineWidth(2);
        happynessRenderer.setDisplayChartValues(true);
        happynessRenderer.setFillBelowLine(true);
        happynessRenderer.setFillBelowLineColor(Color.parseColor("#bbf4ed21"));

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer energyRenderer = new XYSeriesRenderer();
        energyRenderer.setColor(Color.parseColor("#20bff2"));
        energyRenderer.setPointStyle(PointStyle.CIRCLE);
        energyRenderer.setFillPoints(true);
        energyRenderer.setLineWidth(2);
        energyRenderer.setDisplayChartValues(true);
        energyRenderer.setFillBelowLine(true);
        energyRenderer.setFillBelowLineColor(Color.parseColor("#bb20bff2"));

        // strenth
        XYSeriesRenderer strenthRenderer = new XYSeriesRenderer();
        strenthRenderer.setColor(Color.parseColor("#f97b1d"));
        strenthRenderer.setPointStyle(PointStyle.CIRCLE);
        strenthRenderer.setFillPoints(true);
        strenthRenderer.setLineWidth(2);
        strenthRenderer.setDisplayChartValues(true);
        strenthRenderer.setFillBelowLine(true);
        strenthRenderer.setFillBelowLineColor(Color.parseColor("#bbf97b1d"));

        // endurance
        XYSeriesRenderer enduranceRenderer = new XYSeriesRenderer();
        enduranceRenderer.setColor(Color.parseColor("#c68c4d"));
        enduranceRenderer.setPointStyle(PointStyle.CIRCLE);
        enduranceRenderer.setFillPoints(true);
        enduranceRenderer.setLineWidth(2);
        enduranceRenderer.setDisplayChartValues(true);
        enduranceRenderer.setFillBelowLine(true);
        enduranceRenderer.setFillBelowLineColor(Color.parseColor("#bbc68c4d"));


        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }
        multiRenderer.setLabelsTextSize(50);
        multiRenderer.setYLabels(0);
        multiRenderer.setMargins(new int[]{0, 0, 0, 0});

        multiRenderer.addSeriesRenderer(happynessRenderer);
        multiRenderer.addSeriesRenderer(energyRenderer);
        multiRenderer.addSeriesRenderer(strenthRenderer);
        multiRenderer.addSeriesRenderer(enduranceRenderer);

        multiRenderer.setPanEnabled(false, false);
        multiRenderer.setZoomEnabled(false, false);
        multiRenderer.setZoomEnabled(false, false);

        // Creating an intent to plot line chart using dataset and multipleRenderer
        mChart =(GraphicalView) ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);


        LinearLayout llChart = (LinearLayout)findViewById(R.id.layout_chart);
        llChart.addView(mChart);

    }
    private void drawChart2()
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_chart);

        if (mChart == null) {

            initChart();

            addSampleData();

            mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.3f);

            layout.addView(mChart);

        } else {

            mChart.repaint();

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
                vetNrGastoAgua = new double[listEntDom.size()];
            }
            else if(idRecurso == 2)
            {
                luzSeries = new XYSeries("Luz");
                vetNrGastoLuz = new double[listEntDom.size()];
            }
            for (int i = 0; i < listEntDom.size(); i++) {
                GastoHoje g = (GastoHoje) listEntDom.get(i);
                if(idRecurso == 1) // agua?
                {
                    vetNrGastoAgua[i] = g.getNrMetroCubicoAgua();
                    aguaSeries.add((i + 1),vetNrGastoAgua[i]);
                }
                else if(idRecurso == 2) // luz?
                {
                    vetNrGastoLuz[i] = g.getNrWatts();
                    luzSeries.add((i + 1), vetNrGastoLuz[i]);
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
