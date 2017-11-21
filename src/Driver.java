import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root" , "Password");
			Statement myStat= myConn.createStatement();
			System.out.println("Connected database successfully...");
			System.out.println("Creating tables...");
			//createTables(myStat);

			//		      
			String sql = "DROP TABLE REGISTRATION ";

			myStat.executeUpdate(sql);

		}



		catch (Exception e ) {
			e.printStackTrace();
		}

	}



	public static void createTables(Statement myStat) {
		String TripOffering = "CREATE TABLE TripOffering " +
				"("+ 
				"TripNumber INTEGER not NULL, " +
				" Date VARCHAR(10), " + 
				" StartTime VARCHAR(5), " + 
				" ArrivalTime VARCHAR(5), " + 
				"DriverName VARCHAR(25), " + 
				"BusID VARCHAR(25), " + 
				" PRIMARY KEY ( TripNumber, Date, StartTime )"
				+ ")"; 

		String Bus = "CREATE TABLE Bus " +
				"("+ 
				"BusID VARCHAR(25), " +
				" Model VARCHAR(25), " + 
				" Year INTEGER," + 
				" PRIMARY KEY ( BusID ),"+ 
				"FOREIGN KEY (BusID) REFERENCES TripOffering(BusID)"+
				")"; 

		String Driver = "CREATE TABLE Driver " +
				"("+ 
				"DriverName VARCHAR(25), " +
				" DriverTelephoneNumber INTEGER, " + 
				" PRIMARY KEY ( DriverName ),"+ 
				"FOREIGN KEY (DriverName) REFERENCES TripOffering(DriverName)"+ 
				")"; 
		String Trip = "CREATE TABLE Trip " +
				"("+ 
				"TripNumber INTEGER not NULL," +
				" StartLocationName VARCHAR(30), " + 
				"DestinationName VARCHAR (30)"+ 
				" PRIMARY KEY ( DriverName ),"+ 
				"FOREIGN KEY (TripNumber) REFERENCES TripOffering(TripNumber)"+ 
				")"; 

		try {
			myStat.executeUpdate(TripOffering);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
