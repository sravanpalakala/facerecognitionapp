package com.ifc.courts.facerecog.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	Connection conn = null;
	public int getImageIdFromDBByUserName(String userName) throws SQLException {
		Integer imageId = 0;
		//assuming theID is passed in from somewhere
		 System.out.println("getting image id for given username:"+userName);
		String sql = "SELECT IMAGEID FROM IMAGEBLOB WHERE BLOBNAME="+userName;
		
		conn = ConnectionDB.getConnection();
		Statement statement = conn.createStatement();
		
		try {
			ResultSet result    = statement.executeQuery(sql);
			if(result.next()){ 
			    imageId = result.getInt("IMAGEID"); 
			
			   System.out.println("ImageID :"+imageId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//conn.close();
			//statement.close();
		}
		return imageId;

	}
	
	public int getId(String userName) throws SQLException {
		int imgId =0;
		 try {
			    conn = ConnectionDB.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT IMAGEID FROM IMAGEBLOB WHERE BLOBNAME= ?"); 
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();

		        while (rs.next()) {
		        	imgId = rs.getInt(1);
		            System.out.println("image id from db:"+imgId);
		        }
		    }
		    // Handle any errors that may have occurred.
		    catch (SQLException e) {
		        e.printStackTrace();
		    }finally {
		    	//conn.close();
		    }
		 return imgId;
	}

}
