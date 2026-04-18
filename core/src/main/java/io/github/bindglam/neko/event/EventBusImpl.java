package io.github.bindglam.neko.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;

public final class EventBusImpl implements EventBus {
    private final Multimap<Class<? extends Event>, EventSubscriber<?>> subscribers = HashMultimap.create();

    @Override
    public <T extends Event> void subscribe(@NotNull Class<T> clazz, @NotNull EventSubscriber<T> subscriber) {
        subscribers.put(clazz, subscriber);
    }

    @Override
    public void call(@NotNull Event event) {
        subscribers.get(event.getClass()).forEach(sub -> {
            @SuppressWarnings("unchecked")
            EventSubscriber<Event> typedSub = (EventSubscriber<Event>) sub;
            typedSub.onCalled(event);
        });
    }
}
