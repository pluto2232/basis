package com.pluto.build.event;

import com.pluto.build.ConstructionTask;

/**
 * 施工任务完成事件
 */
public class ConstructionTaskCompletedEvent implements ConstructionEvent {
    private final ConstructionTask task;

    public ConstructionTaskCompletedEvent(ConstructionTask task) {
        this.task = task;
    }

    public ConstructionTask getTask() {
        return task;
    }
}

