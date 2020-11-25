package aplicacao;

import Dados.Registro;

import java.io.*;
import java.time.LocalDate;

public class Aplicacao {
    public static void main(String[] args) throws FileNotFoundException {
    RandomAccessFile a = new RandomAccessFile("a","rw");

        try {
            a.writeInt(55);
            a.writeInt(56);
            if(a.length()!=0) {
                a.seek(4);
                System.out.println(a.readInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream stream =  new ByteArrayOutputStream();
        OutputStream =

    }
}
