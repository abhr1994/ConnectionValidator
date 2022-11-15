package validator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TeradataValidator {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            String connurl = "jdbc:teradata://3.81.12.79/DATABASE=dbc,DBS_PORT=1025";
            Class.forName("com.teradata.jdbc.TeraDriver");
            System.out.println("Connecting to " + connurl);
            Connection con = DriverManager.getConnection (connurl, "dbc", "");
            System.out.println("Established successful connection");
            con.close();
            System.out.println("Disconnected");
        }
        catch (SQLException ex) {
            // A SQLException was generated.  Catch it and display
            // the error information.
            // Note that there could be multiple error objects chained
            // together.
            System.out.println();
            System.out.println("*** SQLException caught ***");
            while (ex != null)
            {
                System.out.println(" Error code: " + ex.getErrorCode());
                System.out.println(" SQL State: " + ex.getSQLState());
                System.out.println(" Message: " + ex.getMessage());
                ex.printStackTrace();
                System.out.println();
                ex = ex.getNextException();
            }
            throw new IllegalStateException ("Test Connection failed.") ;
        }
    }
}


