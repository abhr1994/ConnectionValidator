package validator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.cli.*;
public class TeradataValidator {
    public static void main(String[] args) throws ClassNotFoundException {
        Options options = new Options();
        Option jdbc_url = new Option("jdbc_url","jdbc_url", true, "Pass the Teradata jdbc url to connect");
        jdbc_url.setRequired(true);
        options.addOption(jdbc_url);
        Option username = new Option("username", "username", true, "Pass the Teradata username ");
        username.setRequired(true);
        options.addOption(username);
        Option password = new Option("password","password", true, "Pass the Teradata password");
        password.setRequired(true);
        options.addOption(password);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("iwx-connection-validator", options);
            System.exit(1);
        }
        try {
            String connurl = cmd.getOptionValue("jdbc_url");
            String user = cmd.getOptionValue("username");
            String passwd = cmd.getOptionValue("password");
            Class.forName("com.teradata.jdbc.TeraDriver");
            System.out.println("Connecting to " + connurl);
            Connection con = DriverManager.getConnection (connurl, user, passwd);
            if (con != null) {
                System.out.println("Established successful connection");
                DatabaseMetaData dm = con.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                con.close();
            }
            System.out.println("Disconnected");
        }
        catch (SQLException ex) {
            // A SQLException was generated.  Catch it and display
            // the error information.
            // Note that there could be multiple error objects chained
            // together.
            SQLException throwables = ex;
            System.out.println();
            System.out.println("*** SQLException caught ***");
            while (throwables != null)
            {
                System.out.println(" Error code: " + throwables.getErrorCode());
                System.out.println(" SQL State: " + throwables.getSQLState());
                System.out.println(" Message: " + throwables.getMessage());
                throwables.printStackTrace();
                System.out.println();
                throwables = throwables.getNextException();
            }
            throw new IllegalStateException ("Test Connection failed.") ;
        }
    }
}


