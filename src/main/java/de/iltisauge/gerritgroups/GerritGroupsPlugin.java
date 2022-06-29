package de.iltisauge.gerritgroups;

import com.google.common.io.Files;
import com.mongodb.BasicDBObject;
import de.iltisauge.databaseapi.Credential;
import de.iltisauge.databaseapi.PrimaryKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import de.iltisauge.databaseapi.databases.MySQLDatabase;

public final class GerritGroupsPlugin extends JavaPlugin {

    private MySQLDatabase groupDatabase;
    private GroupManager groupManager;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "The plugin is being loaded...");
        getDataFolder().mkdirs();
        final Credential credential = getDBCredential();
        if (credential == null) {
            getLogger().log(Level.SEVERE, "Could not load database credentials from database.json!");
            return;
        }
        groupDatabase = new MySQLDatabase(credential);
        groupDatabase.connect();
        groupDatabase.createTable("groups", new PrimaryKey("type"),
                "type char(36)",
                "displayName text",
                "tablistPrefix text",
                "chatPrefix text",
                "entityPrefix char(16)");
        groupManager = new GroupManager(this);
        getLogger().log(Level.INFO, "The plugin has been loaded!");
    }

    public Credential getDBCredential() {
        try {
            final File file = new File(getDataFolder(), "database.json");
            if (!file.exists()) {
                file.createNewFile();
                return null;
            }
            final String content = Files.toString(file, Charset.forName("UTF-8"));
            final BasicDBObject object = BasicDBObject.parse(content);
            final String hostname = object.getString("hostname");
            final int port = object.getInt("port");
            final String database = object.getString("database");
            final String username = object.getString("username");
            final String password = object.getString("password");
            return new Credential(hostname, port, database, username, password);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public MySQLDatabase getGroupDatabase() {
        return groupDatabase;
    }

    @Override
    public void onDisable() {
    }
}
