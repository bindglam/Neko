package io.github.bindglam.neko.content.feature;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.bindglam.neko.content.feature.event.FeatureEvent;
import io.github.bindglam.neko.content.feature.event.FeatureEventSubscriber;
import org.jetbrains.annotations.NotNull;

public final class FeatureEventBusImpl implements FeatureEventBus {
    private final Multimap<String, FeatureEventSubscriber<?>> subscribers = HashMultimap.create();

    @Override
    public <T extends FeatureEvent> void subscribe(@NotNull Class<T> clazz, @NotNull FeatureEventSubscriber<T> subscriber) {
        subscribers.put(clazz.getName(), subscriber);
    }

    @Override
    public void call(@NotNull FeatureEvent event) {
        subscribers.get(event.getClass().getName()).forEach(sub -> sub.onCalled(event));
    }
}
