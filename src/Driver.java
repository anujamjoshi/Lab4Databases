import java.sql.*;
import java.util.Scanner;

public class Driver {
	private static final String tripNumber = null;
	private static final String NumberofPassengerIn = null;
	static Connection myConn; 
	static Statement myStat; 
	static ResultSet  myRs;
	public static void createDatabase() {

		System.out.println("Connected database successfully...");
		System.out.println("Creating tables...");
		createTables(myStat);
		insertData(myStat);
	}
	public static void main(String[] args) {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4?allowMultiQueries=true", "root" , "Password");
			myStat= myConn.createStatement();
			myRs = null; 
			String input = "";
			Scanner sc = new Scanner(System.in);
			System.out.println("Do you want to initialize the database? Y/N");
			switch(sc.next()) {
			case "Y":
				createDatabase(); 
				break;
			default: 
				System.out.println("Already initalized! ");
			}
			while (!input.equals("9")) {
				System.out.println("Please choose inputs ");
				input = sc.next();
				switch(input) {
				case "1" :
					/*
					 * Display the schedule of all trips for a given StartLocationName
					 *  and Destination Name, and Date. In addition to these attributes, 
					 *  the schedule includes: Scheduled StartTime,  ScheduledArrivalTime , DriverID, and BusID.   					
					 */
					System.out.println("Insert Start Location Name");
					String StartLocationName = sc.next();
					System.out.println("Insert Destination Location Name");
					String DestinationName = sc.next();
					System.out.println("Insert Date in YYYY-MM-DD format");
					String Date = sc.next();
					myRs= myStat.executeQuery(displaySchedule(StartLocationName ,  DestinationName,  Date ) );
					while (myRs.next()) {
						//TODO: PROCESS RESULT 
					}
					break;
				case "2" :

					/*
					 * Edit the schedule i.e. edit the table of Trip Offering as follows:
					 * Delete a trip offering specified by Trip#, Date, and ScheduledStartTime;
					 * Add a set of trip offerings assuming the values of all attributes are given 
					 * (the software asks if you have more trips to enter) ;
					 * Change the driver for a given Trip offering (i.e given TripNumber, Date, ScheduledStartTime);
					 * Change the bus for a given Trip offering.
					 */
					System.out.println("To Edit the schedule Please select from one of the following: \n "
							+ "1 Delete a trip"
							+ "2 Add a new trip offerings"
							+ "3 change the driver for a given trip"
							+ "4 change the bus for a given trip offering "
							+ "5 done with all changes ");
					while(!input.equals("5")) {
						input = sc.next();
						String DriverName;
						String BusId;
						switch(input) {
						case "1":
							//							Trip#, Date, and ScheduledStartTime
							System.out.println("Insert Trip Number ");
							Integer TripNumber = Integer.parseInt(sc.next());
							System.out.println("Insert Date in YYYY-MM-DD format");
							Date = sc.next();
							System.out.println("Insert Scheduled Start Time ");
							String ScheduledStartTime = sc.next();

							int success = myStat.executeUpdate(deleteTrip(TripNumber, Date,  ScheduledStartTime)); 
							System.out.println(success);
							break;
						case "2" : 
							//	TripOffering ( TripNumber, Date, ScheduledStartTime, SecheduledArrivalTime,   
							//							DriverName, BusID)
							String hasMoreInput = "Y"; 
							while (hasMoreInput.equals("Y"))	{
								switch (hasMoreInput) {
								case "Y": 
									System.out.println("Insert Trip Number ");
									TripNumber = Integer.parseInt(sc.next());
									System.out.println("Insert Date in YYYY-MM-DD format");
									Date = sc.next();
									System.out.println("Insert Scheduled Start Time ");
									ScheduledStartTime = sc.next();
									System.out.println("Insert Scheduled Arrival Time ");
									String ScheduledArrivalTime = sc.next();
									System.out.println("Insert Driver Name ");
									DriverName = sc.next();
									System.out.println("Insert Bus ID  ");
									BusId = sc.next();
									success = myStat.executeUpdate(addTripOffering(TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusId));
									System.out.println(success);
									break;
								default : System.out.println( "Done");
								}
							}
							break; 
						case "3" : 
							System.out.println("Insert Trip Number ");
							TripNumber = Integer.parseInt(sc.next());
							System.out.println("Insert Driver Name ");
							DriverName = sc.next();
							success = myStat.executeUpdate(changeDriver(TripNumber, DriverName));
							System.out.println(success);
							break;
						case "4" : 
							System.out.println("Insert Trip Number ");
							TripNumber = Integer.parseInt(sc.next());
							System.out.println("Insert BusId   ");
							BusId = sc.next();
							success = myStat.executeUpdate(changeBus(TripNumber, BusId));
							System.out.println(success);

							break;
						case "5": 
							System.out.println("Already initalized! ");
							return; 
						default :
							System.out.println("choose one choice! ");
						}
					}
					break; 
				case  "3":
					/*
					 * Display the stops of a given trip ( i.e. the attributes of the table TripStopInfo).
					 */
					System.out.println("Insert Trip Number ");
					int TripNumber = Integer.parseInt(sc.next());
					myRs= myStat.executeQuery(displayStops(TripNumber ) );
					while (myRs.next()) {
						//TODO: PROCESS RESULT 
					}

					break;
				case "4" :
					/*
					 *  Display the weekly schedule of a given driver and date.					
					 */
					//	TODO: CHANGE TO DATE DATE TO ALLOW FOR WEEK CHECK? 
					System.out.println("Insert Driver Name ");
					String DriverName = sc.next();
					System.out.println("Insert Date in YYYY-MM-DD format");
					Date = sc.next();
					myRs= myStat.executeQuery(displayDriverSchedule(DriverName, Date ) );
					while (myRs.next()) {
						//TODO: PROCESS RESULT 
					}
					break; 
				case "5" :
					/*
					 * 5. Add a driver.					
					 */
					System.out.println("Insert Driver Name ");
					DriverName = sc.next();
					System.out.println("Insert Driver Phone Number  ");
					Integer phoneNumber = sc.nextInt();
					int success = myStat.executeUpdate(addNewDriver(DriverName, phoneNumber));
					System.out.println(success);
					break;
				case "6" :
					/*
					 * 6. Add a Bus.					
					 */
					System.out.println("Insert Bus  ID ");
					String  BusID = sc.next();
					System.out.println("Insert Bus  Model");
					String model = sc.next();
					System.out.println("Insert Bus  year ");
					String year= sc.next();  //TODO: Update year to be a integer 
					success = myStat.executeUpdate(addNewBus(BusID, model, year));
					System.out.println(success);
					break;
				case "7" :
					/*
					 * Delete a Bus.					
					 */
					System.out.println("Insert Bus  ID ");
					BusID = sc.next();

					success = myStat.executeUpdate(deleteBus(BusID));
					System.out.println(success);
					break;
				case "8" :
					/*
					 *  Record (insert) the actual data of a given trip offering specified by its key. 
					 *  The actual data include the attributes of the table ActualTripStopInfo
					 */
					//PRIMARY KEY (`StopNumber`,`Date`,`ScheduledStartTime`,`TripNumber`),
					System.out.println("Insert Trip Number ");
					TripNumber = Integer.parseInt(sc.next());
					System.out.println("Insert Date in YYYY-MM-DD format");
					Date = sc.next();
					System.out.println("Insert Scheduled Start Time ");
					String ScheduledStartTime = sc.next();
					System.out.println("Insert Stop Number  ");
					Integer stopNumber = sc.nextInt();
					System.out.println("Insert Scheduled Arrival Time ");
					String ScheduledArrivalTime = sc.next();
					System.out.println("Insert Actual Start Time ");
					String ActualStartTime = sc.next();
					System.out.println("Insert Actual Arrival Time ");
					String ActualArrivalTime = sc.next();
					System.out.println("Insert passengers in ");
					Integer NumberofPassengerIn = sc.nextInt();
					System.out.println("Insert passengers out ");
					Integer NumberofPassengerOut = sc.nextInt();
					success = myStat.executeUpdate(addActualTripInformation(TripNumber, Date, ScheduledStartTime, stopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberofPassengerIn, NumberofPassengerOut));
					System.out.println(success);




					break;
				default :
					input = "9";

				}



			}
			System.out.println("Thank you for using this program! ");

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

		String ActualTripStopInfo="CREATE TABLE ActualTripStopInfo (" + 
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

	/*
	 * Question  1 
	 */
	public static String displaySchedule(String StartLocationName, String DestinationName, String Date ) {
		return "SELECT  DISTINCT T.StartLocationName, T.DestinationName, TripOff.Date, TripOff.ScheduledStartTime, TripOff.ScheduledArrivalTime, TripOff.DriverName, TripOff.BusID\n" + 
				" FROM Trip T, TripOffering TripOff \n" + 
				"WHERE T.TripNumber = TripOff.TripNumber \n" + 
				"AND T.StartLocationName = "+ StartLocationName + 
				"AND T.DestinationName = "+DestinationName + 
				"AND TripOff.Date ="+Date+ ";";
	}

	/*
	 * Question 2 
	 */
	private static String changeBus(Integer tripNumber, String busId) {
		return "\n" + 
				"UPDATE  TripOffering\n" + 
				"SET DriverName = " + busId + " WHERE TripNumber = "+tripNumber +" ;  ";


	}
	private static String changeDriver(Integer tripNumber, String newDriver) {
		return "\n" + 
				"UPDATE  TripOffering\n" + 
				"SET DriverName = " + newDriver + " WHERE TripNumber = "+tripNumber +" ;  ";


	}
	private static String addTripOffering(Integer tripNumber, String date, String scheduledStartTime, String scheduledArrivalTime, String driverName, String busId) {
		return "INSERT INTO TripOffering  \" +\n" + 
				"\"VALUES("+tripNumber+","+ date+","+ scheduledStartTime+","+  scheduledArrivalTime+","+  driverName+","+  busId+ ")\";" ;

	}
	private static String deleteTrip(Integer tripNumber, String date, String scheduledStartTime) {
		return "DELETE FROM TripOffering  \n" + 
				"WHERE TripNumber ="+ tripNumber + 
				"AND Date =" + date +" \n" + 
				"And ScheduledStartTime "+ scheduledStartTime+ " \n;" ;
	}

	/*
	 * Question 3 
	 */
	private static String displayStops(int tripNumber) {
		return "SELECT * FROM TripStopInfo\n" + 
				"WHERE TripNumber = "+tripNumber+";";
	}

	/*
	 * Question 4 
	 */
	private static String displayDriverSchedule(String driverName, String date) {
		return " SELECT * FROM TripOffering\n" + 
				"WHERE DriverName = "+driverName+" AND Date = "+ date+ " ; " ;
		//		TODO: UPDATE DATE FOR ENTIRE WEEK? 
	}
	/*
	 * Question 5 : 
	 */
	private static String addNewDriver( String driverName, Integer phoneNumber) {
		return "INSERT INTO TripOffering  \" +\n" + 
				"\"VALUES("+driverName+","+ phoneNumber +")\";" ;

	}
	/*
	 * Question 6: 
	 */
	private static String addNewBus(String busID, String model, String year) {
		return "INSERT INTO TripOffering  \" +\n" + 
				"\"VALUES("+busID+","+ model + "," + year +")\";" ;
	}

	/*
	 * Question 7
	 */

	private static String deleteBus(String busID) {
		return "DELETE FROM Bus  \n" + 
				"WHERE BusID ="+ busID +";" ;
	}

	/*
	 * Question 8 
	 */
	private static String addActualTripInformation(int tripNumber, String date, String scheduledStartTime,
			Integer stopNumber, String scheduledArrivalTime, String actualStartTime, String actualArrivalTime,
			Integer numberofPassengerIn, Integer numberofPassengerOut) {
		// TODO Auto-generated method stub
		return "\n" + 
		"UPDATE ActualTripStopInfo "
		+ "SET ScheduledArrivalTime =" + scheduledArrivalTime + 
		"AND ActualStartTime = " + actualStartTime + 
		"AND ActualArrivalTime = " + actualArrivalTime+ 
		"AND NumberofPassengerIn = "+numberofPassengerIn+ 
		"AND NumberofPassengerOut = "+ numberofPassengerOut +
		" WHERE TripNumber = " +tripNumber+ 
		" AND Date = "+ date+
		"AND ScheduledStartTime=" + scheduledStartTime+ 
		" AND StopNumber=" + stopNumber + "; ";
	}
}
