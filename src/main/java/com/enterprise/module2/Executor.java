package com.enterprise.module2;

import java.util.List;

public interface Executor<T> {

    void addTask(Task<? extends T> task);

    void addTask(Task<? extends T> task, Validator<? super T> validator);

    void execute();

    List< T> getValidResults();

    List<T> getInvalidResults();

}
