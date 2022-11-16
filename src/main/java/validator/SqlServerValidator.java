package validator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.cli.*;

public class SqlServerValidator {
    public static void main(String[] args) {
        Options options = new Options();
        Option jdbc_url = new Option("jdbc_url","jdbc_url", true, "Pass the SQLServer jdbc url to connect");
        jdbc_url.setRequired(true);
        options.addOption(jdbc_url);
        Option username = new Option("username", "username", true, "Pass the SQLServer username ");
        username.setRequired(true);
        options.addOption(username);
        Option password = new Option("password","password", true, "Pass the SQLServer password");
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
        Connection conn = null;
        try {
            String dbURL = cmd.getOptionValue("jdbc_url");
            String user = cmd.getOptionValue("username");
            String pass = cmd.getOptionValue("password");
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