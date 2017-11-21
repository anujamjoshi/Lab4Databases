import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root" , "Password");
			Statement myStat= myConn.createStatement();
			 System.out.println("Connected database successfully...");
			 System.out.println("Creating table in given database...");
		    
		      
		      String sql = "CREATE TABLE REGISTRATION " +
		                   "(id INTEGER not NULL, " +
		                   " first VARCHAR(255), " + 
		                   " last VARCHAR(255), " + 
		                   " age INTEGER, " + 
		                   " PRIMARY KEY ( id ))"; 

		      myStat.executeUpdate(sql);
		      System.out.println("Created table in given database...");
			
		}
		
		
		
		catch (Exception e ) {
			e.printStackTrace();
		}

	}

}
