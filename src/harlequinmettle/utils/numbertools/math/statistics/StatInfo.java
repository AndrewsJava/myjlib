package harlequinmettle.utils.numbertools.math.statistics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;

public class StatInfo extends JFrame implements WindowListener, MouseListener,
		MouseMotionListener {
	// public int ID;
	public int repaintCounter = 0;
	public static final int SIDE_BUFFER = 50;
	public static final int drag_proximity = 20;
	double sum, sumOfSquares, mean, median;
	public double min;
	public double max;
	double n = 1, range;

	int[] histogram;

	double emin, emax, percentile = 2;

	double interval;

	public double standardDeviation;

	float graphicsScale = 1.0f;
	float frameH, frameW;
	int maxBar = Integer.MIN_VALUE;
	int secondToMaxBar = Integer.MIN_VALUE;
	private Color[] changeHighlights;// alpha green/red overlay to indicate
										// relative growth/decline
	private static final Color bg = new Color(180, 180, 250);// new Color(20,
																// 120, 80);
	private static final Color bc = new Color(90, 120, 250);
	private static final Color bcSub = new Color(150, 60, 20, 100);
	private static final Color shade = new Color(20, 20, 20, 60);

	private static final Color greenT = new Color(0, 250, 0);
	private static final Color redT = new Color(250, 0, 0);

	private static final Color lineC = new Color(160, 220, 90);
	private static final Color line2C = new Color(60, 120, 20);
	private static final Color limLine = new Color(90, 60, 60);
	private static final Color gridLineColor = new Color(60, 100, 60);
	private static final BasicStroke thick = new BasicStroke(2f,
			BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final BasicStroke thicker = new BasicStroke(4f,
			BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	private static final int BAR_BUFFER = 2;

	public static final int DONT_SHOW = 10001000;

	static int nbars = 70;
	float barwidth = 10;
	float dataQuality = 0;

	Rectangle2D.Double[] histoBars = new Rectangle2D.Double[nbars];

	ArrayList<Rectangle2D.Double> overBars = new ArrayList<Rectangle2D.Double>();
	ArrayList<Rectangle2D.Double> underBars = new ArrayList<Rectangle2D.Double>();

	Rectangle2D.Float shadedArea1;
	Rectangle2D.Float shadedArea2;

	public boolean varsSet = false;
	public boolean usingMax = true;
	public int nullCt = 0;
	String title = "untitled";
	GeneralPath zeroPercent = new GeneralPath();

	// add as many background graphs as is appropriate - map scalefactor
	// (changeable) to interval mapping
	TreeMap<Float, Float> marketComparison = new TreeMap<Float, Float>();
	GeneralPath viewMarket = new GeneralPath();
	// preserve link to marketchanges
	TreeMap<Float, Float> marketchange;

	ArrayList<GeneralPath> gridLines = new ArrayList<GeneralPath>();
	ArrayList<GeneralPath> greenUpLines = new ArrayList<GeneralPath>();
	ArrayList<GeneralPath> redDownLines = new ArrayList<GeneralPath>();

	public float xLim, nLim;
	public float gmax, gmin, gmaxVert, gminVert;
	public float graphInterval;
	// list of mappings of bundle sizes - for each date id->bundle size
	ArrayList<TreeMap<Float, Integer>> bSizes = new ArrayList<TreeMap<Float, Integer>>();
	ArrayList<TreeMap<Float, Point>> bSizesChanges = new ArrayList<TreeMap<Float, Point>>();
	HashMap<Point2D.Float, Integer> displaySizes = new HashMap<Point2D.Float, Integer>();
	HashMap<Point2D.Float, Point> displaySetChanges = new HashMap<Point2D.Float, Point>();

	ArrayList<TreeMap<Float, Float>> percentChanges = new ArrayList<TreeMap<Float, Float>>();
	ArrayList<GeneralPath> percentChangesView = new ArrayList<GeneralPath>();

	// load with stats for bundles that we are using - qualify based on set
	// category limits
	StatInfo subsetStats;
	public StatInfo beatMarket;
	public StatInfo marketBeat;
	boolean setXlim = false;
	boolean setNlim = false;

	boolean showLines = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TITLE: " + title + "\nsum: " + sum + "\nsum of sq: "
				+ sumOfSquares + "\nmin: " + min + "\nmax: " + max + "\nmean: "
				+ mean + "\nmedian: " + median + "\nstandard deviation: "
				+ standardDeviation + "\nhistogram: "
				+ Arrays.toString(histogram) + "\ninterval: " + interval
				+ "\ngraphicsScale: " + graphicsScale + "\nframeH: " + frameH
				+ "\nframeW: " + frameW + "\nmaxBar: " + maxBar
				+ "\nbarwidth: " + barwidth + "\nhistobars: "
				+ Arrays.toString(histoBars) + "\ndataquality: " + dataQuality
				+ "\nnullCt: " + nullCt

		;
	}

	public StatInfo(ArrayList<Float> data) {
		reVisualize();
		doStatsOnList(data);
		calculateHistogramEffective(data);
		setUpBars();
		varsSet = true;
		setVisible(true);
		this.repaint();
	}

	public void reVisualize() {

		setSize(900, 700);
		this.addWindowListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent evt) {

				frameW = evt.getComponent().getWidth();
				frameH = evt.getComponent().getHeight();
				barwidth = (frameW - 2 * SIDE_BUFFER - BAR_BUFFER * nbars)
						/ nbars;
				if (varsSet)
					setUpBars();
				setMarketPathToScale();
				recalculatePercentagesScale();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

		frameW = this.getWidth();
		frameH = this.getHeight();
		shadedArea1 = new Rectangle2D.Float(0, 0, 0, frameH);
		shadedArea2 = new Rectangle2D.Float(0, 0, 0, frameH);
		barwidth = (frameW - 2 * SIDE_BUFFER - BAR_BUFFER * nbars) / nbars;
		setMarketPathToScale();
		recalculatePercentagesScale();

	}

	public void doStatsOnList(ArrayList<Float> data) {
		ArrayList<Float> medianFinder = new ArrayList<Float>();
		for (float dataPoint : data) {
			// NaN
			if (dataPoint != dataPoint)
				continue;
			// pos or neg infinity
			if (Float.isInfinite(dataPoint))
				continue;

			n++;
			sum += dataPoint;
			sumOfSquares += dataPoint * dataPoint;
			medianFinder.add(dataPoint);
		}
		mean = sum / n;
		if (medianFinder.size() < 1)
			return;
		Collections.sort(medianFinder);
		if(medianFinder.size()==1)
			median = sum;
		else
		median = ((medianFinder.get((int) (n / 2)) + medianFinder
				.get((int) ((n - 1) / 2))) / 2);
		min = medianFinder.get(0);
		max = medianFinder.get(medianFinder.size() - 1);
		// mode = findMode(medianFinder);

		standardDeviation = (float) Math.sqrt((sumOfSquares - sum * sum / n)
				/ n);
		range = max - min;

		dataQuality = (float) n / data.size();
		double lowThird = (mean - percentile * standardDeviation);
		emin = lowThird < min ? min : lowThird;
		double highThird = (mean + percentile * standardDeviation);
		emax = highThird > max ? max : highThird;
		// round our numbers to 4 sig fig
		BigDecimal bd  = new BigDecimal(0);
		if(!Double.isInfinite(emin)){
		  bd = new BigDecimal(emin);
		bd = bd.round(new MathContext(4));
		emin = bd.floatValue();
		}
		if(!Double.isInfinite(emax)){
		bd = new BigDecimal(emax);
		bd = bd.round(new MathContext(4));
		emax = bd.floatValue();
		}
	}

	public int locationInHistogram(double f) {
		return (int) ((f - (emin)) / (interval));

	}

	public void calculateHistogramEffective(ArrayList<Float> _data) {
		// ///////////////////////////////////

		range = emax - emin;
		interval = range / nbars;

		// //////////////////////////////////////
		int[] histogram = new int[nbars];
		for (float dataPt : _data) {
			if (dataPt != dataPt)
				continue;
			int histo_pt = (int) ((dataPt - (emin)) / (interval));
			if (histo_pt >= 0 && histo_pt < nbars)
				histogram[histo_pt]++;
		}

		for (int i : histogram) {
			if (i > maxBar) {

				maxBar = i;
			}
		}
		for (int i : histogram) {
			if (i > secondToMaxBar && i != maxBar) {
				secondToMaxBar = i;
			}
		}
		if (maxBar > 1.3 * secondToMaxBar) {
			usingMax = false;
			// maxBar = secondToMaxBar;
		}
		this.histogram = histogram;
	}

	public int[] calculateHistogramEffective(ArrayList<Float> _data,
			float overrideMin, float overrideMax) {
		// ///////////////////////////////////

		range = overrideMax - overrideMin;
		interval = range / nbars;

		// //////////////////////////////////////
		int[] histogram = new int[nbars];
		for (float dataPt : _data) {
			if (dataPt != dataPt)
				continue;
			int histo_pt = (int) ((dataPt - (overrideMin)) / (interval));
			if (histo_pt >= 0 && histo_pt < nbars)
				histogram[histo_pt]++;
		}

		for (int i : histogram) {
			if (i > maxBar) {

				maxBar = i;
			}
		}
		for (int i : histogram) {
			if (i > secondToMaxBar && i != maxBar) {
				secondToMaxBar = i;
			}
		}
		if (maxBar > 1.3 * secondToMaxBar) {
			usingMax = false;
			// maxBar = secondToMaxBar;
		}
		this.histogram = histogram;
		return histogram;
	}

	public void setUpBars() {

		histoBars = new Rectangle2D.Double[nbars];
		if (usingMax)
			graphicsScale = (frameH - 90) / maxBar;
		else
			graphicsScale = (frameH - 90) / secondToMaxBar;

		for (int i = 0; i < nbars; i++) {
			int top = (int) (frameH - SIDE_BUFFER - (int) (graphicsScale * histogram[i]));
			int left = SIDE_BUFFER + BAR_BUFFER * i + ((int) barwidth) * i;
			int width = (int) (barwidth);
			int height = (int) (graphicsScale * histogram[i]);
			histoBars[i] = new Rectangle2D.Double(left, top, width, height);

		}

	}

	private void setUpBars(boolean leftornot) {

		histoBars = new Rectangle2D.Double[nbars];
		if (usingMax)
			graphicsScale = (frameH - 90) / maxBar;
		else
			graphicsScale = (frameH - 90) / secondToMaxBar;

		graphicsScale *= 0.67;

		for (int i = 0; i < nbars; i++) {
			int top = (int) (frameH - SIDE_BUFFER - (int) (graphicsScale * histogram[i]));
			int left = SIDE_BUFFER + BAR_BUFFER * i + ((int) barwidth) * i;

			if (leftornot)
				left += 0;
			else
				left += barwidth / 2;
			int width = (int) (barwidth / 2);

			int height = (int) (graphicsScale * histogram[i]);
			histoBars[i] = new Rectangle2D.Double(left, top, width, height);

		}
	}

	public void setUpComparisonBars() {
		overBars.clear();
		underBars.clear();
		float toScale = 0.05f;
		for (int i = 0; i < nbars; i++) {

			int over = beatMarket.histogram[i];
			int under = marketBeat.histogram[i];
			int absDiff = Math.abs(over - under);

			int top = (int) (frameH - SIDE_BUFFER - (int) (toScale * absDiff + 2));
			int left = SIDE_BUFFER + BAR_BUFFER * i + ((int) barwidth) * i;
			int width = (int) (barwidth);
			int height = (int) (toScale * absDiff + 2);
			Rectangle2D.Double diff = new Rectangle2D.Double(left, top, width,
					height);

			if (over > under) {
				// green bar
				overBars.add(diff);
			} else if (under > over) {
				// red bar
				underBars.add(diff);
			}
		}
	}

	public void addMarketGraph(TreeMap<Float, Float> newData) {
		marketComparison = (newData);
		setMarketPathToScale();
	}

	private void setMarketPathToScale() {
		graphInterval = (frameW - 2 * SIDE_BUFFER)
				/ (marketComparison.size() - 1);
		gridLines.clear();
		float minimumPt = findMinimum(marketComparison.values());
		float maximumPt = findMaximum(marketComparison.values());
		// System.out.println("range: "+minimumPt+"   ---   "+maximumPt);
		float range = maximumPt - minimumPt;
		float localScale = (frameH - 2 * SIDE_BUFFER) / range;
		GeneralPath graph = new GeneralPath();
		int i = 0;
		for (float f : marketComparison.values()) {
			float xpt = SIDE_BUFFER + graphInterval * i;

			float ypt = frameH - (SIDE_BUFFER + localScale * (f - minimumPt));
			// System.out.println("POINTS IN PATH: "+xpt+"   ---   "+ypt);

			GeneralPath gridL = new GeneralPath();
			gridL.moveTo(xpt, 0);
			gridL.lineTo(xpt, frameH);
			gridLines.add(gridL);

			if (i == 0) {
				graph.moveTo(xpt, ypt);

			} else {
				graph.lineTo(xpt, ypt);
			}
			i++;
		}

		viewMarket = (graph);

	}

	public void addBundleSizes(TreeMap<Float, Integer> bundlesSizes) {
		bSizes.add(bundlesSizes);

	}

	public void addBundlesBuySell(TreeMap<Float, Point> bundlesSizes) {
		// displaySetChanges bSizesChanges
		bSizesChanges.add(bundlesSizes);

	}

	public void addPercentagesGraph(TreeMap<Float, Float> newData) {
		percentChanges.remove(newData);

		System.out.println("adding graph to view -->" + title + "   >"
				+ newData.values());
		percentChanges.add(newData);
		recalculatePercentagesScale();
		repaint();
	}

	private void recalculatePercentagesScale() {
		redDownLines.clear();
		greenUpLines.clear();
		percentChangesView.clear();
		zeroPercent = new GeneralPath();
		displaySizes.clear();
		// bSizes.clear();
		// bSizesChanges.clear();
		displaySetChanges.clear();
		TreeSet<Float> allValues = new TreeSet<Float>();
		for (TreeMap<Float, Float> tmf : percentChanges) {
			allValues.addAll(tmf.values());
		}
		ArrayList<Float> marketPoints = new ArrayList<Float>();
		float minimumPt = findMinimum(allValues);
		float maximumPt = findMaximum(allValues);

		float range = maximumPt - minimumPt;
		float localScale = (frameH - 2 * SIDE_BUFFER) / range;
		int index = 0;
		// only two percentage maps per frame - market and limit applied
		for (TreeMap<Float, Float> tmf : percentChanges) {
			GeneralPath graph = new GeneralPath();
			int i = 0;
			// for (float f : tmf.values()) {
			// convert actual data point into pixel number
			for (Entry<Float, Float> entr : tmf.entrySet()) {
				float f = entr.getValue();
				float key = entr.getKey();
				float xpt = SIDE_BUFFER + graphInterval * i;

				float ypt = frameH
						- (SIDE_BUFFER + localScale * (f - minimumPt));
				if (index == 0) {
					// save market changes points
					marketPoints.add(ypt);
				} else {
					// and compare to limit bundle
					float oldY = marketPoints.get(i);
					GeneralPath growth = new GeneralPath();
					growth.moveTo(xpt, oldY);
					growth.lineTo(xpt, ypt);
					if (oldY < ypt) {
						redDownLines.add(growth);
					} else {
						greenUpLines.add(growth);
					}
				}

				if (index > 0 && bSizes.size() > 0) {
					displaySizes.put(new Point2D.Float(xpt, ypt),
							bSizes.get(index - 1).get(key));
				}

				if (index > 0 && bSizesChanges.size() > 0) {
					displaySetChanges.put(new Point2D.Float(xpt, ypt),
							bSizesChanges.get(index - 1).get(key));
				}

				if (i == 0)
					graph.moveTo(xpt, ypt);
				else
					graph.lineTo(xpt, ypt);

				i++;
			}
			percentChangesView.add(graph);
			index++;
		}
		float zero = frameH - (SIDE_BUFFER + localScale * (0 - minimumPt));
		zeroPercent.moveTo(0, zero);
		zeroPercent.lineTo(frameW, zero);
	}

	public static float findMinimum(Collection<Float> values) {
		float MIN = Float.POSITIVE_INFINITY;

		for (float f : values)
			if (f < MIN)
				MIN = f;

		return MIN;
	}

	public static float findMaximum(Collection<Float> values) {
		float MAX = Float.NEGATIVE_INFINITY;
		for (float f : values)
			if (f > MAX)
				MAX = f;

		return MAX;
	}

	// //////////////////////////
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		// background
		g.setColor(bg);
		g.fillRect(0, 0, (int) frameW, (int) frameH);
		// grid
		g.setColor(gridLineColor);
		for (GeneralPath line : gridLines)
			g.draw(line);
		// draw histogram
		g.setColor(bc);
		for (Rectangle2D.Double f : histoBars) {
			g.fill(f);

		}
		g.setColor(redT);
		for (Rectangle2D.Double f : underBars) {
			g.fill(f);

		}
		g.setColor(greenT);
		for (Rectangle2D.Double f : overBars) {

			g.fill(f);

		}
		g.setColor(bcSub);
		if (subsetStats != null && subsetStats.histoBars != null)
			for (Rectangle2D.Double f : subsetStats.histoBars) {
				if (f != null)
					g.fill(f);

			}

		g.setStroke(thicker);
		g.setColor(Color.green);
		for (GeneralPath vert : greenUpLines)
			g.draw(vert);
		g.setColor(Color.red);
		for (GeneralPath vert : redDownLines)
			g.draw(vert);

		g.setColor(Color.yellow);
		g.draw(viewMarket);

		g.setStroke(thick);
		boolean first = true;
		for (GeneralPath gp : percentChangesView) {
			g.setColor(Color.cyan);
			if (first) {
				g.setColor(Color.orange);
				first = false;
			}
			g.draw(gp);
		}
		g.setColor(shade);
		// if (DataControlls.INVERT[ID].isSelected()) {
		// g.fill(shadedArea1);
		// g.fill(shadedArea2);
		// } else {
		// g.fill(shadedArea1);
		// }
		// upper and lower limit adjustors
		g.setColor(limLine);
		g.drawLine((int) nLim, 0, (int) nLim, (int) frameH);
		g.drawLine((int) xLim, 0, (int) xLim, (int) frameH);

		// add numbers to histogram
		addNumbersToHistogram(g);
		if (showLines) {
			drawRelativeLines(g);
		}

	}

	private void drawRelativeLines(Graphics2D g) {

		// float xZero = getPixel(0);
		// float xMedian = getPixel(median);
		// float xMean = getPixel(mean);
		// Line2D.Float zero = new Line2D.Float(xZero,0,xZero,frameH);
		// Line2D.Float _median = new Line2D.Float(xMedian,0,xMedian,frameH);
		// Line2D.Float _mean = new Line2D.Float(xMean,0,xMean,frameH);
		if (locationInHistogram(0) < 0)
			return;
		Rectangle2D.Double zero = histoBars[locationInHistogram(0)];
		Rectangle2D.Double _median = histoBars[locationInHistogram(median)];
		Rectangle2D.Double _mean = histoBars[locationInHistogram(mean)];

		g.setColor(Color.white);
		g.draw(zero);
		g.setColor(Color.orange);
		g.drawString("median:  " + median, 100, 200);
		g.draw(_median);
		g.setColor(Color.red);
		g.drawString("mean:  " + mean, 100, 250);
		g.draw(_mean);
	}

	private double getPixel(float pt) {
		return ((pt - emin) * (frameW - 2 * SIDE_BUFFER) / emax);
	}

	private void addNumbersToHistogram(Graphics2D g) {
		g.setColor(Color.black);
		g.draw(zeroPercent);
		// g.drawString(" " + gmin, nLim, gminVert);
		// g.drawString(" " + gmax, xLim, gmaxVert);
		g.drawString(" " + emin, 15, frameH - 30);
		g.drawString(" " + emax, frameW - 100, frameH - 30);
		if (false)
			for (Entry<Point2D.Float, Integer> entr : displaySizes.entrySet()) {
				g.drawString("" + entr.getValue(), entr.getKey().x,
						entr.getKey().y);
			}

		for (Entry<Point2D.Float, Point> entr : displaySetChanges.entrySet()) {
			g.setColor(Color.green);
			g.drawString("+" + entr.getValue().x, entr.getKey().x,
					entr.getKey().y - 18);
			g.setColor(Color.red);
			g.drawString("-" + entr.getValue().y, entr.getKey().x,
					entr.getKey().y);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		System.exit(0);

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	// //////////////////////////////////////////////////////////
	// ////////MOUSE EVENTS
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		setXlim = false;
		setNlim = false;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// final float x = arg0.getX();
		//
		// if ((x < xLim + drag_proximity && x > xLim - drag_proximity) ||
		// setXlim) {
		// xLim = x;
		// gmaxVert = arg0.getY();
		// gmax = determineLimit(x);
		// DataControlls.HIGHS[ID].setText("" + gmax);
		// updateShadedArea();
		// } else if ((x < nLim + drag_proximity && x > nLim - drag_proximity)
		// || setNlim) {
		// nLim = x;
		// gminVert = arg0.getY();
		// gmin = determineLimit(x);
		// DataControlls.LOWS[ID].setText("" + gmin);
		// updateShadedArea();
		// }
		// subsetStats = DataControlls.generateBundleStatistics(ID, emin, emax);
		// DataControlls.applyLimtsToDatabase();
		// repaint();
	}

	private void updateShadedArea() {
		// if (DataControlls.INVERT[ID].isSelected()) {
		// // add central rect
		// shadedArea1.width = nLim;
		// shadedArea2.x = xLim;
		// shadedArea2.width = 5555;
		// } else {
		// shadedArea1.x = nLim;
		// shadedArea1.width = xLim - nLim;
		//
		// }
	}

	// /////////////////////////////////////
	// ///////MOUSE MOTION
	@Override
	public void mouseDragged(MouseEvent arg0) {

		final float x = arg0.getX();
		if (x < xLim + drag_proximity && x > xLim - drag_proximity) {
			xLim = x;
			setXlim = true;
			// DataControlls.HIGHS[ID].setText(""+determineLimit(x));
		} else if (x < nLim + drag_proximity && x > nLim - drag_proximity) {
			nLim = x;
			setNlim = true;
			// DataControlls.LOWS[ID].setText(""+determineLimit(x));
		}
		updateShadedArea();
		if (repaintCounter++ % 50 == 0)
			repaint();
		// CALCULATE LIMITS AND APPLY TO DATABASE
	}

	private double determineLimit(float x) {
		double limit = 0;
		double ex = x - SIDE_BUFFER;

		double range = emax - emin;
		double pixelRange = frameW - 2 * SIDE_BUFFER;
		double f = range / pixelRange;

		limit = emin + ex * f;
		BigDecimal bd = new BigDecimal(limit);
		bd = bd.round(new MathContext(4));
		limit = bd.floatValue();
		System.out.println("limits: " + limit);
		return limit;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

}
