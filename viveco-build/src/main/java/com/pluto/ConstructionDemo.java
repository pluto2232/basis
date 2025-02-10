package com.pluto;


import com.pluto.build.ConstructionStep;
import com.pluto.build.ConstructionTask;
import com.pluto.build.TaskManager;
import com.pluto.build.event.EventBus;
import com.pluto.build.listener.ConstructionTaskCompletedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConstructionDemo {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 订阅事件监听器
        EventBus.subscribe(new ConstructionTaskCompletedListener());

        // 创建工序
        ConstructionStep step1 = new ConstructionStep(1L, "水泥砂浆进场", new ArrayList<>(), false, 3);
        ConstructionStep step2 = new ConstructionStep(2L, "墙面找平", Arrays.asList(1L), false, 2);
        ConstructionStep step3 = new ConstructionStep(3L, "上墙涂料", Arrays.asList(2L), false, 1);

        // 创建施工任务
        ConstructionTask task1 = new ConstructionTask(1L, 1001L, 1L, 101L, new ArrayList<>());
        ConstructionTask task2 = new ConstructionTask(2L, 1001L, 2L, 102L, Arrays.asList(1L));
        ConstructionTask task3 = new ConstructionTask(3L, 1001L, 3L, 103L, Arrays.asList(2L));

        // 添加任务
        TaskManager.addTask(task1);
        TaskManager.addTask(task2);
        TaskManager.addTask(task3);

        // 用户输入模拟任务
        while (true) {
            System.out.println("\n当前任务状态：");
            printTasks();

            System.out.println("\n请输入任务ID 和 操作（完成/等待），如 `1 完成` 或 `exit` 退出：");
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("退出系统！");
                break;
            }

            processInput(input);
        }
    }

    private static void processInput(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            System.out.println("❌ 输入格式错误，请重新输入！");
            return;
        }

        try {
            Long taskId = Long.parseLong(parts[0]);
            String action = parts[1];

            ConstructionTask task = TaskManager.getTaskById(taskId);
            if (task == null) {
                System.out.println("❌ 未找到任务 ID: " + taskId);
                return;
            }

            if ("完成".equals(action)) {
                if (!task.getStatus().equals("完成")) {
                    task.markAsCompleted();
                    System.out.println("✅ 任务 " + taskId + " 已完成！");
                } else {
                    System.out.println("⚠ 任务 " + taskId + " 已经完成，无需重复操作！");
                }
            } else if ("等待".equals(action)) {
                System.out.println("⏳ 任务 " + taskId + " 继续等待...");
            } else {
                System.out.println("❌ 无效操作，请输入 `完成` 或 `等待`！");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ 任务 ID 必须是数字！");
        }
    }

    private static void printTasks() {
        for (ConstructionTask task : TaskManager.getAllTasks()) {
            System.out.println("任务ID: " + task.getTaskId() +
                    " | 工序: " + task.getStepId() +
                    " | 状态: " + task.getStatus());
        }
    }
}

