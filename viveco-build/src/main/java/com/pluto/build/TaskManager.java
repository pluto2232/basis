package com.pluto.build;

import java.util.*;

public class TaskManager {
    private static final Map<Long, ConstructionTask> taskMap = new HashMap<>();

    public static void addTask(ConstructionTask task) {
        taskMap.put(task.getTaskId(), task);
    }

    public static ConstructionTask getTaskById(Long taskId) {
        return taskMap.get(taskId);
    }

    public static List<ConstructionTask> getNextTasks(ConstructionTask task) {
        List<ConstructionTask> nextTasks = new ArrayList<>();
        for (ConstructionTask t : taskMap.values()) {
            if (t.getPrevSteps().contains(task.getStepId()) && !t.getStatus().equals("完成")) {
                nextTasks.add(t);
            }
        }
        return nextTasks;
    }

    public static Collection<ConstructionTask> getAllTasks() {
        return taskMap.values();
    }
}

