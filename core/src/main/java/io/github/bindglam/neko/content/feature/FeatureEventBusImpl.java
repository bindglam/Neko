package io.github.bindglam.neko.content.feature;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.bindglam.neko.content.feature.event.FeatureEvent;
import io.github.bindglam.neko.content.feature.event.FeatureEventSubscriber;
import org.jetbrains.annotations.NotNull;

public final class FeatureEventBusImpl implements FeatureEventBus {
    private final Multimap<Class<? extends FeatureEvent>, FeatureEventSubscriber<?>> subscribers = HashMultimap.create();

    @Override
    public <T extends FeatureEvent> void subscribe(@NotNull Class<T> clazz, @NotNull FeatureEventSubscriber<T> subscriber) {
        subscribers.put(clazz, subscriber);
    }

    @Override
    public void call(@NotNull FeatureEvent event) {
        subscribers.get(event.getClass()).forEach(sub -> {
            @SuppressWarnings("unchecked")
            FeatureEventSubscriber<FeatureEvent> typedSub = (FeatureEventSubscriber<FeatureEvent>) sub;
            typedSub.onCalled(event);
        });
    }
}
