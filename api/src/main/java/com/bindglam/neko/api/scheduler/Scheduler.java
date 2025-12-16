package com.bindglam.neko.api.scheduler;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface Scheduler {
    SchedulerTask run(Runnable action);

    SchedulerTask runLater(Runnable action, long ticks);

    SchedulerTask runTimer(Runnable action, long delay, long period);

    SchedulerTask run(Entity entity, Runnable action);

    SchedulerTask runLater(Entity entity, Runnable action, long ticks);

    SchedulerTask runTimer(Entity entity, Runnable action, long delay, long period);

    SchedulerTask run(Location location, Runnable action);

    SchedulerTask runLater(Location location, Runnable action, long ticks);

    SchedulerTask runTimer(Location location, Runnable action, long delay, long period);

    SchedulerTask runAsync(Runnable action);

    SchedulerTask runAsyncLater(Runnable action, long ticks);

    SchedulerTask runAsyncTimer(Runnable action, long delay, long period);
}
