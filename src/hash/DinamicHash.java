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
            this.depth = file.readInt();
            if (this.depth == -1) {
                this.depth = 1;
                directory = new long[getSize()];
                file.seek(0);
                file.writeInt(this.depth);
                for (int i = 0; i < getSize(); i++) {
                    currentBucket = new Bucket(this.depth);
                    file.writeInt(addIndex());
                }
            } else {
                this.directory = new long[getSize()];
                for (int i = 0; i < directory.length; i++) {
                    this.directory[i] = file.readInt();
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean add(Registro registro) throws IOException {
        int hash = hash(registro.getCpf());
        long value = addMaster(registro);
        currentBucket = getBucket(this.directory[hash]);
        currentBucket=

        return false;
    }


    private void chageDepth(int hash) throws IOException {


    }

    private long addMaster(Registro registro) throws IOException {
        RandomAccessFile file = openFile(this.masterFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(registro.toByteArray());
        file.close();
        return endFile;
    }

    private int addIndex() throws IOException {
        RandomAccessFile file = openFile(this.indexFile);
        long endFile = file.length();
        file.seek(endFile);
        file.write(currentBucket.toByteArray());
        file.close();
        return (int) endFile;
    }
    private int addIndex(long address) throws IOException {
        RandomAccessFile file = openFile(this.indexFile);
        long endFile = file.length();
        file.seek(address);
        file.write(currentBucket.toByteArray());
        file.close();
        return (int) endFile;
    }

    private Bucket getBucket(int i) throws IOException {
        RandomAccessFile file = openFile(indexFile);
        byte[] read = new byte[Bucket.SIZE];
        file.read(read, i, Bucket.SIZE);
        return null;
    }

    private void saveDirectory() {
        try {
            RandomAccessFile file = openFile(directoryFile);
            file.seek(0);
            file.writeInt(depth);
            for (int i = 0; i < directory.length; i++) {
                file.writeInt(directory[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean remove(int key) {
        return true;
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

}
