package com.enterprise.module2;

public class Main {
    public static void main(String[] args) {
        NumberExecutor<Integer> executor = new NumberExecutor<Integer>();
        executor.addTask(new AddTask(1, -2));
        executor.addTask(new AddTask(1, 2), result -> result >= 0);
        executor.addTask(new AddTask(1, -2), result -> result >= 0);
        executor.addTask(new AddTask(Integer.MAX_VALUE, 1), result -> result >= 0);

        executor.execute();
        System.out.println("Valid result: ");
        executor.getValidResults().forEach(System.out::println);
        System.out.println("Invalid result: ");
        executor.getInvalidResults().forEach(System.out::println);
    }
    private static class AddTask implements Task<Integer> {
        private int value1;
        private int value2;
        private int result;

        public AddTask(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        public void execute() {
            result = value1 + value2;
        }

        @Override
        public Integer getResult() {
            return result;
        }
    }


}
