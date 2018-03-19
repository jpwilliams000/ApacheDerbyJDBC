// Tenant class is going to be the aggregate field of the Arena class

public class Tenant
{
   // fields
      private String teamName;
      private String sport;
      private String league;
   
   // constructors
      public Tenant()
      {
         teamName = "";
         sport = "";
         league = "";
      }
      
      public Tenant(String t, String s, String l)
      {
         teamName = t;
         sport = s;
         league = l;
      }
      
      //copy constructor
      public Tenant(Tenant e)
      {
         this(); // calls the default constructor to create an instance of the object
         this.teamName = e.getTeamName(); // calls the getTeamName method to get the value of the teamName field
         this.sport = e.getSport(); // calls the getSport method to get the value of the sport field
         this.league = e.getLeague(); // calls the getLeague method to get the value of the league field
      }
   
   // accessors
      public String getTeamName()
      {
         return teamName;
      }
      
      public String getSport()
      {
         return sport;
      }
      
      public String getLeague()
      {
         return league;
      }
      
   // mutators
      public void setTeamName(String t)
      {
         teamName = t;
      }
      
      public void setSport(String s)
      {
         sport = s;
      }
      
      public void setLeague(String l)
      {
         league = l;
      }
      
   // toString method
       public String toString()
      {
         return "Team Name:    " + getTeamName() + "\n" +
        		"Sport:        " + getSport() + "\n" +
        		"League:       " + getLeague();
      }
}