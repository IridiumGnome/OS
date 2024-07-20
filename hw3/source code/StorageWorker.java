import java.io.*;

public class StorageWorker {

    public static void saveInStorage(BTree b) {
        File f = new File("storage/");
        String[] files = f.list();
        int maxFileName = 0;
        for (String file : files) {
            if (!file.endsWith(".tmp")) continue;
            String [] a = file.split("/");
            String potentialFileName = a[a.length - 1].replace(".tmp", "");
            try {
                maxFileName = Math.max(maxFileName, Integer.parseInt(potentialFileName));
            } catch (Exception ignored) {}
        }
        maxFileName++;
        try {
            File newStorage = new File("storage/" + maxFileName + ".tmp");
            FileOutputStream fileOut = new FileOutputStream(newStorage);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(b);
            out.close();
            fileOut.close();
        } catch (Exception ignored) {}

    }

    
    public static boolean checkAllStorageDirectoryPUT(long key, String val) {
        File f = new File("storage/");
        String[] files = f.list();
        boolean status = false;
        for (String file : files) {
            if (status) break;
            if (!file.endsWith(".tmp")) {
                continue;
            }
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("storage/" + file));
                BTree bH = (BTree) in.readObject();
                in.close();
                if(bH.Contain(key)) {
                    bH.Change(key, val);
                    FileOutputStream fileOut = new FileOutputStream("storage/" + file);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(bH);
                    out.close();
                    fileOut.close();
                    status = true;
                }
            }
            catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
        }
        return status;
    }

    public static String checkAllStorageDirectoryGET(long key) {
        File f = new File("storage/");
        String[] files = f.list();
        String response = "";
        for (String file : files) {
            if (!file.endsWith(".tmp")) {
                continue;
            }
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("storage/" + file));
                BTree bH = (BTree) in.readObject();
                in.close();
                if (bH.Contain(key)) {
                    response = bH.Get(key);
                    break;
                }
            } catch (Exception ignored) {
            }
        }
        return response;
    }
}

