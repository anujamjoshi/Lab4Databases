import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4?allowMultiQueries=true", "root" , "Password");
			Statement myStat= myConn.createStatement();
			System.out.println("Connected database successfully...");
			System.out.println("Creating tables...");
			createTables(myStat);
			insertData(myStat);


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

	public static void insertData(Statement myStat) {
		String baseBus = 
				"INSERT INTO Bus VALUES ('1', 'City', '2007');\n" +
				"INSERT INTO Bus VALUES ('2', 'City', '2007');\n" + 
				"INSERT INTO Bus VALUES ('3', 'Tour', '2008');\n" + 
				"INSERT INTO Bus VALUES ('4', 'Tour', '2008');\n" + 
				"INSERT INTO Bus VALUES ('5', 'Commuter', '2008');\n" + 
				"INSERT INTO Bus VALUES ('6', 'Commuter', '2008');\n" + 
				"INSERT INTO Bus VALUES ('7', 'City', '2009');\n" + 
				"INSERT INTO Bus VALUES ('8', 'City', '2009');";
		
		String baseDriver = 
				"INSERT INTO Driver VALUES ('Michael Scott', '10005');\n" + 
				"INSERT INTO Driver VALUES ('Dwight Schrute', '10006');\n" + 
				"INSERT INTO Driver VALUES ('Jim Halpert', '20004');\n" + 
				"INSERT INTO Driver VALUES ('Pam Beesly', '20002');\n" + 
				"INSERT INTO Driver VALUES ('Ryan Howard', '30001');\n" + 
				"INSERT INTO Driver VALUES ('Andy Bernard', '30002');\n" + 
				"INSERT INTO Driver VALUES ('Jan Levinson', '50003');";
		
		try {
			myStat.executeUpdate(baseBus);
			myStat.executeUpdate(baseDriver);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
