package com.ifc.courts.facerecog.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.alignment.FaceAligner;
import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.CLMFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.util.CLMDetectedFaceRenderer;
import org.openimaj.image.processing.face.util.KEDetectedFaceRenderer;
import org.openimaj.image.processing.resize.ResizeProcessor;

public class ByteArrayToImage {
	private BufferedImage buffImage;
	private File file;


		 
	public byte[] convertFileToByteArray(String filePath) {
		byte[] data = null;

		try {
			BufferedImage bImage = ImageIO.read(new File(filePath));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bImage, "jpg", bos);
			data = bos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
	
	public boolean createFileUsingByteArray(byte[] data) throws IOException {
		boolean result = false;
	
		 try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			  BufferedImage bImage2 = ImageIO.read(bis);
			 result= ImageIO.write(bImage2, "jpg", new File("C:\\image\\output.jpg") );
			  System.out.println("image created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return result;
	}
	
	public boolean createFileUsingByteArrayFromDB(byte[] data) throws IOException {
		boolean result = false;
	
		 try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			  BufferedImage bImage2 = ImageIO.read(bis);
			 result= ImageIO.write(bImage2, "jpg", new File("C:\\image\\DBoutput.jpg") );
			  System.out.println("image created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return result;
	}
	
	
	
	public boolean convertToSlicedFile(String filePath) throws IOException {
		boolean result = false;
		try {
			
			FImage img = ImageUtilities.readF(new File(filePath));
			FKEFaceDetector detector = new FKEFaceDetector();
			FaceAligner<KEDetectedFace> aligner = new AffineAligner();
			KEDetectedFace face = detector.detectFaces(img).get(0);
			FImage alignedFace = aligner.align(face);
			ImageUtilities.write(alignedFace, new File("C:\\image\\slicedpic.jpg"));
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	public static void main(String args[]) {
		ByteArrayToImage image = new ByteArrayToImage();
		try {
			image.convertToSlicedFile("C:\\image\\output.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage decodeToImage(String imageString) {
		 
        BufferedImage image = null;
        byte[] imageByte;
        try {
            Decoder decoder =  Base64.getDecoder();
            imageByte = decoder.decode(imageString);
          
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
	
	
	public BufferedImage loadOriginalImage(File file) {
	      try {
	    	  buffImage = ImageIO.read(file);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return buffImage;
	   }
	
   public File convertFilePathtoFile(String filePath) {
	   try {
		  this.file = new File(filePath);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   return file;
   }
	
}
