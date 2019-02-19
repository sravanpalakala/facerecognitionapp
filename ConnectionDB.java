package com.ifc.courts.facerecog.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	private static ConnectionDB dbIsntance;
	private static Connection con;
	private static Statement stmt;

	private ConnectionDB() {

	}

	public static ConnectionDB getInstance() {
		if (dbIsntance == null) {
			dbIsntance = new ConnectionDB();
		}
		return dbIsntance;
	}

	public static Connection getConnection() throws SQLException {
		if (con == null) {
			try {
				String jdbc_url = "jdbc:sqlserver://localhost:1433;databaseName=DMSWF_BNI";
				String db_userid = "sa";
				String db_password = "sasa";
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				con = DriverManager.getConnection(jdbc_url, db_userid, db_password);
				System.out.println("connected" + con);
				
			}catch (SQLException ex) {
                //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
				ex.printStackTrace();
            } 
			
			catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		return con;
	}

	public static void main(String[] args) throws SQLException {
		/*ConnectionDB connServer = new ConnectionDB();
		connServer.dbConnect("jdbc.url = jdbc:sqlserver://localhost:1433;databaseName=DMSWF_BNI", "sa", "sasa");*/

	}}
