import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupedDataFrame implements Groupby {
    public LinkedList<DataFrame> data;
    private final int MAX_THREADS = 3;

    GroupedDataFrame() { data = new LinkedList<>(); }

    GroupedDataFrame(LinkedList<DataFrame> grouped) {
        data = (LinkedList<DataFrame>) grouped.clone();
    }


    // THREADING
    class TaskMax implements Runnable {
        private String name;
        private DataFrame df;
        private final DataFrame dfRet;
        private CountDownLatch latch;

        public TaskMax(CountDownLatch latchInput, String nameInput, DataFrame dfInput, DataFrame result) {
            super();
            latch = latchInput;
            name = nameInput;
            df = dfInput;
            dfRet = result;
        }
        public void run(){
            DataFrame maxRow = df.getRow(0);
            ArrayList<Value> colF = df.getData().get(2);
            MyFloat currF = new MyFloat((float)0.0);
            MyFloat maxF = (MyFloat) currF.create(colF.get(0).toString());

            ArrayList<Value> colD = df.getData().get(3);
            MyDouble currD = new MyDouble((double)0.0);
            MyDouble maxD = (MyDouble) currD.create(colD.get(0).toString());

            ArrayList<Value> colT = df.getData().get(1);
            MyDateTime currT = new MyDateTime(LocalDate.parse(colT.get(0).toString()));
            MyDateTime maxT = (MyDateTime) currT.create(colT.get(0).toString());
            for (int i = 0; i < df.getHeight(); i ++) {
                currF = (MyFloat) currF.create(colF.get(i).toString());
                currD = (MyDouble) currD.create(colD.get(i).toString());
                currT = (MyDateTime) currT.create(colT.get(i).toString());
                if (currF.gte(maxF)) {
                    maxF = currF;
                }
                if (currD.gte(maxD)) {
                    maxD = currD;
                }
                if (currT.gte(maxT)) {
                    maxT = currT;
                }
            }

            // prepare max values from all cols in a single row
            maxRow.set(maxRow.getColNames()[1], 0, maxT);
            maxRow.set(maxRow.getColNames()[2], 0, maxF);
            maxRow.set(maxRow.getColNames()[3], 0, maxD);
            synchronized (dfRet) {
                dfRet.appendFrame(maxRow);
            }
            latch.countDown();
        }
    }

    public DataFrame threadedMax() throws InterruptedException {
        DataFrame result = new DataFrame(data.get(0).getColNames(), data.get(0).getColTypes());

        // create tasks
        ArrayList<Runnable> taskList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(data.size());
        for (int i = 0; i < data.size(); i ++) {
            taskList.add(new TaskMax(latch, Integer.toString(i), data.get(i), result));
        }

        // execute tasks from the pool
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
        for (Runnable task: taskList) {
            pool.execute(task);
        }

        // wait for all tasks to finish before returning completed DataFrame
        latch.await();
        pool.shutdown();
        return result;
    }

    @Override
    public DataFrame max() {
        DataFrame result = new DataFrame(data.get(0).getColNames(), data.get(0).getColTypes());
        DataFrame maxRow;
        for (DataFrame df: data) {
            maxRow = df.getRow(0);
            ArrayList<Value> colF = df.getData().get(2);
            MyFloat currF = new MyFloat((float)0.0);
            MyFloat maxF = (MyFloat) currF.create(colF.get(0).toString());

            ArrayList<Value> colD = df.getData().get(3);
            MyDouble currD = new MyDouble((double)0.0);
            MyDouble maxD = (MyDouble) currD.create(colD.get(0).toString());

            ArrayList<Value> colT = df.getData().get(1);
            MyDateTime currT = new MyDateTime(LocalDate.parse(colT.get(0).toString()));
            MyDateTime maxT = (MyDateTime) currT.create(colT.get(0).toString());
            for (int i = 0; i < df.getHeight(); i ++) {
                currF = (MyFloat) currF.create(colF.get(i).toString());
                currD = (MyDouble) currD.create(colD.get(i).toString());
                currT = (MyDateTime) currT.create(colT.get(i).toString());
                if (currF.gte(maxF)) {
                    maxF = currF;
                }
                if (currD.gte(maxD)) {
                    maxD = currD;
                }
                if (currT.gte(maxT)) {
                    maxT = currT;
                }
            }
            maxRow.set(maxRow.getColNames()[1], 0, maxT);
            maxRow.set(maxRow.getColNames()[2], 0, maxF);
            maxRow.set(maxRow.getColNames()[3], 0, maxD);
            result.appendFrame(maxRow);
        }
        return result;
    }

    @Override
    public DataFrame min() {
        return null;
    }

    @Override
    public DataFrame mean() {
        return null;
    }

    @Override
    public DataFrame std() {
        return null;
    }

    @Override
    public DataFrame sum() {
        return null;
    }

    @Override
    public DataFrame var() {
        return null;
    }

    @Override
    public DataFrame apply() {
        return null;
    }
}
