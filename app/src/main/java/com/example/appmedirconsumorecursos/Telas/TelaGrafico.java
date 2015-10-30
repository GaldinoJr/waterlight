package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TelaGrafico extends Activity {

    private Intent dados;
    //
    private int indIgualNrMordador;
    private int indIgualNrComodo;
    private int indTipoComparacaoMaiorConsumo;
    private int fgCompararOutrasResidencias;
    //
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
    // GRAFICO DE BARRA
    private GraphicalView mChartView;
    //
    private int maiorConsumo;
    //
    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    //http://portalandroid.org/comunidade/viewtopic.php?f=2&t=16346
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_grafico_anual);

        dados = getIntent(); // Recebe os dados da tela anterior
        absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
        idRecurso = Integer.parseInt(absRecurso.getIdRecurso());
        tipoGrafico = (dados.getIntExtra("tipoGrafico",0));
        sDia = dados.getStringExtra("dia");
        sMes = dados.getStringExtra("mes");
        sAno = dados.getStringExtra("ano");
        // filtros do gráfico
        maiorConsumo = dados.getIntExtra("maiorConsumo", 0);
        indIgualNrMordador = dados.getIntExtra("indTipoComparacaoMaiorConsumo",0);
        indIgualNrComodo  = dados.getIntExtra("fgCompararOutrasResidencias",0);
        indTipoComparacaoMaiorConsumo  = dados.getIntExtra("nrMorador", 0);
        fgCompararOutrasResidencias  = dados.getIntExtra("nrComodo",0);

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
            if(maiorConsumo == 0)
                pesquisarGastoNoMes(data);
            else
                openChart();
               // createChart();
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

        mCurrentRenderer.setFillBelowLine(true);


        //mCurrentRenderer.setColor(Color.RED); // cor da linha do grafico
        if(idRecurso == 1) // agua?
        {
            mCurrentRenderer.setColor(Color.BLACK); // cor da linha do grafico
            mCurrentRenderer.setFillBelowLineColor(Color.BLUE);
        }
        else if(idRecurso == 2) //luz?
        {
            mCurrentRenderer.setColor(Color.BLACK); // cor da linha do grafico
            mCurrentRenderer.setFillBelowLineColor(Color.YELLOW);
        }
        // Pontos no grafico
        mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer.setPointStrokeWidth(3);
        mCurrentRenderer.setDisplayChartValues(true);
        //
//        mCurrentRenderer.setFillBelowLine(true);
//        mCurrentRenderer.setFillBelowLineColor(Color.parseColor("#bb20bff2"));

        mRenderer.addSeriesRenderer(mCurrentRenderer);
        // We want to avoid black border

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins


        // Disable Pan on two axis

        mRenderer.setPanEnabled(false, false);

        // ****************TORNAR ISSO DINÂMICO DE ACORDO COM O MAIOR E O MENOR DADO QUE VEM DO BANCO
        mRenderer.setYAxisMax(10);

        mRenderer.setYAxisMin(0);
        //
        mRenderer.setAxesColor(Color.BLACK);// Cor do eixo

        mRenderer.setLabelsColor(Color.BLACK); // legendas
        // Borda do gráfico
        mRenderer.setYAxisColor(Color.BLACK);
        mRenderer.setXAxisColor(Color.BLACK);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setXLabels(2); // escala do gráfico
        mRenderer.setYLabelsColor(0,Color.BLACK);

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
    // --------------------------GRAFICO DE BARRA
    public void createChart(){
        int[] valores = {6, 7, 5, 3};

        CategorySeries series = new CategorySeries("Grafico de barras");
        for (int i = 0; i < valores.length; i++) {
            series.add(valores[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
        setChartSettings(renderer);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_chart);
        mChartView = ChartFactory.getBarChartView(this, getBarDemoDataset(),
                renderer, BarChart.Type.DEFAULT);
        layout.addView(mChartView);

    }
    private XYMultipleSeriesDataset getBarDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int[] valores = {6, 7, 5, 3};
        CategorySeries series = new CategorySeries("Demo series");
        for (int i = 0; i < valores.length; i++) {
            series.add(valores[i]);
        }
        dataset.addSeries(series.toXYSeries());
        return dataset;
    }

    public XYMultipleSeriesRenderer getBarDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setBarSpacing(1);
        renderer.setLegendTextSize(15);
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.YELLOW);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setShowLegend(false);
        renderer.setAxesColor(Color.DKGRAY);
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(12.5);
        renderer.setYAxisMin(0);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setXLabels(0);
        renderer.setZoomEnabled(false, false);
        renderer.setShowCustomTextGrid(true);
        renderer.setShowGridY(true);
        renderer.setShowGridX(true);
        renderer.addXTextLabel(1, "Jan");
        renderer.addXTextLabel(2, "Fev");
        renderer.addXTextLabel(3, "Mar");
        renderer.addXTextLabel(4, "Abr");

    }
    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6,7 };
        int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        // Creating an  XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Income");
        // Creating an  XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Expense");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            incomeSeries.add(i,income[i]);
            expenseSeries.add(i,expense[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.rgb(130, 130, 230));
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.rgb(220, 80, 80));
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Income vs Expense Chart");
        multiRenderer.setXTitle("Year 2012");
        multiRenderer.setYTitle("Amount in Dollars");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, BarChart.Type.DEFAULT);

        // Start Activity
        startActivity(intent);

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
