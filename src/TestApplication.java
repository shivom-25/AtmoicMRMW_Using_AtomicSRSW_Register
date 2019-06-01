import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class TestApplication  implements Runnable {

    private int numThread;
    private int ktime;
    private float lambda;
    private float prob;
    private AtomicMRMW shrdObj;
    private float[] timeTaken;

    public TestApplication(int parameterThread,int parameterKtime,float parameterLambda,float parameterProb,AtomicMRMW AtomicMRMWObj,float[] parameterTimeTaken) {
        this.numThread = parameterThread;
        this.ktime = parameterKtime;
        this.lambda = parameterLambda;
        this.prob =parameterProb;
        this.shrdObj = AtomicMRMWObj;
        this.timeTaken = parameterTimeTaken;
    }

    public static double getNext(float lambda) {
        Random rand = new Random();
        return  Math.log(1-rand.nextDouble())/(lambda);
    }

    public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public void run() {
        try {
            long value;
            int id = (int) Thread.currentThread().getId()%numThread;
            double delay;
            int decider; // 0 => read , 1 => write;
            double choice;
            String s,str;
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            Date start = null;
            Date stop = null;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime enterTime;
            LocalDateTime cmpltTime;
            long duration;

            for(int i=0;i<ktime;i++) {
                delay = TestApplication.getNext(lambda);
                choice = Math.random()*100 + 1;
                if(choice > prob*100)
                    decider=0;
                else
                    decider=1;

                if(decider == 0) {
                    enterTime = LocalDateTime.now();
                    s= Integer.toString(i) + "th read requested at " + enterTime + " by thread " + Integer.toString(id);
                    System.out.println(s);
                    value = shrdObj.readAtomicMRMW(id);
                    cmpltTime = LocalDateTime.now();
                    str= Integer.toString(i) + "th read completed at " + cmpltTime + " by thread " + Integer.toString(id) +" and value read is : " + Long.toString(value);
                    System.out.println(str);
                }
                else {
                    value = (long) choice;
                    enterTime = LocalDateTime.now();
                    s= Integer.toString(i) + "th write requested at " + enterTime + " by thread " + Integer.toString(id);
                    System.out.println(s);
                    shrdObj.writeAtomicMRMW(value,id);
                    cmpltTime = LocalDateTime.now();
                    str= Integer.toString(i) + "th write completed at " + cmpltTime + " by thread " + Integer.toString(id) +" and value written is : " + Long.toString(value);
                    System.out.println(str);
                }
                start = TestApplication.convertToDateViaSqlTimestamp(enterTime);
                stop = TestApplication.convertToDateViaSqlTimestamp(cmpltTime);
                duration = stop.getTime() - start.getTime();
                timeTaken[id] += duration;
                Thread.sleep(2000);
            }
            System.out.println("Thread :" + id + " completed its run");
        }
        catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }
}
