package Dados;

import java.time.LocalDate;

public class Registro {
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MAX_LENGTH_GENDER = 10;
    private static final int MAX_LENGTH_DESCRIPTION = 300;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String description;


    private Registro() {
    }

    public Registro(String name, LocalDate birthDate, String gender, String description) {
        this.name = resizeString(name, MAX_LENGTH_NAME);
        this.birthDate = birthDate;
        this.gender = resizeString(gender, MAX_LENGTH_GENDER);
        this.description = resizeString(description, MAX_LENGTH_DESCRIPTION);
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
        return str.length() > length ? str.substring(0, length) : String.format("%" + length + "s", str).replaceAll(" ", "0");
    }

    //remove os caracteres nulos da string
    private String normalizeString(String str) {
        return str.trim();
    }

    public String getName() {
        return normalizeString(name);
    }

    public void setName(String name) {
        this.name = resizeString(name, MAX_LENGTH_NAME);
    }

    public String getGender() {
        return normalizeString(gender);
    }

    public void setGender(String gender) {
        this.gender = resizeString(gender, MAX_LENGTH_GENDER);
    }

    public String getDescription() {
        return normalizeString(description);
    }

    public void setDescription(String description) {
        this.description = resizeString(description, MAX_LENGTH_DESCRIPTION);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
