package Chart;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hadi
 */
/**
 * JFreeChartDemo
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * More than 150 demo applications are included with the JFreeChart Developer
 * Guide. For more information, see:
 *
 * JFreeChart Developer's Guide
 */
import Data_pkg.HealthParameters.Health_Parameter;
import Data_pkg.Time_Series;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * @author John B. Matthews
 */
public class JFreeChartDemo extends JFrame {

    private static final int MAX = 8;
    private static final Random random = new Random();

    /**
     * Construct a new frame
     *
     * @param title the frame title
     */
    public JFreeChartDemo(ArrayList<Time_Series> tslist, String title, int xd, int yd) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final DefaultXYDataset dataset = new DefaultXYDataset();

        for (int i = 0; i < tslist.size(); i++) {
            double[][] data = createSeries2(tslist.get(i));
            dataset.addSeries(tslist.get(i).name, data);
        }

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(xd, yd)); //640, 480));
        this.add(chartPanel, BorderLayout.CENTER);

    }

    public JFreeChartDemo(String title, ArrayList<Health_Parameter> hplist) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final DefaultXYDataset dataset = new DefaultXYDataset();

        for (int i = 0; i < 2/*hplist.size()*/; i++) {
            double[][] data = createSeries2(hplist.get(i).ts);
            dataset.addSeries(hplist.get(i).abbreviation , data);
        }

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        this.add(chartPanel, BorderLayout.CENTER);

        
//        JPanel buttonPanel = new JPanel();
//        JButton addButton = new JButton("Add Series");
//        buttonPanel.add(addButton);
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int n = dataset.getSeriesCount();
//                dataset.addSeries("Series" + n, createSeries(n));
//            }
//        });
//        JButton remButton = new JButton("Remove Series");
//        buttonPanel.add(remButton);
//        remButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int n = dataset.getSeriesCount() - 1;
//                dataset.removeSeries("Series" + n);
//            }
//        });
//        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Create a series
     *
     * @ return the series
     */
    private double[][] createSeries(int mean) {
        double[][] series = new double[2][MAX];
        for (int i = 0; i < MAX; i++) {
            series[0][i] = (double) i;
            series[1][i] = mean + random.nextGaussian() / 2;

        }
        return series;
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
                "", // chart title
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