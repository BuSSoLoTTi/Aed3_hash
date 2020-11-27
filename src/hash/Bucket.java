package hash;

import Dados.Registro;

import java.io.*;
import java.time.LocalDate;

public class Bucket {
    public static final int SIZE = 44;
    private static final int SIZE_BUCKET = 5; //valor simbolico para o numero do tamanho do bucket
    private int depth;
    private long[] values;

    public Bucket(int deph){
        this.depth=deph;
        values = new  long[SIZE_BUCKET];
        for (int i = 0; i < SIZE_BUCKET; i++) {
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

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeInt(depth);
        for (int i = 0; i < SIZE_BUCKET; i++) {
            saida.writeLong(values[i]);
        }
        return dados.toByteArray();
    }

    public void fromByteArray(byte[] bytes) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(bytes);
        DataInputStream entrada = new DataInputStream(dados);
        this.depth=entrada.readInt();
        long[] values = new long[SIZE_BUCKET];
        for (int i = 0; i < SIZE_BUCKET; i++) {
            values[i]=entrada.readLong();
        }
        this.values=values;
    }




}
