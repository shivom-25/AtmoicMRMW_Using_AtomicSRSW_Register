import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            ReadInput rInp = new ReadInput();
            String filePath = args[0];
            System.out.println(args[0]);
            Float[] inputParameters = rInp.readInput(filePath);
            int numThread =(int) (float) inputParameters[0];
            int ktime = (int) (float) inputParameters[1];
            float lambda = inputParameters[2];
            float prob = inputParameters[3];
            float[] timeTaken = new float[numThread];
            for(int i=0;i<numThread;i++) {
                timeTaken[i]=0;
            }
            AtomicMRMW AtomicMRMWObj = new AtomicMRMW(numThread);
            try {
                Thread th[] = new Thread[numThread];
                for (int i = 0; i < numThread; i++) {
                    th[i] = new Thread(new TestApplication(numThread,ktime,lambda,prob,AtomicMRMWObj,timeTaken));
                    th[i].start();
                }
                for (int i = 0; i < numThread; i++) {
                    th[i].join();
                }

                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println("avg time(in milisec) taken by each thread to enter and complete critical section is:");
                for(int i=0;i<numThread;i++) {
                    System.out.println("thread" + i + " : " + timeTaken[i]/ktime);
                }
                System.out.println("-----------------------------------------------------------------------------------");
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted exception caught");
            }
        }
        catch (FileNotFoundException fe) {
            System.out.println("File not found");
        }
        System.out.println("task completed");
    }
}
