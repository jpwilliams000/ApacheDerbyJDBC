import java.io.*; // Input / Output Package
import java.util.*; // allows for the use of the Scanner 
import java.sql.*;

public class Driver
{
   //global static fields to be used through out program
   protected static Statement stmt; //closed during program exit method
   protected static Connection conn;//closed during program exit method
   protected static Scanner keyboard = new Scanner(System.in); //Closed during program exit method. 
   
   /**
   *
   * main method
   *
   */
   public static void main(String[] args) throws FileNotFoundException{
      boolean goAgain;
      do{
         String userChoice = promptMenuChoices(); //get main menu choice, store into userChoice
         controllerLogic(userChoice); //Perform the logic of the userChoice from menu
         goAgain = promptGoAgain(); //promp go again
      } while(goAgain);
      exitProgram(); 

   } 
   
   /**
   *
   * promptMenuChoices() : Prompt the user for a choice of menu options and return choice.
   *
   * @return int : user choice of menu options.
   *
   */
   private static String promptMenuChoices(){
      String userChoiceString; 
      do{
         System.out.println("Select and option from the following: ");
         System.out.println("\t1. Build / Rebuild Arena Table");
         System.out.println("\t2. Display all of the Arena info in the Arena Table");
         System.out.println("\t3. Display all of the Arena info for Basketball Tenants");
         System.out.println("\t4. Display the average capacity for all Arenas");
         System.out.println("\t5. Display the Venue and Year Opened info for " +
                                "all Arenas Opened after 2000 in ascending order by year");
         System.out.println("\t6. Display the Total Attendance for Arenas in California");
         System.out.println("\t7. Exit");
         System.out.print("\n");           
         System.out.print("\tEnter your choice (1-7): ");
         userChoiceString = keyboard.nextLine();
      } while (!userChoiceString.matches("[1-7]"));
      System.out.print("\n");       
      return userChoiceString;           
   }
   
   /**
   *
   * controllerLogic() : Cordinates the flow of the program after user enters choice
   *                     from initial input.
   * @param String userChoice : The choice the user made from the main menu.
   *
   */
   private static void controllerLogic(String userChoice){
      if(userChoice.equals("1")) buildArenaDatabaseAndPopulateTables();
      if(userChoice.equals("2")) printArenasTable();
      if(userChoice.equals("3")) printBasketball();
      if(userChoice.equals("4")) printCapacityAverage();
      if(userChoice.equals("5")) printGreaterThanYear2000();
      if(userChoice.equals("6")) printTotalAttendanceCalifornia();
      if(userChoice.equals("7")) exitProgram();
   }
   
   /**
   *
   * promptGoAgain() : Prompt if the user would like to continue using the program.
   *
   * @return boolean goAgain : If anything other than 'y' or 'yes' is entered, true is returned.
   *                           If 'y' or 'yes' is entered, then false is returned.
   *
   */
   private static boolean promptGoAgain(){
      System.out.print("\nWould you like to make another selection? (y/yes to continue, anything else to exit): ");
      String input = keyboard.nextLine();
      System.out.print("\n");
      boolean goAgain = (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) ? true : false;
      return goAgain;
   }
   
   // Option 1
   
   /**
   *
   * buildArenaDatabaseAndPopulateTables() : This runs when user enters 1 on main screen.
   * 
   * Consist of four methods, see individually for more information.
   *
   */
   private static void buildArenaDatabaseAndPopulateTables(){
      createDatabaseConnection();
      dropArenaTable();
      createTableArenas();
      populateTableFromTxtFile();
      
   }
   
   /**
   *
   * createDatabaseConnection() : Creates database connection. And statement.
   *                              If the database does not exist, the 
   *                              attribute of create=true, will create one.
   *
   */
   private static void createDatabaseConnection(){
      try{
         conn = DriverManager.getConnection("jdbc:derby:ArenaDB;create=true");
         stmt = conn.createStatement();
      } catch (Exception e) {
         System.out.println(e);
      } 
   }
   /**
   *
   * dropArenaTable() : If the table exist, the table is dropped.
   * 
   *
   */
   private static void dropArenaTable(){
      System.out.println("Checking for existing tables.");
      try {
         stmt.execute("DROP TABLE Arenas");
         System.out.println("Arenas Table Dropped.");
      } catch (Exception e) {
         System.out.println("Arena Table Not Found.");
         }
   }
   
   /**
   *
   * createTableArenas() : Try catch to create arena table.
   * 
   *
   */
   private static void createTableArenas(){
      try{
         String sql = "CREATE TABLE Arenas" +
                        "(Venue CHAR(45) NOT NULL PRIMARY KEY, " +
                        "City CHAR(15)," +
                        "State CHAR(15)," +
                        "MaxCapacity INT," +
                        "YearOpened INT," +
                        "TeamName CHAR(30)," +
                        "Sport CHAR(10)," + 
                        "League CHAR(15)," +
                        "Attendance INT)";
         stmt.execute(sql);
         System.out.println("Arenas Table Created.");
      } catch (Exception e) {
         System.out.println(e);
      }
   }
   
   /**
   *
   * insertIntoArenasTable() : Insert the data from Arena object into Arena table row.
   * 
   * @param Arena a : Arena object containing all the fields from txt file
   *
   */
   private static void insertIntoArenasTable(Arena a){
         try{
            stmt.execute("INSERT INTO Arenas VALUES ( "
                         + "'"+ a.getVenueName() + "',"
                         + "'"+ a.getCity() + "',"
                         + "'"+ a.getState() + "',"
                         + a.getMaxCapacity() + ","
                         + a.getYearOpened() + ","
                         + "'"+ a.getTenant().getTeamName() + "',"
                         + "'"+ a.getTenant().getSport() + "',"
                         + "'"+ a.getTenant().getLeague() + "'," 
                         + a.getAttendance() + ")");
         } catch (Exception e){
            System.out.println(e);
         }
   }
   
   // Option 2
   /**
   *
   * printArenasTable() : Query the database and print the entire Arena table.
   *
   */
   private static void printArenasTable(){
      System.out.println("All Arena Info Query: \n");
      try{
         String sqlStatement = "SELECT * FROM Arenas";
         ResultSet result = stmt.executeQuery(sqlStatement);
         while(result.next()) {
            System.out.printf( "%30s %10s %10s %,-6d %-5d %10s %10s %10s %,10d %n", 
                                result.getString("Venue"), 
                                result.getString("City"),
                                result.getString("State"),
                                result.getInt("MaxCapacity"),
                                result.getInt("YearOpened"), 
                                result.getString("TeamName"),
                                result.getString("Sport"),
                                result.getString("League"),
                                result.getInt("Attendance") );
         }
      } catch (Exception e) {
         System.out.println("\n\t ERROR ID: 10-T - Please ensure the table exist.\n");
      }      
   }
   // Option 3
   /**
   *
   * printBasketball() : Query the database and print all the Venues that host sport Basketball.
   *
   */
   private static void printBasketball(){
      System.out.println("Basketball Arena Query: \n");
      try{
         String sqlStatement = "SELECT * FROM Arenas WHERE Sport = 'Basketball'";
         ResultSet result = stmt.executeQuery(sqlStatement);
         while(result.next()) {
            System.out.printf( "%30s %10s %10s %,-6d %-5d %10s %10s %10s %,10d %n", 
                                result.getString("Venue"), 
                                result.getString("City"),
                                result.getString("State"),
                                result.getInt("MaxCapacity"),
                                result.getInt("YearOpened"), 
                                result.getString("TeamName"),
                                result.getString("Sport"),
                                result.getString("League"),
                                result.getInt("Attendance") );
         }
      } catch (Exception e) {
         System.out.println("\n\t ERROR ID: 10-T - Please ensure the table exist.\n");
      }      
   }
   // Option 4
   /**
   *
   * printArenasTable() : Query the database and print the average MaxCapacity.
   * 
   *
   */
   private static void printCapacityAverage(){
      try{
         String sqlStatement = "SELECT AVG(MaxCapacity) FROM Arenas";
         ResultSet result = stmt.executeQuery(sqlStatement);
         while(result.next()) {
            System.out.printf("%nAverage Arena Capacity: %,6d %n%n", result.getInt(1));
         }
      } catch (Exception e) {
         System.out.println("\n\t ERROR ID: 10-T - Please ensure the table exist.\n");
      }      
   }
   // Option 5
   /**
   *
   * printGreaterThanYear2000() : Query the database and print all venues with Year newer than 2000.
   * 
   *
   */
   private static void printGreaterThanYear2000(){
      try{
         String sqlStatement = "SELECT Venue, YearOpened FROM Arenas WHERE YearOpened > 2000 ORDER BY YearOpened";
         ResultSet result = stmt.executeQuery(sqlStatement);
         while(result.next()) {
            System.out.printf("%30s %6d %n", result.getString(1), result.getInt(2));
         }
      } catch (Exception e) {
         System.out.println("\n\t ERROR ID: 10-T - Please ensure the table exist.\n");
      }      
   }
   // Option 6
   /**
   *
   * printTotalAttendanceCalifornia() : 
   *               Query the database and print summation of attendance in California.
   * 
   *
   */
   private static void printTotalAttendanceCalifornia(){
      try{
         String sqlStatement = "SELECT SUM(Attendance) FROM Arenas WHERE State = 'California'";
         ResultSet result = stmt.executeQuery(sqlStatement);
         while(result.next()) {
            System.out.printf("%nCalifornia Arena Attendance: %,6d %n%n", result.getInt(1));
         }
      } catch (Exception e) {
         System.out.println("\n\t ERROR ID: 10-T - Please ensure the table exist.\n");
      }      
   }
   // Option 7
   /**
   *
   * exitProgram() : Exit the program after trying to close the global connections.
   *
   */
   private static void exitProgram(){
      try {
         stmt.close();//close the statment if it exists
         conn.close();//close the connection if it exist
         keyboard.close();
      } catch (Exception e) {
         } finally {
            System.out.println("Exiting program");
            System.exit(0); 
            }
   }
   /**
   *
   *
   * populateTableFromTxtFile() : Populate the Arena table with data from the text file.
   *
   *
   */
   private static void populateTableFromTxtFile(){
      try{
         //create File object with string name as argument
         File file = new File("IndoorArenasTwo.txt"); 
         Scanner input = new Scanner(file);
         
         // Variables to hold the data read from the file
         String venueName, city, state, teamName, sport, league;
         String maxCapacityString, yearOpenedString, attendanceString;
         int maxCapacity, yearOpened, attendance;
         
         // use a while loop to read data from the file
         while(input.hasNext()){ // hasNext() determines if there is more to read from the file
            venueName = input.nextLine();
            city = input.nextLine();
            state = input.nextLine();
            maxCapacityString = input.nextLine();
        	   maxCapacity = Integer.parseInt(maxCapacityString); // converts maxCapacityString to an integer
            yearOpenedString = input.nextLine();
        	   yearOpened = Integer.parseInt(yearOpenedString); // converts yearOpenedString to an integer
   	      teamName = input.nextLine();
            sport = input.nextLine();
            league = input.nextLine();
            attendanceString = input.nextLine();
            attendance = Integer.parseInt(attendanceString);// converts attendanceString to an integer
            
            if(input.hasNext()) { //allows for text file to end without requiring empty lines
           	 input.nextLine(); // reads the blank space between the entries
            } 
            
            // create an instance of the Arena class
            Arena temp = new Arena(venueName, city, state, maxCapacity, yearOpened, teamName, 
                                    sport, league, attendance);
            
            //Insert the data into Arenas table.
            insertIntoArenasTable(temp); 
         } // end while loop
         
         input.close(); //close Scanner object
      }catch (Exception e) {
         System.out.println(e);
         }
   
   }


} // end FileReader class