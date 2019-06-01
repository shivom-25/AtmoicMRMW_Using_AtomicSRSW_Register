import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadInput {

    Float[] readInput(String filename) throws IOException {

        float token;
        Float[] tempsArray = new Float[10];
        try {
            Scanner inFile = new Scanner(new File(filename)).useDelimiter("\\s");
            List<Float> temps = new ArrayList<Float>();
            while(inFile.hasNext()) {
                token = inFile.nextFloat();
                temps.add(token);
            }
            inFile.close();
            tempsArray = temps.toArray(new Float[0]);

        }
        catch (FileNotFoundException fe) {
            System.out.println("File not found");
        }
        finally {
            return tempsArray;
        }
    }
}
