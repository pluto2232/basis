package com.pluto.build.listener;


import com.pluto.build.TaskManager;
import com.pluto.build.ConstructionTask;
import com.pluto.build.event.ConstructionEvent;
import com.pluto.build.event.ConstructionTaskCompletedEvent;
import com.pluto.build.event.EventBus;

import java.util.List;

public class ConstructionTaskCompletedListener implements ConstructionEventListener {
    @Override
    public void handleEvent(ConstructionEvent event) {
        if (event instanceof ConstructionTaskCompletedEvent) {
            ConstructionTask task = ((ConstructionTaskCompletedEvent) event).getTask();
            System.out.println("🔔 任务完成: " + task.getStepId() + ", 更新后续任务状态...");

            // 处理后续工序解锁
            unlockNextTask(task);
            sendWechatNotification(task.getAssigneeId(), "施工完成，请确认！");
        }
    }

    private void unlockNextTask(ConstructionTask task) {
        List<ConstructionTask> nextTasks = TaskManager.getNextTasks(task);
        for (ConstructionTask t : nextTasks) {
            if (t.getPrevSteps().stream().allMatch(prevStepId -> TaskManager.getTaskById(prevStepId).getStatus().equals("完成"))) {
                t.setStatus("待开始");
                System.out.println("✅ 任务 " + t.getTaskId() + " 现在可开始！");
            }
        }
    }

    private void sendWechatNotification(Long assigneeId, String message) {
        System.out.println("📢 【微信通知】 发送给负责人 " + assigneeId + ": " + message);
    }
}
