package com.ifc.courts.facerecog.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;


public class TestWebcam extends JFrame implements ActionListener {
	JButton submit, cancel;
	JLabel user_label, password_label, message;
	JPanel captpanel;
	JFrame window = new JFrame("DUCKAP System Face recognition Application");
	Webcam webcam = null;
	Integer ImageId =0;
	Boolean result = true;
	public void openWebcam(Integer imgId) {
        this.ImageId = imgId;

		 webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

	
		window.add(panel,BorderLayout.NORTH);
		submit = new JButton("CAPTURE");

		captpanel =  new JPanel();
		

		message = new JLabel("Please take your position close to webcam,click capture to take picture");
		captpanel.add(message);
		captpanel.add(submit);
		
		window.add(captpanel,BorderLayout.SOUTH);
		
		
		submit.addActionListener(this);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		TestWebcam test = new TestWebcam();
		test.openWebcam(2093);
	}




	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			webcam.close();
		this.result =	new FaceDetectorCopy().detectFace(this.ImageId);
		System.out.println("action performed:"+this.result);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(false);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
