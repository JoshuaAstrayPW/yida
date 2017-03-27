package com.fx.async;

import java.util.List;

/**
 * Created by nowcoder on 2017/3/15.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
