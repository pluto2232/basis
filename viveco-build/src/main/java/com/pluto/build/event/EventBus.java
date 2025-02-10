package com.pluto.build.event;


import com.pluto.build.listener.ConstructionEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件总线（发布/订阅）
 */
public class EventBus {
    private static final List<ConstructionEventListener> listeners = new ArrayList<>();

    public static void subscribe(ConstructionEventListener listener) {
        listeners.add(listener);
    }

    public static void publish(ConstructionEvent event) {
        for (ConstructionEventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}

