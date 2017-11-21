import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root" , "Password");
			Statement myStat= myConn.createStatement();
			System.out.println("Connected database successfully...");
			System.out.println("Creating tables...");
			createTables(myStat);


		}



		catch (Exception e ) {
			e.printStackTrace();
		}

	}



	public static void createTables(Statement myStat) {
		String Bus = 
				"CREATE TABLE Bus (" + 
				"    BusID VARCHAR(30)," + 
				"	Model VARCHAR(30)," + 
				"    Year VARCHAR(30)," + 
				"    PRIMARY KEY (BusID)" + 
				");" ;
			String Driver=	"CREATE TABLE Driver (" + 
				"    DriverName VARCHAR(30)," + 
				"    DriverTelephoneNumber VARCHAR(30)," + 
				"    PRIMARY KEY (DriverName)" + 
				");" ;
				
			String TripOffering=	"CREATE TABLE TripOffering (" + 
				"	TripNumber INTEGER," + 
				"    Date DATE," + 
				"    ScheduledStartTime VARCHAR(30)," + 
				"	ScheduledArrivalTime VARCHAR(30)," + 
				"    DriverName VARCHAR(30)," + 
				"    BusID VARCHAR(30)," + 
				"	PRIMARY KEY(Date, ScheduledStartTime, TripNumber)," + 
				"    FOREIGN KEY(BusID) REFERENCES Bus(BusID)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE," + 
				"	FOREIGN KEY(DriverName) REFERENCES Driver(DriverName)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE" + 
				");" ;
			
			String Trip =	"CREATE TABLE Trip (" + 
				"	TripNumber INTEGER PRIMARY KEY," + 
				"    StartLocationName VARCHAR(30)," + 
				"    DestinationName VARCHAR(30)" + 
				");" ;
String Stop="CREATE TABLE Stop (" + 
				"	StopNumber INTEGER PRIMARY KEY," + 
				"	StopAddress VARCHAR(30)" + 
				");" ;

String ActualTripStopInfo=				"CREATE TABLE ActualTripStopInfo (" + 
				"	TripNumber INTEGER," + 
				"    Date DATE," + 
				"    ScheduledStartTime VARCHAR(30)," + 
				"    StopNumber INTEGER," + 
				"    ScheduledArrivalTime VARCHAR(30)," + 
				"    ActualStartTime VARCHAR(30)," + 
				"    ActualArrivalTime VARCHAR(30)," + 
				"    NumberofPassengerIn VARCHAR(30)," + 
				"    NumberofPassengerOut VARCHAR(30)," + 
				"    PRIMARY KEY(StopNumber, Date, ScheduledStartTime, TripNumber)," + 
				"	FOREIGN KEY(Date, ScheduledStartTime, TripNumber) REFERENCES TripOffering(Date, ScheduledStartTime, TripNumber)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE," + 
				"	FOREIGN KEY(StopNumber) REFERENCES Stop(StopNumber)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE" + 
				");" ;

		String 	TripStopInfo=	"CREATE TABLE TripStopInfo (" + 
				"    TripNumber INTEGER," + 
				"    StopNumber INTEGER," + 
				"	SequenceNumber VARCHAR(30)," + 
				"    DrivingTime VARCHAR(30)," + 
				"    PRIMARY KEY(StopNumber, TripNumber)," + 
				"	FOREIGN KEY(StopNumber) REFERENCES Stop(StopNumber)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE," + 
				"	FOREIGN KEY(TripNumber) REFERENCES Trip(TripNumber)" + 
				"		ON DELETE CASCADE ON UPDATE CASCADE" + 
				");" ;
		try {
			myStat.executeUpdate(Bus);
			myStat.executeUpdate(Driver);
			myStat.executeUpdate(TripOffering);
			myStat.executeUpdate(Trip);
			myStat.executeUpdate(Stop);
			myStat.executeUpdate(ActualTripStopInfo);
			myStat.executeUpdate(TripStopInfo);
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
