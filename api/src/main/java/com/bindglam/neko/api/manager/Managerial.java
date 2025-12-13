package com.bindglam.neko.api.manager;

public interface Managerial {
    void start(LifecycleContext context, Process process);

    void end(LifecycleContext context, Process process);
}
