package validator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerValidator {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            if (args.length != 3) {
                System.out.println("Invalid number of arguments: Must provide 3 arguments in the format: jdbc_url username password");
                System.exit(0);
            }
            String dbURL = args[0];
            String user = args[1];
            String pass = args[2];
            System.out.println("Connecting to " + dbURL);
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Established successful connection");
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Disconnected");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}