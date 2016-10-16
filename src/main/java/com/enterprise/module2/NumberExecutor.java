package com.enterprise.module2;

import java.util.ArrayList;
import java.util.List;

public class NumberExecutor<T> implements Executor<T> {

    public static final Validator<Object> DEFAULT_VALIDATOR = value -> true;
    private List<TaskAndValid<T>> tasks = new ArrayList<>();
    private List<T> validResults = new ArrayList<>();
    private List<T> invalidResults = new ArrayList<>();

    private boolean executed = false;


    @Override
    public void addTask(Task<? extends T> task) {
        checkNotExecuted();
        addTask(task, DEFAULT_VALIDATOR);
    }

    private void checkExecuted() {
        if(!executed){
            throw new IllegalStateException("Executor already executed");

        }
    }
    private void checkNotExecuted() {
        if(executed){
            throw new IllegalStateException("Executor already executed");

        }
    }

    @Override
    public void addTask(Task<? extends T> task, Validator<? super T> validator) {

        tasks.add(new TaskAndValid<T>(task, validator));

    }

    @Override
    public void execute() {
        checkNotExecuted();
        for (TaskAndValid<T> taskAndValid : tasks) {
            Task<? extends T> task = taskAndValid.task;
            task.execute();
                if (taskAndValid.validator.isValid(task.getResult())) {
                    validResults.add(task.getResult());
                } else {
                    invalidResults.add(task.getResult());
                }
        }
        executed = true;
    }

    @Override
    public List<T> getValidResults() {
        checkExecuted();
        return validResults;
    }

    @Override
    public List<T> getInvalidResults() {
        checkExecuted();
        return invalidResults;
    }

    private static class TaskAndValid<T> {
        private Task<? extends T> task;
        private Validator<? super T> validator;

        public TaskAndValid(Task<? extends T> task, Validator<? super T> validator) {
            this.task = task;
            this.validator = validator;
        }
    }
}
