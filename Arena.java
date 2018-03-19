// Arena class that will contain attributes for an Arena

public class Arena
{
   // fields
      private String venueName; // name of the venue/arena
      private String city;
      private String state;// city of the event
      private int maxCapacity;// maxCapacity of the event
      private int yearOpened;// Year Opened of the arena
      // aggregate field to hold the event and the seating type
      private Tenant team; // creates a "has-a" relationship between the Arena and Tenant classes
      private int attendance;
   
   // constructors
      // no-arg constructor
      public Arena()
      {
         venueName = "";
         city = "";
         state = "";
         maxCapacity = 0;
         yearOpened = 0;
         team = new Tenant(); // calls the no-arg constructor of the Tenant class
         attendance = 0;
      }
      
      //constructor with arguments
      public Arena(String v, String c, String s, int m, int y, String teamName, String sport, String league, int a)
      {
         this(); // calls the default no-arg constructor
         setVenueName(v);
         setCity(c);
         setState(s);
         setMaxCapacity(m);
         setYearOpened(y);
         setTenant(teamName, sport, league);
         setAttendance(a);
      }
      
      // copy constructor
      public Arena(Arena v)
      {
         /** Creates a new instance of the Arena class by 
         *   copying the values of the fields from the argument.
         */
         this(v.getVenueName(), 
        	     v.getCity(), 
        	     v.getState(),
              v.getMaxCapacity(), 
              v.getYearOpened(), 
              v.getTenant().getTeamName(), 
              v.getTenant().getSport(),
              v.getTenant().getLeague(),
              v.getAttendance()
             );
      }
   
   // accessors
      public String getVenueName()
      {
         return venueName;
      }
      
      public String getCity()
      {
         return city;
      }
      
      public String getState()
      {
         return state;
      }
      
      public int getMaxCapacity()
      {
         return maxCapacity;
      }
      
      public int getYearOpened()
      {
         return yearOpened;
      }
      
      public int getAttendance()
      {
         return attendance;
      }
      
      // accessor for aggregate field
      public Tenant getTenant()
      {
         return new Tenant(team); //***Must use copy constructor when reference private field objects
      }
   
   // mutators
      public void setVenueName(String v)
      {
         venueName = v;
      }
      
      public void setCity(String c)
      {
         city = c;
      }
      
      public void setState(String s)
      {
         state = s;
      }
      
      public void setMaxCapacity(int m)
      {
         maxCapacity = m;
      }
      
      public void setYearOpened(int y)
      {
         yearOpened = y;
      }
      
      public void setAttendance(int a)
      {
         attendance = a;
      }
      
      // mutator to set the attributes of the aggregate field
      public void setTenant(String t, String s, String l)
      {
         team.setTeamName(t); // calls the Tenant mutator to set the teamName field
         team.setSport(s); // calls the Tenant mutator to set the sport field
         team.setLeague(l); // calls the Tenant mutator to set the league field
      }
   
   // other methods
      // toString method
      public String toString()
      {
         return "Venue:        " + getVenueName() + "\n" +
        		"Location:     " + getCity() + ", " + getState() + "\n" +
        		"Capacity:     " + getMaxCapacity() + "\n" +
        		"Year Opened:  " + getYearOpened() + 
        		"\n" + team.toString();
      }
   

} // end Arena class