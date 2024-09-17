package com.opencvcamera;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Camera extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel cameraScreen;
	private JButton btnCapture;
	private VideoCapture capture;
	private Mat image;
	private boolean clicked = false;

	public Camera() {
		setLayout(null);
		cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 560);
		add(cameraScreen);
		btnCapture = new JButton("Capture");
		btnCapture.setBounds(300, 480, 80, 40);
		add(btnCapture);

		btnCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clicked = true;

			}
		});

		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// criacao da camera
	public void startCamera() {
		capture = new VideoCapture(0);
		image = new Mat();
		byte[] imageData;

		ImageIcon icon;
		
		while (true) {
			// le a imagem dentro da Mat
			capture.read(image);
			// convertendo a matriz pra byte
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);
			imageData = buf.toArray();
			
			// add to JLabel
			
			
			
			
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);
			// capture and save to file
			if (clicked) {
				String name = JOptionPane.showInputDialog("Enter image name: ");
				if(name == null) {
					name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
				}
				//write to file
				Imgcodecs.imwrite("images/" + name + ".jpg", image);
				
				clicked = false;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Camera camera = new Camera();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						camera.startCamera();
					}
				}).start();
			}
		});
	}

}
