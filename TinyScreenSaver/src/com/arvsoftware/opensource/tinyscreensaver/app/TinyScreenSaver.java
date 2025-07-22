package com.arvsoftware.opensource.tinyscreensaver.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class TinyScreenSaver {
	
	// for two seconds, you can move the mouse without it shutting down the program.
	public double timeout = 2000;
	public double startTime = System.currentTimeMillis();
	
	// settings that may get a config file in a later version
	public boolean hideCursor = true;
	public boolean exitOnMouseMove = true;
	
	public TinyScreenSaver() {
		System.out.println("Tiny Screen Saver - By ProbablyStupid");
		
		JFrame frame = new JFrame();
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenDimension);
		frame.setTitle("Tiny Screen Saver");
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setLocation(0, 0);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setBackground(Color.black);
		
		frame.setVisible(true);
		
		BufferedImage image = null;
		
		// load image
		try {
			
			File jarFilepath = new File(TinyScreenSaver.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			File jarPath = jarFilepath.getParentFile();
			File imageFile = new File(jarPath, "screensaver.png");
			if (imageFile.exists() && imageFile.isFile()) {
				System.out.println("found screensaver.png!");
				image = ImageIO.read(imageFile);
			} else {
				// default to a built-in default image
				System.out.println("screensaver.png not found, using builtin alternative!");
				image = ImageIO.read(TinyScreenSaver.class.getResourceAsStream("sosp.png"));
			}
			
			if (image != null) {
				System.out.println("Loaded image successfully!");
			}
			
		} catch (IOException | URISyntaxException | IllegalArgumentException | NullPointerException e) {
			System.out.println("Could not read image!");
			e.printStackTrace();
		}
		
		frame.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				System.exit(0);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// There must NOT be a system.exit(0); here because the mouse enters the window on startup, making it immediately close
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		frame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				double timeElapsed = System.currentTimeMillis() - startTime;
				if (timeElapsed >= timeout && exitOnMouseMove) {
					System.out.println("Exited after : " + timeElapsed + " Miliseconds");
					System.exit(0);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				System.exit(0);
			}
		});
		
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				System.exit(0);
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.exit(0);
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.exit(0);
				
			}
		});
		
		if (hideCursor) {
			frame.setCursor(frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), "null"));
		}
		 
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.black);
		frame.add(imagePanel);
		frame.setContentPane(imagePanel);
		frame.setLayout(null);
		
		ImageIcon imageIcon = new ImageIcon(image);
		JLabel imageLabel = new JLabel(imageIcon);
		imageLabel.setSize(image.getWidth(), image.getHeight());
		
		imagePanel.add(imageLabel);
		
		imagePanel.revalidate();
		imagePanel.repaint();
		frame.revalidate();
		frame.repaint();
		
		// generate a random direction between - 2 and 2
//		int dirx = (int) Math.round((Math.random() * 4)) + 1, diry = (int) Math.round((Math.random() * 4)) + 1;
		
		final int[] direction = {(int) (Math.round((Math.random() * 4)) + 1), (int) (Math.round((Math.random() * 4)) + 1)};
		int limx = screenDimension.width - imageLabel.getWidth(), limy = screenDimension.height - imageLabel.getHeight();
		
		// draw loop
//		while (enabled) {
//			imageLabel.setLocation(imageLabel.getX() + dirx, imageLabel.getY() + diry);
//			
//			if (imageLabel.getX() >= limx || imageLabel.getX() <= 0) {
//				dirx = -dirx;
//			}
//			if (imageLabel.getY() >= limy || imageLabel.getY() <= 0) {
//				diry = -diry;
//			}
//			
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			this.timeElapsed += (System.currentTimeMillis() - this.startTime);
//			this.startTime = System.currentTimeMillis();
//			System.out.println("Running with status " + enabled);
//		}
		
		Timer myTimer = new Timer(10, e -> {
			imageLabel.setLocation(imageLabel.getX() + direction[0], imageLabel.getY() + direction[1]);
			
			if (imageLabel.getX() >= limx || imageLabel.getX() <= 0) {
				direction[0] = -direction[0];
			}
			if (imageLabel.getY() >= limy || imageLabel.getY() <= 0) {
				direction[1] = -direction[1];
			}
		});
		myTimer.start();
	}
	
	public static void main(String[] args) {
		Object lock = new Object();
		SwingUtilities.invokeLater(() -> {
			try {
				new TinyScreenSaver();
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (lock) {
				lock.notify();
			}
		});
		
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
