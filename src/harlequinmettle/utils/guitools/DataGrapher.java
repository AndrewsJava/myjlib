// Oct 21, 2015 11:58:57 AM
package harlequinmettle.utils.guitools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;

public class DataGrapher extends JPanel {

	private static final int POINT_SIZE = 10;
	// private TreeMap<Integer, ArrayList<Float>> dataSets = new
	// TreeMap<Integer, ArrayList<Float>>();
	volatile private TreeMap<String, ArrayList<Point2D.Float>> titledDataSets = new TreeMap<String, ArrayList<Point2D.Float>>();
	volatile private TreeMap<String, ArrayList<Point2D.Float>> scaledDataPoints = new TreeMap<String, ArrayList<Point2D.Float>>();

	private double width;
	private double height;
	private double minX = Double.POSITIVE_INFINITY;
	private double maxX = Double.NEGATIVE_INFINITY;
	private double minY = Double.POSITIVE_INFINITY;
	private double maxY = Double.NEGATIVE_INFINITY;
	private TreeMap<String, Color> colors = new TreeMap<String, Color>();
	ErrorLine errorLine = new ErrorLine();

	class ErrorLine {
		private ArrayList<Float> errorPoints = new ArrayList<Float>();
		GeneralPath errorGraph = new GeneralPath();

		public void addErrorPoint(float error) {
			errorPoints.add(error);
		}

		// Oct 23, 2015 12:09:16 PM
		private void rescaleErrorLine() {
			int x = 10;
			int y = 20;
			errorGraph.moveTo(x, y);
			if (errorPoints.isEmpty())
				return;
			float initialError = errorPoints.get(0);
			float errorGraphScalingFactor = (float) (height / initialError);
			float interval = 5;
			for (float error : errorPoints) {
				x += interval;
				y = (int) (height + 10 - error * errorGraphScalingFactor);
				errorGraph.lineTo(x, y);
			}
		}
	}

	public void addData(int index, ArrayList<Float> x, ArrayList<Float> y) {
		addData("" + index, x, y);
	}

	public void addData(String index, ArrayList<Float> x, ArrayList<Float> y) {
		ArrayList<Point2D.Float> dataset = pairData(x, y);
		ArrayList<Point2D.Float> scaleSet = scalePoints(dataset);
		titledDataSets.put("" + index, dataset);
		scaledDataPoints.put("" + index, scaleSet);
		int colorCode = index.hashCode();
		colors.put(index, new Color(colorCode));
	}

	// private Color getRandomColor(int colorCode) {
	// // Oct 22, 2015 10:00:12 AM
	// Color randomColor = new Color(colorCode);
	// return null;
	// }

	// Oct 22, 2015 9:05:16 AM
	private ArrayList<Point2D.Float> scalePoints(ArrayList<Point2D.Float> dataset) {

		ArrayList<Point2D.Float> scaleSet = new ArrayList<Point2D.Float>();
		if (dataset.isEmpty())
			return scaleSet;
		float horizontalInterval = (int) (width / dataset.size());
		float vertRange = (float) (maxY - minY);
		float verticalFactor = (float) -(height / vertRange);
		float horizontalPoint = (float) (0.02f * width);
		for (Point2D.Float originalData : dataset) {
			Point2D.Float scaledPoint = new Point2D.Float();
			scaledPoint.x = horizontalPoint;
			scaledPoint.y = (float) (0.04 * height + verticalFactor * (minY + originalData.y));
			scaleSet.add(scaledPoint);
			horizontalPoint += horizontalInterval;
		}
		return scaleSet;
	}

	// Oct 21, 2015 1:12:14 PM
	private ArrayList<Point2D.Float> pairData(ArrayList<Float> data1, ArrayList<Float> data2) {
		setMinMaxX(data1);
		setMinMaxY(data2);
		int size = data1.size() < data2.size() ? data1.size() : data2.size();
		ArrayList<Point2D.Float> dataset = new ArrayList<Point2D.Float>();
		for (int i = 0; i < size; i++) {
			float x = data1.get(i);
			float y = data2.get(i);
			Point2D.Float d = new Point2D.Float(x, y);
			dataset.add(d);
		}
		return dataset;
	}

	public void addData(int index, ArrayList<Point2D.Float> dataset) {
		titledDataSets.put("" + index, dataset);
	}

	public void addData(String title, ArrayList<Point2D.Float> dataset) {
		titledDataSets.put(title, dataset);
	}

	private boolean setMinMaxY(ArrayList<Float> dataset) {
		// Oct 21, 2015 12:48:42 PM
		double maxY = Collections.max(dataset);
		boolean minMaxChange = false;
		if (maxY > this.maxY) {
			this.maxY = maxY;
			minMaxChange = true;
		}
		double minY = Collections.min(dataset);
		if (minY < this.minY) {
			this.minY = minY;
			minMaxChange = true;
		}
		return minMaxChange;
	}

	private boolean setMinMaxX(ArrayList<Float> dataset) {
		// Oct 21, 2015 12:48:42 PM
		double maxX = Collections.max(dataset);
		boolean minMaxChange = false;
		if (maxX > this.maxX) {
			this.maxX = maxX;
			minMaxChange = true;
		}
		double minX = Collections.min(dataset);
		if (minX < this.minX) {
			this.minX = minX;
			minMaxChange = true;
		}
		return minMaxChange;
	}

	public DataGrapher() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() - 50;
		height = screenSize.getHeight() - 50;
		errorLine.rescaleErrorLine();
		// setDimensions();
	}

	@Override
	public void repaint() {
		// Oct 22, 2015 9:23:43 AM
		setDimensions();
		super.repaint();
	}

	// Oct 22, 2015 8:13:36 AM
	private void setDimensions() {
		Dimension screenSize = getSize();
		width = screenSize.getWidth() * 0.95;
		height = screenSize.getHeight() * 0.95;
		errorLine.rescaleErrorLine();
	}

	// Oct 21, 2015 12:12:29 PM
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		// setBackground(new Color(128, 40, 228));
		BufferedImage canvas = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = canvas.createGraphics();
		drawData(g2d);

		// Graphics2D g = (Graphics2D) g1;
		g1.drawImage(canvas, 0, 0, null);
	}

	private void drawData(Graphics2D g2d) {
		// Oct 21, 2015 12:42:44 PM
		int colorchooser = 0;
		for (Entry<String, ArrayList<Point2D.Float>> ent : scaledDataPoints.entrySet()) {
			ArrayList<Point2D.Float> pointsArray = ent.getValue();
			GeneralPath line = new GeneralPath();
			Point2D.Float firstPoint = pointsArray.get(0);
			boolean first = true;
			line.moveTo(firstPoint.x, firstPoint.y);
			if (colorchooser++ == 0)
				g2d.setColor(Color.white);
			else
				g2d.setColor(Color.blue);
			// g2d.setColor(colors.get(ent.getKey()));
			for (Point2D.Float points : pointsArray) {
				if (first) {
					first = false;
					continue;
				}
				float scaledX = points.x;
				float scaledY = points.y;
				line.lineTo(scaledX, scaledY);
				g2d.fillOval((int) scaledX - POINT_SIZE / 2, (int) scaledY - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
			}
			g2d.draw(line);
		}

		g2d.setColor(Color.green);
		// g2d.draw(errorGraph);
	}
}
