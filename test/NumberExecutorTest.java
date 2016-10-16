import com.enterprise.module2.NumberExecutor;
import com.enterprise.module2.Task;
import org.junit.Assert;
import org.junit.Test;

public class NumberExecutorTest {

    @Test
    public void testExecutorWithoutValidator() throws Exception {
        NumberExecutor<Integer> executor = new NumberExecutor<Integer>();
        executor.addTask(new AddTask(1, 2));
        executor.execute();
        Assert.assertEquals("Wrong valid Results sizes", 1, executor.getValidResults().size());
        Assert.assertEquals("Wrong invalid Results sizes", 0, executor.getInvalidResults().size());
        Assert.assertEquals("Wrong execution result", Integer.valueOf(3), executor.getValidResults().get(0));
    }

    @Test
    public void testExecuteWithValidator() throws Exception {
        NumberExecutor<Integer> executor = new NumberExecutor<Integer>();
        executor.addTask(new AddTask(1, -2), result -> result >= 0);
        executor.execute();

        Assert.assertEquals("Wrong valid Results sizes", 0, executor.getValidResults().size());
        Assert.assertEquals("Wrong invalid Results sizes", 1, executor.getInvalidResults().size());
        Assert.assertEquals("Wrong execution result", Integer.valueOf(-1), executor.getInvalidResults().get(0));

    }

    @Test
    public void testExecutor() throws Exception {
        NumberExecutor<Integer> executor = new NumberExecutor<Integer>();
        executor.addTask(new AddTask(1, -2));
        executor.addTask(new AddTask(1, 2), result -> result >= 0);
        executor.addTask(new AddTask(1, -2), result -> result >= 0);
        executor.addTask(new AddTask(Integer.MAX_VALUE, 1), result -> result >= 0);

        executor.execute();

        Assert.assertEquals("Wrong valid Results sizes", 2, executor.getValidResults().size());
        Assert.assertEquals("Wrong invalid Results sizes", 2, executor.getInvalidResults().size());

        Assert.assertEquals("Wrong execution result", Integer.valueOf(-1), executor.getValidResults().get(0));
        Assert.assertEquals("Wrong execution result", Integer.valueOf(3), executor.getValidResults().get(1));

        Assert.assertEquals("Wrong execution result", Integer.valueOf(-1), executor.getInvalidResults().get(0));
        Assert.assertEquals("Wrong execution result", Integer.valueOf(Integer.MIN_VALUE), executor.getInvalidResults().get(1));







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