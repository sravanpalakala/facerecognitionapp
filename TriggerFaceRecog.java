package com.ifc.courts.facerecog;

import java.io.IOException;

/*import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;*/

import com.ifc.courts.facerecog.impl.FaceDetectorCopy;
import com.ifc.courts.facerecog.impl.HomeScreen;
import com.ifc.courts.facerecog.impl.Login;

public class TriggerFaceRecog {

	//protected final Logger log = LogManager.getLogger(TriggerFaceRecog.class);
	public String triggerFaceRecongnition(String icNum) throws IOException
	{
	   boolean result= false;
	   String message ="";
		int icnumber = Integer.parseInt(icNum);
		long wrkid =0;
		try {
			//result = new FaceDetectorCopy().detectFace(icnumber);
		    result = new HomeScreen().openWebcam(icnumber);
			//wrkid = new FaceRecogService().instantiateWorkflowIdFromDMS();
			//new FaceRecogService().customerFormSubmission(wrkid, icnumber);
			if(result) {
				message ="valid";
			}else {
				message ="invalid";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	
	}
}
