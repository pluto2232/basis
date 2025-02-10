package com.pluto.build;


import com.pluto.build.event.ConstructionTaskCompletedEvent;
import com.pluto.build.event.EventBus;

import java.util.List;

/**
 * 施工任务
 */
public class ConstructionTask {
    private Long taskId;
    private Long projectId;
    private Long stepId;
    private Long assigneeId;
    private String status;
    private List<Long> prevSteps;

    public ConstructionTask(Long taskId, Long projectId, Long stepId, Long assigneeId, List<Long> prevSteps) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.stepId = stepId;
        this.assigneeId = assigneeId;
        this.status = "待开始";
        this.prevSteps = prevSteps;
    }

    public Long getTaskId() { return taskId; }
    public Long getStepId() { return stepId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Long> getPrevSteps() { return prevSteps; }
    public Long getAssigneeId() {
        return assigneeId;
    }

    public void markAsCompleted() {
        this.status = "完成";
        EventBus.publish(new ConstructionTaskCompletedEvent(this));
    }
}


