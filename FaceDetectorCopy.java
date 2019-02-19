package com.ifc.courts.facerecog.impl;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class FaceDetectorCopy extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final HaarCascadeDetector detector = new HaarCascadeDetector();
	private Webcam webcam = null;
	private BufferedImage img = null;
	private List<DetectedFace> faces = null;
	private Connection conn = null;
	private  JFrame  facerecogFr = new JFrame();
	
	private String successMsg ="Congratulations! Face recognition authentication is successful.";
	
	private String errorMsg ="Failed: invalid user, Click No to retry with face recognition authentication process";
	
	public FaceDetectorCopy() throws IOException {
	
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(true);
		img = webcam.getImage();
		webcam.close();
		ImagePanel panel = new ImagePanel(img);
		panel.setPreferredSize(WebcamResolution.VGA.getSize());
		add(panel);
		facerecogFr.setTitle("Face Recognizer");
		facerecogFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		facerecogFr.pack();
		facerecogFr.setLocationRelativeTo(null);
		facerecogFr.setVisible(true);
		
	}

	public boolean detectFace(int imgId) throws IOException {
		byte[] readImgDb = null;
		boolean isValidUser = false;
		boolean isInvalidImage = false;
		JFrame fr = new JFrame("Discovered Faces");
		ImageIO.write(img, "JPG", new File("C:\\image\\sampleImg16.jpg"));

		System.out.println("store to file is successful");
		isInvalidImage = new ByteArrayToImage().convertToSlicedFile("C:\\image\\sampleImg16.jpg");
		System.out.println("invalid image or not"+isInvalidImage);
		  if(!isInvalidImage) {
			  System.out.println("inside if condition invalid image or not"+isInvalidImage);
			  return false;
		  }
		faces = detector.detectFaces(ImageUtilities.createFImage(img));
		if (faces == null) {
			System.out.println("No faces found in the captured image");
			return isValidUser;
		}

		Iterator<DetectedFace> dfi = faces.iterator();
		while (dfi.hasNext()) {
			DetectedFace face = dfi.next();
		
			FImage image1 = face.getFacePatch();
		
			byte[] imageByt = new ByteArrayToImage().convertFileToByteArray("C:\\image\\slicedpic.jpg");
			try {
				new ByteArrayToImage().createFileUsingByteArray(imageByt);
				saveImageToDb(imageByt);
				System.out.println("store to db is successful");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				readImgDb = readImageDB(imgId);
				new ByteArrayToImage().createFileUsingByteArrayFromDB(readImgDb);
				System.out.println("read from db successfully" + readImgDb.length);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				// boolean result =validateImage();
				boolean result = verifyImages();

				System.out.println("validated successfully" + result);
				if (result) {
					isValidUser = true;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			/*ImagePanel p = new ImagePanel(ImageUtilities.createBufferedImage(image1));
			
			File dbFile =  new ByteArrayToImage().convertFilePathtoFile("C:\\image\\DBoutput.jpg");
			BufferedImage dbOutputBufImg = new ByteArrayToImage().loadOriginalImage(dbFile);
			
			
			
			File capturedFile =  new ByteArrayToImage().convertFilePathtoFile("C:\\image\\output.jpg");
			BufferedImage capturedBufImg = new ByteArrayToImage().loadOriginalImage(capturedFile);
			*/
			
			
			JLabel label1 = new JLabel("Test");

			if (isValidUser) {

				label1.setText(successMsg);
				
			} else {
				label1.setText(errorMsg);
			}

			
			new ImagePaneExample("C:\\image\\DBoutput.jpg","C:\\image\\output.jpg",label1,fr);
		
		}
		fr.setLayout(new FlowLayout(0));
		fr.setSize(500, 350);

		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		fr.setVisible(true);
		
		
		final JOptionPane optionPane = new JOptionPane(
                "Please choose Yes to proceed with document check or No to cancel",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);

final JDialog dialog = new JDialog(fr, "Choose Yes to Proceed ", true);
dialog.setContentPane(optionPane);
dialog.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE);


dialog.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent we) {
       System.out.println("window ia about to close");
    }
});
optionPane.addPropertyChangeListener(
    new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
            String prop = e.getPropertyName();

            if (dialog.isVisible() 
             && (e.getSource() == optionPane)
             && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
               
                dialog.setVisible(false);
            }
        }
    });
dialog.pack();
dialog.setVisible(true);

		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			facerecogFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fr.setVisible(false);
			facerecogFr.setVisible(false);
			System.out.println("Good");
		} else if (value == JOptionPane.NO_OPTION) {
			
			System.out.println("Try using the window decorations");
			fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fr.setVisible(false);
			facerecogFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			facerecogFr.setVisible(false);
}
				
				
		return isValidUser;
	}

	public boolean verifyImages() throws IOException {
		boolean result = false;
		String originalPath = "C:\\image\\DBoutput.jpg";
		String generatedPath = "C:\\image\\output.jpg";
		try {
			result = new CompareImage().compareImagesFromBufferedImage(originalPath, generatedPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public void saveImageToDb(byte[] byteImg) throws SQLException {
		PreparedStatement statement = null;
		String sql = "INSERT INTO IMAGEBLOB(BLOBName, BLOBData) VALUES(?,?)";
		double randomNo = Math.random();
		String randomNumber = Double.toString(randomNo);
		String testString = "SImg" + randomNumber;
		byte[] testBytes = byteImg;
		int rowsaffected = 0;
		conn = ConnectionDB.getConnection();
		statement = conn.prepareStatement(sql);
		try {
			statement.setString(1, testString);
			statement.setBinaryStream(2, new ByteArrayInputStream(testBytes), testBytes.length);
			rowsaffected = statement.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
	}

	public byte[] readImageDB(int imageId) throws SQLException {
		byte[] theBytes = null;
		// assuming theID is passed in from somewhere

		String sql = "SELECT BLOBDATA FROM IMAGEBLOB WHERE IMAGEID=" + imageId;
		
		conn = ConnectionDB.getConnection();
		Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		try {
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) { // got at least one row, and only one row if theID is primary key
				Blob blob = result.getBlob("BLOBDATA"); // creates the blob object from the result
				/*
				 * blob index starts with 1 instead of 0, and starting index must be (long). we
				 * want all the bytes back, so this grabs everything. keep in mind, if the blob
				 * is longer than max int length, this won't work right because a byte array has
				 * max length of max int length.
				 */
				theBytes = blob.getBytes(1L, (int) blob.length());
				// just to make sure:
				System.out.println("blob back to string |" + new String(theBytes) + "|");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// conn.close();
			// statement.close();
		}
		return theBytes;

	}
	
	
	

	
}
