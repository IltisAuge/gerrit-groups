package de.iltisauge.gerritgroups;

import java.util.List;

public class Group {

    private final GroupType type;
    private final String displayName;
    private final List<String> permissions;
    private final String tablistPrefix;
    private final String chatPrefix;
    private final String entityPrefix;

    public Group(GroupType type, String displayName, List<String> permissions, String tablistPrefix, String chatPrefix, String entityPrefix) {
        this.type = type;
        this.displayName = displayName;
        this.permissions = permissions;
        this.tablistPrefix = tablistPrefix;
        this.chatPrefix = chatPrefix;
        this.entityPrefix = entityPrefix;
    }
}
