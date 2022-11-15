package validator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SnowflakeValidator {
    public static void main(String[] args) throws Exception
    {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments: Must provide 4 arguments in the format: jdbc_url username password account_name");
            System.exit(0);
        }
        // get connection
        System.out.println("Create JDBC connection");
        String connectStr = args[0];
        System.out.println("Connecting to " + connectStr);
        Properties properties = new Properties();
        properties.put("user", args[1]);
        properties.put("password", args[2]);
        properties.put("account", args[3]);
        // properties.put("db", "");       // replace "" with target database name
        // properties.put("schema", "");   // replace "" with target schema name
        Connection connection = getConnection(connectStr, properties);
        if (connection != null) {
            System.out.println("Established successful connection");
            connection.close();
            System.out.println("Disconnected");
        }

    }
    private static Connection getConnection(String connectStr, Properties properties)
            throws SQLException
    {
        try
        {
            Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
        }
        catch (ClassNotFoundException ex)
        {
            System.err.println("Driver not found");
        }
        return DriverManager.getConnection(connectStr, properties);
    }
}

