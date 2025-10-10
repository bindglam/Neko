package com.bindglam.neko.api.manager;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Process extends AutoCloseable {
    void start(List<ManagerBase> list);

    <T> void forEachParallel(List<T> list, Consumer<T> block);
}
