package com.example.appmedirconsumorecursos.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.appmedirconsumorecursos.Dominio.AbsRecurso;
import com.example.appmedirconsumorecursos.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class TelaGraficoAnual extends Activity {

    private Intent dados;
    private AbsRecurso absRecurso;

    private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_grafico_anual);

        dados = getIntent(); // Recebe os dados da tela anterior
        absRecurso = (AbsRecurso)dados.getSerializableExtra("absClasse"); // Recebe a classe correspondente

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

        mRenderer.setShowGrid(true); // we show the grid
    }

    private void addSampleData() {

        mCurrentSeries.add(1, 2);

        mCurrentSeries.add(2, 3);

        mCurrentSeries.add(3, 2);

        mCurrentSeries.add(4, 5);

        mCurrentSeries.add(5, 4);

    }
    public void onBackPressed() // precionou o voltar do telefone?
    { // Sim, volta para a p?gina anterior
        Intent intent = new Intent();
        // Para chamar a pr?xima tela tem que dizer qual e a tela atual, e dpois a pr?xima tela( a que vai ser chamada)
        intent.setClass(TelaGraficoAnual.this, TelaMenu.class);
        intent.putExtra("absClasse", absRecurso);
        startActivity(intent); // chama a pr?xima tela
        finish();
    }
}
