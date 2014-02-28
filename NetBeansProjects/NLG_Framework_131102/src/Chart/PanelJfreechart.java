/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Chart;

import Data_pkg.HealthParameters.Health_Parameter;
import Data_pkg.Time_Series;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author hadi
 */
public class PanelJfreechart {

    public JPanel fillpanel(ArrayList<Health_Parameter> hplist) {

        
//        
//        
//        
//        DefaultPieDataset pieDataset = new DefaultPieDataset();
//        pieDataset.setValue("One", new Integer(10));
//        pieDataset.setValue("Two", new Integer(20));
//        pieDataset.setValue("Three", new Integer(30));
//        pieDataset.setValue("Four", new Integer(10));
//        pieDataset.setValue("Five", new Integer(20));
//        pieDataset.setValue("Six", new Integer(10));
//        JFreeChart chart = ChartFactory.createPieChart3D("3D Pie Chart", pieDataset, true, true, true);
        
        
        final DefaultXYDataset dataset = new DefaultXYDataset();

        for (int i = 0; i < hplist.size(); i++) {
            double[][] data = createSeries2(hplist.get(i).ts);
            dataset.addSeries(hplist.get(i).name, data);
        }

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(640, 480));

        return new ChartPanel(chart);

    }
    
    
     private double[][] createSeries2(Time_Series ts) {
        double[][] series = new double[2][ts.values.size()];
        for (int i = 0; i < ts.values.size(); i++) {
            series[0][i] = (double) i;
            series[1][i] = ts.values.get(i);

        }
        return series;
    }

    /**
     * Create a chart.
     *
     * @param dataset the dataset
     * @return the chart
     */
    private JFreeChart createChart(XYDataset dataset) {

        //       WHAT IS THIS LINE???
        // JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null, dataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled)


        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Time Series Data", // chart title
                "Time", // domain axis label
                "Range", // range axis label
                dataset, // initial series
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
                );

        // set chart background
        chart.setBackgroundPaint(Color.white);

        // set a few custom plot features
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);



        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);

        // render shapes and lines
//        XYLineAndShapeRenderer renderer =
//                new XYLineAndShapeRenderer(true, true);
//        plot.setRenderer(renderer);
//        renderer.setBaseShapesVisible(true);
//        renderer.setBaseShapesFilled(true);
//
//        // set the renderer's stroke
//        Stroke stroke = new BasicStroke(
//                3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
//        
//        renderer.setBaseOutlineStroke(stroke);
//       
        //Shape theshape = ShapeUtilities.createDiamond(1);
        //renderer.setSeriesShape(5, theshape);


        // label the points
//        NumberFormat format = NumberFormat.getNumberInstance();
//        format.setMaximumFractionDigits(2);
//        XYItemLabelGenerator generator =
//                new StandardXYItemLabelGenerator(
//                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
//                format, format);
//        renderer.setBaseItemLabelGenerator(generator);
//        renderer.setBaseItemLabelsVisible(true);

        return chart;
    }

}
