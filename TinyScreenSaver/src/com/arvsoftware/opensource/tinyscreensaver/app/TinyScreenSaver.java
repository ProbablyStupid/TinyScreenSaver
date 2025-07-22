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
import javax.swing.WindowConstants;

public class TinyScreenSaver {
	
	// for two seconds, you can move the mouse without it shutting down the program.
	public double timeout = 2000;
	public double timeElapsed = 0;
	public double startTime = 0;
	
	// settings that may get a config file in a later version
	public boolean hideCursor = true;
	public boolean exitOnMouseMove = true;
	
	public TinyScreenSaver() {
		boolean enabled = true;
		
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
			
		} catch (IOException | URISyntaxException e) {
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
				System.exit(0);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				
			}
		});
		
		frame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
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
		int dirx = (int) Math.round((Math.random() * 4)) + 1, diry = (int) Math.round((Math.random() * 4)) + 1;
		int limx = screenDimension.width - imageLabel.getWidth(), limy = screenDimension.height - imageLabel.getHeight();
		
		
		this.startTime = System.currentTimeMillis();
		
		// draw loop
		while (enabled) {
			imageLabel.setLocation(imageLabel.getX() + dirx, imageLabel.getY() + diry);
			
			if (imageLabel.getX() >= limx || imageLabel.getX() <= 0) {
				dirx = -dirx;
			}
			if (imageLabel.getY() >= limy || imageLabel.getY() <= 0) {
				diry = -diry;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.timeElapsed += (System.currentTimeMillis() - this.startTime);
			this.startTime = System.currentTimeMillis();
		}
	}
	
	public static void main(String[] args) {
		TinyScreenSaver myTinyScreenPlayer = new TinyScreenSaver();
	}
	
}
