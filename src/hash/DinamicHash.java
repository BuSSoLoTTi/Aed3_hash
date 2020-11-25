package hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DinamicHash {
    private int depth ;
    private File indexFile;
    private File directoryFile;
    private File masterFile;
    private final String path;
    private Bucket currentBucket;
    private int[] directory;


    public DinamicHash(String path) {
        this.path = path;
        definePath();
    }

    private void setup(){
        try {
            RandomAccessFile index = openFile(indexFile);
            RandomAccessFile master = openFile(masterFile);
            RandomAccessFile dir = openFile(directoryFile);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void definePath(){
        if(path==null||path.isEmpty()){
            this.indexFile = new File("index.hx");
            this.directoryFile = new File("dir.hx");
            this.masterFile = new File("master.hx");
        }
        else {
            if (this.path.endsWith("/") || this.path.endsWith("\\")) {
                this.indexFile = new File(this.path + "index.hx");
                this.directoryFile = new File(this.path + "dir.hx");
                this.masterFile = new File(this.path + "master.hx");
            } else {
                this.indexFile = new File(this.path + "/index.hx");
                this.directoryFile = new File(this.path + "/dir.hx");
                this.masterFile = new File(this.path + "/master.hx");
            }
        }
    }

    private RandomAccessFile openFile(File file) throws FileNotFoundException {
        return new RandomAccessFile(file, "rw");
    }

    private int hash(){
        return 0;
    }

}
