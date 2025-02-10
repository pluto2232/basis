package com.pluto.build.listener;


import com.pluto.build.event.ConstructionEvent;

/**
 * 事件监听器（处理施工任务完成事件）
 */
public interface ConstructionEventListener {
    void handleEvent(ConstructionEvent event);
}
