package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted = 0;

        Map<Integer, String> prevMap = new HashMap<>();
        for (User user : previous) {
            prevMap.put(user.getId(), user.getName());
        }

        Map<Integer, String> currentMap = new HashMap<>();
        for (User user : current) {
            currentMap.put(user.getId(), user.getName());
        }

        for (Map.Entry<Integer, String> entry : currentMap.entrySet()) {
            int id = entry.getKey();
            String currentName = entry.getValue();
            if (!prevMap.containsKey(id)) {
                added++;
            }
            if (prevMap.get(id) != null && !prevMap.get(id).equals(currentName)) {
                changed++;
            }
        }

        for (User user : previous) {
            if (!currentMap.containsKey(user.getId())) {
                deleted++;
            }
        }
        return new Info(added, changed, deleted);
    }
}
