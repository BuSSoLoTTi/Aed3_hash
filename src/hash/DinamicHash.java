package hash;

import Dados.Registro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DinamicHash {
    private int depth;
    private File indexFile;
    private File directoryFile;
    private File masterFile;
    private final String path;
    private Bucket currentBucket;
    private long[] directory;

    public DinamicHash(String path) {
        this.path = path;
        definePath();
        setup();
    }

    private int getSize() {
        return (int) Math.pow(2, this.depth);
    }

    private void setup() {
        try {
            RandomAccessFile file = openFile(directoryFile);
            String fristline = file.readLine();
            file.seek(0);
            if (fristline == null) {
                this.depth = 1;
                directory = new long[getSize()];
                file.seek(0);
                file.writeInt(this.depth);
                for (int i = 0; i < getSize(); i++) {
                    currentBucket = new Bucket(this.depth);
                    this.directory[i]=addIndex();
                    file.writeLong(this.directory[i]);
                }
            } else {
                this.depth = file.readInt();
                this.directory = new long[getSize()];
                for (int i = 0; i < directory.length; i++) {
                    this.directory[i] = file.readLong();
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean add(Registro registro) throws IOException {
        boolean add = false;
        int attempt = 0;
        do{
            int hash = hash(registro.getCpf());
            long value = addMaster(registro);
            currentBucket = getBucket(this.directory[hash]);
            if(currentBucket.add(registro.getCpf(),value)){
                add=true;
                addIndex(this.directory[hash]);
            }
            else {
                chageDepth(hash);
            }
            attempt++;
        }while (!add&&attempt<3);
        return add;
    }

    private void chageDepth(int hash) throws IOException {
        int oudSize = getSize();
        this.depth++;
        Bucket oudBucket = currentBucket;
        Bucket newBucket = new Bucket(this.depth);
        currentBucket = new Bucket(this.depth);
        addIndex(this.directory[hash]);
        long[] directory = new long[getSize()];
        for (int i = 0; i < directory.length; i++) {
            directory[i]=this.directory[i%oudSize];
        }
        directory[oudSize]=addIndex();
        int[] keys = oudBucket.getKeys();
        long[] values = oudBucket.getValues();
        for (int i = 0; i < keys.length; i++) {
            int newHash = hash(keys[i]);
            if(newHash == hash){
                if (!currentBucket.add(keys[i],values[i])) {
                    chageDepth(newHash);
                }
            }
            else {
                if (!newBucket.add(keys[i],values[i])) {
                    chageDepth(newHash);
                }
            }
            addIndex(this.directory[hash]);
            currentBucket=newBucket;
            addIndex(this.directory[oudSize]);
            saveDirectory();
        }

    }

    public Registro find(int key) throws IOException {
        int hash = hash(key);
        currentBucket = getBucket(this.directory[hash]);
        long address = currentBucket.find(key);
        if(address==-1){
            return null;
        }
        return getMaster(address);

    }

    public Registro remove(int key) throws IOException {
        int hash = hash(key);
        currentBucket = getBucket(this.directory[hash]);
        long address = currentBucket.remove(key);
        if(address!=-1) {
            addIndex(this.directory[hash]);
            return getMaster(address);
        }
        return null;
    }

    public boolean edit(int key,Registro registro) throws IOException {
        int hash =  hash(key);
        currentBucket = getBucket(this.directory[hash]);
        long address = currentBucket.find(key);
        addMaster(registro,address);
        return true;
    }

    private long addMaster(Registro registro) throws IOException {
        RandomAccessFile file = openFile(this.masterFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(registro.toByteArray());
        file.close();
        return endFile;
    }

    private long addMaster(Registro registro,long address) throws IOException {
        RandomAccessFile file = openFile(this.masterFile);
        file.seek(address);
        file.write(registro.toByteArray());
        file.close();
        return address;
    }

    private Registro getMaster(long address) throws IOException {
        RandomAccessFile file = openFile(masterFile);
        file.seek(address);
        byte[] bytes = new byte[Registro.SIZE];
        file.read(bytes,0,Registro.SIZE);
        file.close();
        Registro registro = new Registro();
        registro.fromByteArray(bytes);
        return registro;
    }

    private long addIndex() throws IOException {
        RandomAccessFile file = openFile(this.indexFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(currentBucket.toByteArray());
        file.close();
        return  endFile;
    }
    private Long addIndex(long address) throws IOException {
        RandomAccessFile file = openFile(this.indexFile);
        file.seek(address);
        file.write(currentBucket.toByteArray());
        file.close();
        return  address;
    }

    private Bucket getBucket(long pos) throws IOException {
        RandomAccessFile file = openFile(indexFile);
        byte[] read = new byte[Bucket.SIZE];
        file.seek(pos);
        file.read(read,0, Bucket.SIZE);
        Bucket bucket = new Bucket(this.depth);
        bucket.fromByteArray(read);
        return bucket;
    }

    private void saveDirectory() {
        try {
            RandomAccessFile file = openFile(directoryFile);
            file.seek(0);
            file.writeInt(depth);
            for (int i = 0; i < directory.length; i++) {
                file.writeLong(directory[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void definePath() {
        if (path == null || path.isEmpty()) {
            this.indexFile = new File("index.hx");
            this.directoryFile = new File("dir.hx");
            this.masterFile = new File("master.hx");
        } else {
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

    private int hash(Registro registro) {
        return registro.hashCode();
    }

    private int hash(int key) {
        return (int) ((key * key) % (Math.pow(2, this.depth)));
    }

    public void printFiles() throws IOException {
        RandomAccessFile directory = openFile(directoryFile);
        RandomAccessFile index = openFile(indexFile);
        RandomAccessFile master = openFile(masterFile);

        int directorySize = directory.readInt();


    }
}
