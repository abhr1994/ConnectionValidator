package validator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class OracleValidator {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            if (args.length != 3) {
                System.out.println("Invalid number of arguments: Must provide 3 arguments in the format: jdbc_url username password");
                System.exit(0);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String connurl = args[0];
            System.out.println("Connecting to " + connurl);
            String user = args[1];
            String pass = args[2];
            String sqlQuery = "select sysdate from dual";
            Connection con = DriverManager.getConnection (connurl, user, pass);
            if (con != null) {
                System.out.println("Established successful connection");
                con.setAutoCommit(false);
                Statement statement = con.createStatement();
                DatabaseMetaData dm = con.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    System.out.println("Result of SQL query: [{}]" + resultSet.getString(1));
                }
                statement.close();
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


