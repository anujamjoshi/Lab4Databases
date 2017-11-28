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
		
		String baseTripOffering = 
				"INSERT INTO TripOffering VALUES ('1001', '2017-11-01', '08:00AM', '10:00AM', 'Andy Bernard', '1');\n" + 
				"INSERT INTO TripOffering VALUES ('1002', '2017-11-01', '09:00AM', '11:00AM', 'Dwight Schrute', '2');\n" + 
				"INSERT INTO TripOffering VALUES ('1003', '2017-11-01', '12:00PM', '02:00PM', 'Andy Bernard', '1');\n" + 
				"INSERT INTO TripOffering VALUES ('1004', '2017-11-01', '01:00PM', '03:00PM', 'Dwight Schrute', '2');\n" + 
				"INSERT INTO TripOffering VALUES ('2001', '2017-11-02', '07:00AM', '10:00AM', 'Jan Levinson', '5');\n" + 
				"INSERT INTO TripOffering VALUES ('2002', '2017-11-02', '07:30AM', '10:30AM', 'Jim Halpert', '6');\n" + 
				"INSERT INTO TripOffering VALUES ('2003', '2017-11-02', '04:00PM', '07:00PM', 'Jan Levinson', '5');\n" + 
				"INSERT INTO TripOffering VALUES ('2004', '2017-11-02', '04:30PM', '07:30PM', 'Jim Halpert', '6');\n" + 
				"INSERT INTO TripOffering VALUES ('3001', '2017-11-03', '10:00AM', '01:00PM', 'Michael Scott', '3');\n" + 
				"INSERT INTO TripOffering VALUES ('3002', '2017-11-03', '02:00PM', '04:00PM', 'Pam Beesly', '4');\n" + 
				"INSERT INTO TripOffering VALUES ('3003', '2017-11-03', '03:00PM', '06:00PM', 'Michael Scott', '3');\n" + 
				"INSERT INTO TripOffering VALUES ('3004', '2017-11-04', '11:00AM', '02:00PM', 'Pam Beesly', '4');\n" + 
				"INSERT INTO TripOffering VALUES ('3005', '2017-11-04', '04:00PM', '07:00PM', 'Ryan Howard', '3');\n" + 
				"INSERT INTO TripOffering VALUES ('4001', '2017-11-04', '09:00AM', '11:00AM', 'Andy Bernard', '7');\n" + 
				"INSERT INTO TripOffering VALUES ('4002', '2017-11-04', '12:00PM', '01:00PM', 'Dwight Schrute', '8');\n";
		
		String baseTrip = 
				"INSERT INTO Trip VALUES ('1001', '110 Wyoming Ave', 'Blakely St / Grove St');\n" + 
				"INSERT INTO Trip VALUES ('1002', 'Blakely St / Drinker St', 'Price Chopper Shopping Ctr');\n" + 
				"INSERT INTO Trip VALUES ('1003', 'Center St / George St', 'Delaware St / Lackawanna Ave');\n" + 
				"INSERT INTO Trip VALUES ('1004', 'Delaware St / Lackawanna Ave', 'Jessup Sports Complex');\n" + 
				"INSERT INTO Trip VALUES ('2001', 'Lackawanna Transit Ctr', 'N Everett Ave');\n" + 
				"INSERT INTO Trip VALUES ('2002', 'Lackawanna Transit Ctr', 'Viewmont Mall');\n" + 
				"INSERT INTO Trip VALUES ('2003', 'N Everett Ave', 'Lackawanna Transit Ctr');\n" + 
				"INSERT INTO Trip VALUES ('2004', 'Viewmont Mall', 'Lackawanna Transit Ctr');\n" + 
				"INSERT INTO Trip VALUES ('3001', 'Scranton Martz Terminal', '30th St Philadelphia Station');\n" + 
				"INSERT INTO Trip VALUES ('3002', 'Wilkes-Barre Martz Terminal', 'White Haven Turnpike Exit');\n" + 
				"INSERT INTO Trip VALUES ('3003', '30th St Philadelphia Station', 'Scranton Martz Terminal');\n" + 
				"INSERT INTO Trip VALUES ('3004', 'White Haven Turnpike Exit', 'Wilkes-Barre Martz Terminal');\n" + 
				"INSERT INTO Trip VALUES ('3005', 'Scranton Martz Terminal', '30th St Philadelphia Station');\n" + 
				"INSERT INTO Trip VALUES ('4001', 'Lafayette St / N Van Buren Ave', 'Jackson St / Keyser Ave');\n" + 
				"INSERT INTO Trip VALUES ('4002', 'Lackawanna Transit Ctr', 'Simplex Dr / N South Rd');\n";
		
		String baseStop = 
				"INSERT INTO Stop VALUES ('1', '110 Wyoming Ave');\n" + 
				"INSERT INTO Stop VALUES ('2', 'Blakely St / Grove St');\n" + 
				"INSERT INTO Stop VALUES ('3', 'Blakely St / Drinker St');\n" + 
				"INSERT INTO Stop VALUES ('4', 'Price Chopper Shopping Ctr');\n" + 
				"INSERT INTO Stop VALUES ('5', 'Center St / George St');\n" + 
				"INSERT INTO Stop VALUES ('6', 'Delaware St / Lackawanna Ave');\n" + 
				"INSERT INTO Stop VALUES ('7', 'Jessup Sports Complex');\n" + 
				"INSERT INTO Stop VALUES ('8', 'Lackawanna Transit Ctr');\n" + 
				"INSERT INTO Stop VALUES ('9', 'N Everett Ave');\n" + 
				"INSERT INTO Stop VALUES ('10', 'Viewmont Mall');\n" + 
				"INSERT INTO Stop VALUES ('11', 'Scranton Martz Terminal');\n" + 
				"INSERT INTO Stop VALUES ('12', '30th St Philadelphia Station');\n" + 
				"INSERT INTO Stop VALUES ('13', 'Wilkes-Barre Martz Terminal');\n" + 
				"INSERT INTO Stop VALUES ('14', 'White Haven Turnpike Exit');\n" + 
				"INSERT INTO Stop VALUES ('15', 'Lafayette St / N Van Buren Ave');\n" + 
				"INSERT INTO Stop VALUES ('16', 'Jackson St / Keyser Ave');\n" + 
				"INSERT INTO Stop VALUES ('17', 'Simplex Dr / N South Rd');\n";

		
		try {
			myStat.executeUpdate(baseBus);
			myStat.executeUpdate(baseDriver);
			myStat.executeUpdate(baseTripOffering);
			myStat.executeUpdate(baseTrip);
			myStat.executeUpdate(baseStop);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
