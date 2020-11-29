package aplicacao;

import Dados.Registro;
import hash.DinamicHash;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) throws Exception {

        final String[] nomes = new String[]{"maria", "joao"};
        final String[] sobrenomes = new String[]{"silva", "santos"};
        DinamicHash hash = null;
        boolean run = true;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("_____________menu_________________\n\n" +
                    "1-Criar hash\n" +
                    "2-Inserir registro\n" +
                    "3-Editar registro\n" +
                    "4-Remover registro\n" +
                    "5-Imprimir arquivos\n" +
                    "6-Simulação");

            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1: {
                    System.out.println("digite o caminho dos arquivos\n" +
                            "EX: C:\\Users\\{Seu usuario}\\Documents\n" +
                            "ou deixe vasio para o diretorio padrao");
                    String path = scanner.nextLine();
                    hash = new DinamicHash(path);
                    System.out.println("Arquivos criados" +
                            "\n");
                    break;
                }
                case 2: {
                    System.out.println("_____novo registro_____\n\n");
                    System.out.println("digite o nome");
                    String nome = scanner.nextLine();
                    System.out.println("digite o cpf");
                    int cpf = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("digite a data de nascimento\n" +
                            "dd/mm/yyyy");
                    String data = scanner.nextLine();
                    String[] datavet = data.split("/");
                    LocalDate localDate = LocalDate.of(Integer.parseInt(datavet[2]), Integer.parseInt(datavet[1]), Integer.parseInt(datavet[0]));
                    System.out.println("digite o genero \n" +
                            "(M/F/O)");
                    char gender = scanner.nextLine().toCharArray()[0];
                    long start = System.currentTimeMillis();
                    Registro registro = new Registro(nome, cpf, localDate, gender, "");
                    hash.add(registro);
                    long end = System.currentTimeMillis();
                    System.err.println("o tempo de dinserção: " + (end - start) + "ms");
                    break;
                }
                case 3:{
                    System.out.println("digite a chave do registro que deseja editar");
                    int key = scanner.nextInt();
                    scanner.nextLine();
                    long start = System.currentTimeMillis();
                    Registro registro = hash.find(key);
                    long end = System.currentTimeMillis();
                    System.err.println("Tempo de busca: "+ (end-start)+"ms");

                    if (registro != null) {
                        printReg(registro);
                        System.out.println("digite a nova descrição");
                        String description = scanner.nextLine();
                        registro.setDescription(description);
                        start = System.currentTimeMillis();
                        hash.edit(key,registro);
                        end = System.currentTimeMillis();
                        System.err.println("Tempo de update: "+ (end-start)+"ms");
                    }
                    else {
                        System.out.println("Registro nao encontrado");
                    }
                    break;
                }
                case 4:{
                    System.out.println("digite a chave do registro que deseja remover");
                    int key = scanner.nextInt();
                    long start = System.currentTimeMillis();
                    Registro registro = hash.remove(key);
                    long end = System.currentTimeMillis();
                    System.err.println("Tempo de remoção: "+ (end-start)+"ms");
                    printReg(registro);
                    break;
                }
            }
        }

        while (run);


    }

    static void printReg(Registro registro){
        if (registro != null) {
            System.out.printf("\n\n___________Registro__________\n\n" +
                            "Nome: %s\n" +
                            "CPF: %d\n" +
                            "Nascimeto: %s\n" +
                            "Genero: %c\n" +
                            "Descrição: %s\n",
                    registro.getName(),
                    registro.getCpf(),
                    registro.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    registro.getGender(),
                    registro.getDescription());
        }
    }


}

