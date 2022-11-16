package validator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.cli.*;

public class SnowflakeValidator {
    public static void main(String[] args) throws Exception
    {
        Options options = new Options();
        Option jdbc_url = new Option("jdbc_url","jdbc_url", true, "Pass the Snowflake jdbc url to connect");
        jdbc_url.setRequired(true);
        options.addOption(jdbc_url);
        Option username = new Option("username", "username", true, "Pass the Snowflake username ");
        username.setRequired(true);
        options.addOption(username);
        Option password = new Option("password","password", true, "Pass the Snowflake password");
        password.setRequired(true);
        options.addOption(password);
        Option account = new Option("account","account", true, "Pass the Snowflake account name");
        account.setRequired(true);
        options.addOption(account);
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
        String connectStr = cmd.getOptionValue("jdbc_url");
        String user = cmd.getOptionValue("username");
        String pass = cmd.getOptionValue("password");
        String account_name = cmd.getOptionValue("account");
        // get connection
        System.out.println("Create JDBC connection");
        System.out.println("Connecting to " + connectStr);
        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", pass);
        properties.put("account", account_name);
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

