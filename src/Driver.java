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
				" ScheduledStartTime VARCHAR(5), " + 
				" SecheduledArrivalTime VARCHAR(5), " + 
				"DriverName VARCHAR(25), " + 
				"BusID VARCHAR(25), " + 
				" PRIMARY KEY ( TripNumber, Date, ScheduledStartTime )"
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
				" PRIMARY KEY ( TripNumber ),"+ 
				"FOREIGN KEY (TripNumber) REFERENCES TripOffering(TripNumber)"+ 
				")"; 
		String TripStopInfo  = "CREATE TABLE TripStopInfo  " +
				"("+ 
				"TripNumber INTEGER not NULL," +
				" StopNumber INTEGER, " + 
				"SequenceNumber INTEGER,"+
				"DrivingTime INTEGER "+
				" PRIMARY KEY ( TripNumber, StopNumber ),"+ 
				"FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber)"+ 
				")"; 
		String ActualTripStopInfo   = "CREATE TABLE ActualTripStopInfo   " +
				"("+ 
				"TripNumber INTEGER not NULL, " +
				" Date VARCHAR(10), " + 
				" ScheduledStartTime VARCHAR(5), " + 
				" StopNumber INTEGER, " + 
				" SecheduledArrivalTime VARCHAR(5), " + 
				" ActualStartTime VARCHAR(5), " + 
				" ActualArrivalTime VARCHAR(5), " + 
				" NumberOfPassengerIn INTEGER, " + 
				" NumberOfPassengerOut INTEGER, " + 
				" PRIMARY KEY ( TripNumber, Date, ScheduledStartTime, StopNumber ),"+
				"FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber)"+ 
				")"; 
		String Stop = "CREATE TABLE Stop" +
				"("	+ 
				" StopNumber INTEGER, " + 
				" StopAddress VARCHAR(30), " + 
				" PRIMARY KEY ( StopNumber ),"+
				"FOREIGN KEY (StopNumber) REFERENCES TripStopInfo(StopNumber)"+ 
				"FOREIGN KEY (StopNumber) REFERENCES ActualTripStopInfo(StopNumber)"+ 
				
				") "
				;
		try {
			myStat.executeUpdate(TripOffering);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
