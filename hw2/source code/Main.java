import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.lang.model.type.ArrayType;

public class Main {
    // temporary map
    static ConcurrentSkipListMap<Integer, String> allData = new ConcurrentSkipListMap<>();
    static FileWriter myWriter;
    static int threadCount;
    static int simple_capacity;
    static ArrayList <ArrayList <Integer> > test;
    static ArrayList <Integer> idList;
   
    public static void callThreads(boolean first) throws IOException, InterruptedException {
    	//System.out.println("Start Processing Threads");
	// array of threads
        Refactor [] list = new Refactor[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int [] sizeInfo = getRange(i, test.size());
            list[i] = new Refactor("Thread" + String.valueOf(i + 1), test.subList(sizeInfo[0], sizeInfo[1]), idList.subList(sizeInfo[0], sizeInfo[1]), first);
            list[i].start();
            first = false;
        }
        for (int i = 0; i < threadCount; i++) list[i].join();
	//System.out.println("Stop Processing Threads");
        // clean up
        simple_capacity = 0;
        test.clear();
        //System.gc();
        idList.clear();
	//System.out.println("Writing");
        // write to json
        for(Map.Entry<Integer, String> e: allData.entrySet()) myWriter.write(e.getValue());
        //for (int i : allData.keySet()) myWriter.write(allData.get(i)); <- this one is slower
        //System.out.println("Stop Writing");
	allData.clear();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	boolean first = true;
    	threadCount = Integer.parseInt(args[0]);
        myWriter = new FileWriter("output.json");
        myWriter.write("[\n");
        simple_capacity = 0;
        final long startTime = System.nanoTime();
        // array of rows
        test = new ArrayList<>();
        // variable and array to check the num of row
        int globalIdPointer = 1;
        idList = new ArrayList<>();
        // read csv line by line
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("input.csv"))) {
        //System.out.println("Start Reading");    
	while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");
                ArrayList <Integer> local_val = new ArrayList<>();
                for (String i : values) local_val.add(Integer.valueOf(i));
                simple_capacity = simple_capacity + local_val.size() * 4;
                test.add(local_val);
                idList.add(globalIdPointer);
                globalIdPointer++;
                // if size bigger than 20MB start processing // on windows 50MB
                if (simple_capacity / 1000000 > 20) { callThreads(first); first=false; }
            }
        } catch (IOException ignor) {}
	//System.out.println("Stop Reading");
        if(simple_capacity != 0) callThreads(first = false); 

        final long duration = System.nanoTime() - startTime;
        System.out.println(duration / 1000000000);
        
        myWriter.write("\n]");
        myWriter.close();
    }


    static int [] getRange(int i, int size) {
        int [] ans = new int[2];
        int k = size / Main.threadCount;
        ans[0] = k * i;
        ans[1] = k * (i + 1);
        if (i == Main.threadCount - 1) ans[1] = size;
        return ans;
    }
}

class Refactor extends Thread {
	
    List<ArrayList<Integer>> toProcess;
    List<Integer> myIdList;
    Boolean first;
    
    Refactor(String name, List<ArrayList<Integer>> toProcess , List<Integer> myIdList, Boolean first){
        super(name);
        this.myIdList = myIdList;
        this.toProcess = toProcess;
        this.first = first;
    }

    public void run(){
        for (int i = 0; i < toProcess.size(); i++) {
        	StringBuilder row;
        	if(first & i == 0 & Thread.currentThread().getName().equals("Thread1"))
        		row = new StringBuilder("\t{\n");
        	else row = new StringBuilder(",\n\t{\n");
            int arraySize = toProcess.get(i).size();
            for (int j = 0; j < arraySize; j++) {
                row.append("\t\t\"col_" + String.valueOf(j + 1) + "\":" + String.valueOf(toProcess.get(i).get(j)));
                row.append(j + 1 != arraySize ? ",\n" : "\n");
            }
            //row.append("\t}").append(i + 1 == toProcess.size() ? " \n" : ",\n");
            row.append("\t}");
            Main.allData.put(myIdList.get(i), row.toString());
        }
        Thread.yield();
    }
}
