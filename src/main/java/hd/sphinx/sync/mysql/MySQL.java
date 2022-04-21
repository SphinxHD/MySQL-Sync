package hd.sphinx.sync.mysql;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.util.ConfigManager;

import java.sql.*;

public class MySQL {

    public static String host = ConfigManager.getString("mysql.host");
    public static String port = ConfigManager.getString("mysql.port");
    public static String database = ConfigManager.getString("mysql.database");
    public static String username = ConfigManager.getString("mysql.username");
    public static String password = ConfigManager.getString("mysql.password");
    public static Connection con;

    // Connect to Database
    public static void connectMySQL() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                Main.main.getLogger().info("§aConnected to the MySQL");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Disconnect from Database
    public static void disconnectMySQL() {
        if (isConnected()) {
            try {
                con.close();
                Main.main.getLogger().info("§cDisconnected from the MySQL");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Reconnect to Database
    public static void reconnectMySQL() {
        try {
            con.close();
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Setting up the Database
    public static void registerMySQL() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SHOW TABLES LIKE 'playerdata'");
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            PreparedStatement ps1 = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerdata (player_uuid VARCHAR(100) NOT NULL, player_name VARCHAR(16), inventory TEXT, gamemode VARCHAR(18), health INT(10), food INT(10), enderchest TEXT, exp INT(255), last_joined VARCHAR(255), PRIMARY KEY (player_uuid))");
            ps1.executeUpdate();
        }
    }

    // If the Server is connected to the MySQL
    public static boolean isConnected() {
        if (con == null) {
            return false;
        }
        try {
            if (con.isClosed()) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    // Get the Connection
    public static Connection getConnection() {
        return con;
    }
}
