package hash;

import Dados.Registro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        setup();
    }

    private void setup(){
        try {
            RandomAccessFile file = openFile(directoryFile);
            this.depth = file.readInt();
            if(this.depth==-1){
                this.depth =1;
                directory = new int[(int) Math.pow(2,this.depth)];
                file.seek(0);
                file.writeInt(this.depth);
            }
            else {
                this.directory = new int[(int) Math.pow(2,this.depth)];
                for (int i = 0; i <directory.length; i++) {
                    this.directory[i] = file.readInt();
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean add(Registro registro){
        try {
            int hash = hash(registro.getCpf());
            if (this.directory[hash] == -1) {
                currentBucket = new Bucket(this.depth);
                long[] values = currentBucket.getValues();
                int [] keys = currentBucket.getKeys();
                for (int i = 0; i < values.length; i++) {
                    if (keys[i] == -1) {
                        keys[i]=registro.getCpf();
                        values[i] = addMaster(registro);
                        currentBucket.setKeys(keys);
                        currentBucket.setValues(values);
                        this.directory[hash]= (int) addIndex(currentBucket);
                        return true;
                    }
                }
            }
            else{
                currentBucket = getIndex(this.directory[hash]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    private long addMaster(Registro registro) throws IOException {
        RandomAccessFile file = openFile(this.masterFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(registro.toByteArray());
        file.close();
        return endFile;
    }
    private long addIndex(Bucket bucket) throws IOException {
        RandomAccessFile file = openFile(this.indexFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(bucket.toByteArray());
        file.close();
        return endFile;
    }

    private Bucket getIndex(int i) throws IOException {
        RandomAccessFile file = openFile(indexFile);
        byte[] read = new byte[0];
        file.read(read,i,Bucket.SIZE);

        return null;
    }

    public Registro find(int key){
        return null;
    }

    private int findAddress(int key){
        int hash = hash(key);
        return 0;
    }

    public boolean remove(int key){
        return true;
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

    private int hash(Registro registro){
        return registro.hashCode();
    }

    private int hash(int key){
        return (int) ((key*key)%(Math.pow(2,this.depth)));
    }

}
