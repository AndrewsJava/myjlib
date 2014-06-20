package harlequinmettle.utils.systemtools;

import harlequinmettle.utils.guitools.CommonColors;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class SystemMemoryUseDisplay extends JFrame implements Runnable {
	int inc = 0;
	String title = "Memory Use: ";
	int mem = 0;
	int slowdown = 0;

	public SystemMemoryUseDisplay() {
		this.setSize(300, 800);
		this.setVisible(true);
		this.setTitle(title);
		new Thread(this).start();
	}

	public void paint(Graphics g) {
		float maxMem = Runtime.getRuntime().maxMemory();
		float useMem = Runtime.getRuntime().totalMemory();
		float freMem = Runtime.getRuntime().freeMemory();
		int height = this.getHeight() - 90;

		float scale = (float) height / maxMem;
		int max = (int) (maxMem * scale);
		int use = (int) (useMem * scale);
		int fre = (int) (freMem * scale);
		mem = (int) (useMem / 1000000);
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(CommonColors.FAINT_BLACK);
		g.fillRect(0, 80, 100, max);

		g.setColor(CommonColors.FAINT_GREEN_BLUE);
		g.fillRect(100, 80, 100, use);

		g.setColor(CommonColors.FAINT_GREEN);
		g.fillRect(200, 80, 100, fre + inc);

		g.setColor(Color.white);
		g.drawString("" + (long) maxMem / 1000000 + " MB", 0, 70);
		g.drawString("" + (long) useMem / 1000000 + " MB", 100, 70);
		g.drawString("" + (long) freMem / 1000000 + " MB", 200, 70);

	}

	@Override
	public void run() {
		while (true) {
			this.repaint();
			this.setTitle(title + mem);
			try {
				if (slowdown < 3000)
					slowdown += 10;
				Thread.sleep(100 + slowdown);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
