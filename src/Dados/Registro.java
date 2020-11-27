package Dados;

import java.io.*;
import java.time.LocalDate;

public class Registro {
    private static final int MAX_LENGTH_NAME = 30;
    public static final int SIZE = 352;
    private static final int MAX_LENGTH_DESCRIPTION = 300;
    private int cpf;
    private String name;
    private LocalDate birthDate;
    private char gender;
    private String description;


    public Registro() {
    }

    public Registro(String name,int cpf, LocalDate birthDate, char  gender, String description) {
        this.name = resizeString(name, MAX_LENGTH_NAME);
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = resizeString(description, MAX_LENGTH_DESCRIPTION);
        this.cpf=cpf;
    }

    /**
     * this method return the new String with size informed
     *
     * @param str    String for input
     * @param length length of the final String
     * @return String with length
     */


//Transforma a String em uma string com o tamanho informado
    private String resizeString(String str, int length) {
        if (str.length() > length)
            return str.substring(0, length);
        return String.format("%" + length + "s", str).replaceAll(" ", "^");
    }

    //remove os caracteres nulos da string
    private String normalizeString(String str) {
        return str.replaceAll("\\^","");
    }

    public String getName() {
        return normalizeString(name);
    }

    public void setName(String name) {
        this.name = resizeString(name, MAX_LENGTH_NAME);
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return normalizeString(description);
    }

    public void setDescription(String description) {
        this.description = resizeString(description, MAX_LENGTH_DESCRIPTION);
    }
    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeUTF(name);
        saida.writeInt(cpf);
        saida.writeInt(birthDate.getDayOfMonth());
        saida.writeInt(birthDate.getMonthValue());
        saida.writeInt(birthDate.getYear());
        saida.writeChar(gender);
        saida.writeUTF(description);
        return dados.toByteArray();
    }

    public void fromByteArray(byte[] bytes) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(bytes);
        DataInputStream entrada = new DataInputStream(dados);
        this.name=entrada.readUTF();
        this.cpf=entrada.readInt();
        int day =entrada.readInt();
        int month =entrada.readInt();
        int year =entrada.readInt();
        this.birthDate = LocalDate.of(year,month,day);
        this.gender=entrada.readChar();
        this.description=entrada.readUTF();
    }

}
