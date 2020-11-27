package hash;

import Dados.Registro;

import java.io.*;
import java.time.LocalDate;

public class Bucket {
    public static final int SIZE = 64;
    private static final int SIZE_BUCKET = 5; //valor simbolico para o numero do tamanho do bucket
    private int depth;
    private int[] keys;
    private long[] values;

    public Bucket(int deph){
        this.depth=deph;
        keys = new  int[SIZE_BUCKET];
        values = new  long[SIZE_BUCKET];
        for (int i = 0; i < SIZE_BUCKET; i++) {
            keys[i]=-1;
            values[i]=-1;
        }
    }


    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public long[] getValues() {
        return values;
    }

    public void setValues(long[] values) {
        this.values = values;
    }

    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeInt(depth);
        for (int i = 0; i < SIZE_BUCKET; i++) {
            saida.writeInt(keys[i]);
        }
        for (int i = 0; i < SIZE_BUCKET; i++) {
            saida.writeLong(values[i]);
        }
        return dados.toByteArray();
    }

    public void fromByteArray(byte[] bytes) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(bytes);
        DataInputStream entrada = new DataInputStream(dados);
        this.depth=entrada.readInt();
        long[] keys = new long[SIZE_BUCKET];
        long[] values = new long[SIZE_BUCKET];
        for (int i = 0; i < SIZE_BUCKET; i++) {
            keys[i]=entrada.readInt();
        }
        for (int i = 0; i < SIZE_BUCKET; i++) {
            values[i]=entrada.readLong();
        }
        this.values=values;
    }




}
