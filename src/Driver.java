import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root" , "Password");
			Statement myStat= myConn.createStatement();
			
//			ResultSet myRs= myStat.executeQuery("SELECT * FROM lab4.Student;");
//			while (myRs.next()) {
//				System.out.println(myRs.getString("SSN") + ", " + myRs.getString("Name"));
//			}
		
		
		}
		catch (Exception e ) {
			e.printStackTrace();
		}

	}

}
