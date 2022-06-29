package de.iltisauge.gerritgroups;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupManager {

    private final GerritGroupsPlugin plugin;
    private final Map<GroupType, Group> groups = new HashMap<>();

    public GroupManager(GerritGroupsPlugin plugin) {
        this.plugin = plugin;
        final ResultSet result = plugin.getGroupDatabase().getResultAsync("SELECT * FROM `groups`;");
        try {
            while (result.next()) {
                final GroupType type = GroupType.valueOf(result.getString("type"));
                final String displayName = result.getString("displayName");
                final List<String> permissions = new ArrayList<>(); // Load permissions later
                final String tablistPrefix = result.getString("tablistPrefix");
                final String chatPrefix = result.getString("chatPrefix");
                final String entityPrefix = result.getString("entityPrefix");
                final Group group = new Group(type, displayName, permissions, tablistPrefix, chatPrefix, entityPrefix);
                synchronized (groups) {
                    groups.put(type, group);
                }
                System.out.println("Loaded group " + type.name());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
