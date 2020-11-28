package Menu;

import java.util.Scanner;

public class Menu {public static void main(String[] args) {

    Scanner objeto = new Scanner(System.in);
    boolean menu = true;

    while (menu==true){
        
        System.out.println("Escolha:");
        int key = objeto.nextInt();
        switch (key){

            case 0://Saida do Menu
                menu=false;
                System.out.println("Encerrando...");
                break;

            case 1://Inserir Registro

                break;
            case 2://Editar Registro

                break;
            case 3://Remover Registro

                break;
            case 4://Imprimir Registro

                break;

            default:
                System.out.println("Comando Inválido!");
                break;

        }
    }
}
}
