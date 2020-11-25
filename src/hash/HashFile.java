package hash;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class HashFile {
    public void initial() throws FileNotFoundException {
        RandomAccessFile file = new RandomAccessFile("Bucket.bk","r+");

    }
}
