import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main {
	
    public static void main(String[] args) throws IOException {

	//final long startTime = System.nanoTime();

        String filePath = args[0];
        String[] tmpString = filePath.split("/");
	int x = tmpString.length - 1;
	String y = tmpString[x];
        String[] tmpString2 = y.split("\\.");
        FileWriter myWriter;
        String filePathOut = tmpString2[0].concat(".output");
        myWriter = new FileWriter(filePathOut);
        
        //System.out.println(filePath);
        //System.out.println(filePathOut);
        
        BTree b = new BTree(300);
	boolean writeToStorage = false;
	boolean deleteOutput = true;

	File storageFolder = new File("./storage");
	storageFolder.mkdir();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] input = line.split(" ");
                if(input[0].equals("PUT")) {
                    long key = Long.parseLong(input[1]);
                    if(StorageWorker.checkAllStorageDirectoryPUT(key, input[2])) {
                        continue;
                    }
                    else {
                        if(b.Contain(key)) {
                            b.Change(key, input[2]);
                        }
                        else {
			    writeToStorage = true;
                            b.Insert(key, input[2]);
                            counter++;
                        }
                        if (counter == 200000) { // 15 000 000
                            StorageWorker.saveInStorage(b);
                            b = new BTree(300);
                            counter = 0;
			    writeToStorage = false;
                        }
                    }
                }
                else if(input[0].equals("GET")) {
                    long key = Long.parseLong(input[1]);
                    String val = StorageWorker.checkAllStorageDirectoryGET(key);
                    if (val.equals("")) {
                        val = b.Get(key);
                    }
                    myWriter.write(val + "\n");
		    deleteOutput = false;
                    //System.out.println(key + " " + val);

                }
                else if(input[0].equals("SCAN")) {
                    long key = Long.parseLong(input[1]);
                    long key2 = Long.parseLong(input[2]);
                    for(long i = key; i <= key2; i++) {
                        String val = StorageWorker.checkAllStorageDirectoryGET(i);
                        if (val.equals("")) {
                            val = b.Get(i);
                        }
                        myWriter.write(val + "\n");
			deleteOutput = false;
                        //System.out.println(i + " " + val);
                    }
                }
            }
        }
        catch(IOException e) {
            System.out.println("Could not open the file");
            e.printStackTrace();
        }

        if (b != null && writeToStorage) {
            StorageWorker.saveInStorage(b);
        }

        myWriter.close();

	if(deleteOutput){
		File f = new File(filePathOut);
		f.delete();
	}

	//check time
	//final long duration = System.nanoTime() - startTime;
        //System.out.print(duration / 1000000000);//1000000000 
	//System.out.println(" sec");
    }
}

