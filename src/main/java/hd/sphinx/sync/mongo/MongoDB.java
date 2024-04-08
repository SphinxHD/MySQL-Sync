package hd.sphinx.sync.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hd.sphinx.sync.Main;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.Bukkit;

public class MongoDB {

    public static String uri = ConfigManager.getString("mongodb.uri");
    public static String database = ConfigManager.getString("mongodb.database");
    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;
    public static MongoCollection mongoCollection;

    // Connect to Database
    public static void connectMongoDB() {
        if (!isConnected()) {
            try {
                mongoClient = MongoClients.create(uri);
                mongoDatabase = mongoClient.getDatabase(database);
                mongoCollection = mongoDatabase.getCollection("playerdata");
                Main.logger.info("§aConnected to the MongoDB");
            } catch (Exception exception) {
                Main.logger.severe("No valid MongoDB Credentials is set in Config!\n Disabling Plugin!");
                Bukkit.getPluginManager().disablePlugin(Main.main);
            }
        }
    }

    // Disconnect from Database
    public static void disconnectMongoDB() {
        if (isConnected()) {
            try {
                mongoClient.close();
                mongoClient = null;
                mongoDatabase = null;
                mongoCollection = null;
                Main.logger.info("§cDisconnected from the MongoDB");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    // If the Server is connected to the MySQL
    public static boolean isConnected() {
        return mongoClient != null;
    }

    // Get the Connection
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    // Get the Database
    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    // Get the Collection
    public static MongoCollection getMongoCollection() {
        return mongoCollection;
    }
}
