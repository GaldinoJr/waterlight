package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appmedirconsumorecursos.Controle.Controler.Controler;
import com.example.appmedirconsumorecursos.Core.impl.Controle.Session;
import com.example.appmedirconsumorecursos.Dominio.AbsFactoryRecurso;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoHora;
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
import java.util.Locale;

public class TelaGrafico extends Activity {

    private Intent dados;
    //
    private int nrMordador;
    private int nrComodo;
    private int indTipoComparacaoMaiorConsumo;
    private int fgCompararOutrasResidencias;
    private int vincularAguaLuz;
    //
    private AbsFactoryRecurso absFactoryRecurso;
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
    private GastoHora gastoHora;
    //
    private int tipoGrafico;
    private int dia;
    private String data;
    private boolean fgBuscaNoDia;
    // GRAFICO DE BARRA
    private GraphicalView mChartView;
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
        absFactoryRecurso = (AbsFactoryRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente
        idRecurso = Integer.parseInt(absFactoryRecurso.getIdRecurso());
        tipoGrafico = (dados.getIntExtra("tipoGrafico",0));
        sDia = dados.getStringExtra("dia");
        sMes = dados.getStringExtra("mes");
        sAno = dados.getStringExtra("ano");
        // filtros do gráfico
        indTipoComparacaoMaiorConsumo = dados.getIntExtra("indTipoComparacaoMaiorConsumo",0);
        if(indTipoComparacaoMaiorConsumo > 0) // É o gráfico por maior consumo?
        {
            int fgTelaGrafico = dados.getIntExtra("fgTelaHistorico", 0);
            if(fgTelaGrafico == 1) // Já gerou o gráfico?
            { // sim, então volta para a tela de filtros
                onBackPressed();
            }
        }
        fgCompararOutrasResidencias = dados.getIntExtra("fgCompararOutrasResidencias",0);
        nrMordador = dados.getIntExtra("nrMorador", 0);
        nrComodo = dados.getIntExtra("nrComodo",0);
        vincularAguaLuz = dados.getIntExtra("vincularAguaLuz",0);

        session = Session.getInstance();
        session.setContext(this);
        fgBuscaNoDia = false;
        dia = Integer.parseInt(sDia);
        if(dia > 0) // Vai pesquisar o gasto das horas do dia?
        {
            fgBuscaNoDia = true;
            data = sAno + "-" + sMes + "-" + sDia;
            if(indTipoComparacaoMaiorConsumo == 0)
            {
                //data += " 00:00:00";
                pesquisarGastoNoDia(data);
            }
            else
            {
                gastoHora = new GastoHora();
                gastoHora.getMapInstance();
                gastoHora.setMapCdResidencia(session.getResidencia().getId());
                gastoHora.setMapsDtInclusao(data);
                gastoHora.setMapFitro_nrMorador(String.valueOf(nrMordador));
                gastoHora.setMapFitro_nrComodo(String.valueOf(nrComodo));
                gastoHora.setMapFitro_fgCompararOutrasResidencias(String.valueOf(fgCompararOutrasResidencias));
                gastoHora.setMapFitro_indTipoComparacaoMaiorConsumo(String.valueOf(indTipoComparacaoMaiorConsumo));
                gastoHora.setMapFiltro_idRecurso(String.valueOf(idRecurso));
                listEntDom = gastoHora.operar(this,false,Controler.DF_CONSULTAR);
                if(listEntDom != null)
                    graficoMaiorConsumo(listEntDom);
                else
                {
                    Toast.makeText(TelaGrafico.this, "Não a registros nessa Data.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }
        else // Vai pesquisar o gasto dos dias do mês
        {
            data = "01/" + sMes + "/" + sAno;
            data += " 00:00:00";
            if(indTipoComparacaoMaiorConsumo == 0)
                pesquisarGastoNoMes(data);
            else
            {
                gastoHoje = new GastoHoje();
                gastoHoje.getMapInstance();
                gastoHoje.setMapCdResidencia(session.getResidencia().getId());
                gastoHoje.setMapDtInicialBusca(descobrirPrimeiroDiaData(data));
                gastoHoje.setMapDtFinalBusca(descobrirUltimoDiaData(data));
                gastoHoje.setMapFitro_nrMorador(String.valueOf(nrMordador));
                gastoHoje.setMapFitro_nrComodo(String.valueOf(nrComodo));
                gastoHoje.setMapFitro_fgCompararOutrasResidencias(String.valueOf(fgCompararOutrasResidencias));
                gastoHoje.setMapFitro_indTipoComparacaoMaiorConsumo(String.valueOf(indTipoComparacaoMaiorConsumo));
                gastoHoje.setMapFiltro_idRecurso(String.valueOf(idRecurso));
                listEntDom = gastoHoje.operar(this,false,Controler.DF_CONSULTAR);
                //openChart();
                if(listEntDom != null)
                    graficoMaiorConsumo(listEntDom);
                else
                {
                    Toast.makeText(TelaGrafico.this, "Não a registros nessa Data.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

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

    private void pesquisarGastoNoDia(String data)
    {
        gastoHora = new GastoHora();
        gastoHora.getMapInstance();
        gastoHora.setMapCdResidencia(session.getResidencia().getId());
        gastoHora.setMapsDtInclusao(data);
        gastoHora.setMapFiltro_fgTodosRegistros("1");
        // Mudar a consulta par a
        listEntDom = gastoHora.operar(session.getContext(), true, Controler.DF_CONSULTAR);
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
                GastoHora g = (GastoHora) listEntDom.get(i);
                diaParaGrafico = extrairDiaDoMes(g.getDtInclusao());
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

        if (graphicalView == null)
        {
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
        mCurrentRenderer.setChartValuesTextSize(20);

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
        mCurrentRenderer.setChartValuesTextSize(20);
        //
//        mCurrentRenderer.setFillBelowLine(true);
//        mCurrentRenderer.setFillBelowLineColor(Color.parseColor("#bb20bff2"));

        mRenderer.addSeriesRenderer(mCurrentRenderer);
        mRenderer.setLabelsTextSize(16);
        mRenderer.setLegendTextSize(15);

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

//        int[] x = { 0,1 };
//        int[] income = { 2000,0};
//        int[] expense = {2200,0};

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
    private void graficoMaiorConsumo(List<EntidadeDominio> list)
    {
//        int[] x = { 0,1 };
//        int[] income = { 2000,0};
//        int[] expense = {2200,0};
        int[] x = { 0,1,2,3,4,5,6,7 };
        int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        int i;
        int qtdRegistros = list.size();
        int[] iexoX;
        double[] valoresAgua;
        double[] valoresLuz;
        String[] nmResidencias;
        String diaDeMaiorConsumo = "";
        if(fgCompararOutrasResidencias == 0 || qtdRegistros == 1)
        {
            iexoX = new int[qtdRegistros+1];
            valoresAgua = new double[qtdRegistros+1];
            valoresLuz = new double[qtdRegistros+1];
            nmResidencias = new String[qtdRegistros+1];
        }
        else
        {
            iexoX = new int[qtdRegistros];
            valoresAgua = new double[qtdRegistros];
            valoresLuz = new double[qtdRegistros];
            nmResidencias = new String[qtdRegistros];
        }
        nmResidencias[0] = session.getResidencia().getNome();
        String[] alfabeto = new String[] {
                "A", "B" , "C", "D", "E", "F",
                "G", "H" , "I", "J", "K", "L",
                "M", "N" , "O", "O", "Q", "R",
                "S", "T" , "U", "V", "W", "X",
                "Y", "Z"
        };
        for(i = 0; i < qtdRegistros;i++)
        {
            iexoX[i] = i;
            if(i > 0)
                nmResidencias[i] = alfabeto[i-1];
            if(fgBuscaNoDia) {
                GastoHora g = (GastoHora) listEntDom.get(i);
                if( i ==0)
                    diaDeMaiorConsumo = converterDataParaString(String.valueOf(g.getDtInclusao()));
                if(tipoGrafico == 1) // consumo?
                {
                    valoresAgua[i] = g.getNrMetroCubicoAgua();
                    valoresLuz[i] = g.getNrWatts();
                }
                else if(tipoGrafico == 2) // valor gasto
                {
                    valoresAgua[i] = g.getVlrGastoAgua();
                    valoresLuz[i] = g.getVlrGastLuz();
                }
            }
            else
            {
                GastoHoje g =(GastoHoje) listEntDom.get(i);
                if( i ==0)
                    diaDeMaiorConsumo = converterDataParaString(String.valueOf(g.getDtUltimaRegistroDia()));
                if(tipoGrafico == 1) // consumo?
                {
                    valoresAgua[i] = g.getNrMetroCubicoAgua();
                    valoresLuz[i] = g.getNrWatts();
                }
                else if(tipoGrafico == 2) // valor gasto
                {
                    valoresAgua[i] = g.getVlrGastoAgua();
                    valoresLuz[i] = g.getVlrGastLuz();
                }
            }
        }
        if(fgCompararOutrasResidencias == 0 || qtdRegistros == 1)// não vai comparar com outras residencias?
        {
            iexoX[1] = 1;
            valoresAgua[1] = 0;
            valoresLuz[1] = 0;
            nmResidencias[1] = " ";
            qtdRegistros++;
        }
        // Creating an  XYSeries for Income

        aguaSeries = new XYSeries("Agua");
        luzSeries = new XYSeries("Luz");


        // Adding data to Income and Expense Series
        for(i=0;i< qtdRegistros;i++){
            aguaSeries.add(i,valoresAgua[i]);
            luzSeries.add(i,valoresLuz[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        if(vincularAguaLuz == 1) // vai vincular agua e luz?
        {
            dataset.addSeries(aguaSeries);
            // Adding Expense Series to dataset
            dataset.addSeries(luzSeries);
        }
        else
        {
            if(idRecurso == 1) // agua?
                dataset.addSeries(aguaSeries);
            else if(idRecurso == 2)
                dataset.addSeries(luzSeries);
        }

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer aguaRenderer = new XYSeriesRenderer();
       // aguaRenderer.setColor(Color.BLUE);
        aguaRenderer.setColor(Color.rgb(130, 130, 230)); // azul
        aguaRenderer.setFillPoints(true);
        aguaRenderer.setLineWidth(2);
        aguaRenderer.setDisplayChartValues(true);
        aguaRenderer.setChartValuesTextSize(20);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer luzRenderer = new XYSeriesRenderer();
        //luzRenderer.setColor(Color.YELLOW);
        //luzRenderer.setColor(Color.rgb(220, 80, 80));
        luzRenderer.setColor(Color.rgb(217, 217, 25));
        luzRenderer.setFillPoints(true);
        luzRenderer.setLineWidth(2);
        luzRenderer.setDisplayChartValues(true);
        luzRenderer.setChartValuesTextSize(20);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setAxisTitleTextSize(16);
        multiRenderer.setLegendTextSize(15);
        multiRenderer.setLabelsTextSize(16);

        String nmTempo;
        String titulo = "";

        nmTempo = "Tempo(Dia: "+diaDeMaiorConsumo + ")";

        if(vincularAguaLuz == 1) // vai vincular agua e luz?
            titulo = "Agua x Luz x " + nmTempo;
        else
        {
            if(idRecurso == 1) // agua
                titulo = "Agua x " + nmTempo;
            else if(idRecurso == 2) // luz
                titulo =  "Luz x " + nmTempo;
        }
        if(fgCompararOutrasResidencias == 1)
            titulo += " x Residencias";
        multiRenderer.setChartTitle(titulo);
        String yTitulo = "Gasto: ";

        if(tipoGrafico == 1) // consumo?
        {
            if(vincularAguaLuz == 1) {
                yTitulo += "(m³) e (kw/h)";
            }
            else {
                if (idRecurso == 1)// Agua
                    yTitulo += "(m³)";
                else
                    yTitulo += "(kw/h)";
            }
        }
        else if(tipoGrafico == 2)
        {
            yTitulo += "(R$)";
        }
        multiRenderer.setYTitle(yTitulo);
        multiRenderer.setXTitle("Residencias");
        multiRenderer.setZoomButtonsVisible(true);
        for(i = 0; i< qtdRegistros;i++){
            multiRenderer.addXTextLabel(i, nmResidencias[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        if(vincularAguaLuz == 1) // vai vincular agua e luz?
        {
            multiRenderer.addSeriesRenderer(aguaRenderer);
            multiRenderer.addSeriesRenderer(luzRenderer);
        }
        else
        {
            if(idRecurso == 1) // agua?
                multiRenderer.addSeriesRenderer(aguaRenderer);
            else if(idRecurso == 2)
                multiRenderer.addSeriesRenderer(luzRenderer);
        }


        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, BarChart.Type.DEFAULT);

        // Start Activity
        startActivity(intent);

    }
    // datas
    private String converterDataParaString(String sDate) {

        String dia,
                mes,
                ano,
                hora;
        dia = sDate.substring(8, 10);
        mes = sDate.substring(4, 7);
        ano = sDate.substring(24, 28);
        hora = sDate.substring(11, 13);
        try {
            mes = new SimpleDateFormat("MM").format(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(mes));
        } catch (Exception e2) {
            mes = null;
        }
        sDate = dia +"-"+mes+"-"+ ano;
        if(fgBuscaNoDia)
            sDate += " de "+ hora + ":00 até " + hora + ":59";
        else
            sDate += " de 00:00 até 00:59";

        return sDate;
    }
    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a página anterior
        Intent intent = new Intent();
        // Para chamar a próxima tela tem que dizer qual e a tela atual, e depois a próxima tela( a que vai ser chamada)
        intent.setClass(TelaGrafico.this, TelaDeHistorico.class);
        intent.putExtra("absClasse", absFactoryRecurso);
        startActivity(intent); // chama a próxima tela
        finish();
    }
}
