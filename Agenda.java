import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Agenda {
    public static void imprimeContatos() {        
        System.out.println(">>>> Contatos <<<<\n" +
            "Id | Nome");

        File arquivo = new File("database.txt");
        try {
            Scanner scanner = new Scanner(arquivo);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] linhaQuebrada = linha.split(",");
                Contato contato = new Contato();

                contato.setId(Long.valueOf(linhaQuebrada[0]).longValue());
                contato.setNome(linhaQuebrada[1]);
                contato.setSobrenome(linhaQuebrada[2]);

                System.out.println(contato.getId() + "  | " + contato.getNome() + " " + contato.getSobrenome());
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo.");
        }
    } 

    public static void imprimeMenu() {
        System.out.println("\n>>>> Menu <<<<\n" +
            "1 - Adicionar Contato\n" + 
            "2 - Remover Contato\n" + 
            "3 - Editar Contato\n" + 
            "4 - Sair\n");
    }

    public static void imprimeAgenda() {
        System.out.println("##################\n" + 
        "##### AGENDA #####\n" +
        "##################\n");
    }

    public static void salvaContato(Contato contato) {
        try {
            FileWriter arquivo = new FileWriter("database.txt", true);
            BufferedWriter writer = new BufferedWriter(arquivo);

            String novoContato = contato.getId() + "," + contato.getNome() + "," + contato.getSobrenome();
            writer.write(novoContato);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        imprimeAgenda();
        imprimeContatos();
        imprimeMenu();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Escolha uma opcao do menu: ");
            
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            Long id = 1L;
            System.out.print("Digite o nome do contato: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o sobrenome do contato: ");
            String sobrenome = scanner.nextLine();

            Contato contato = new Contato();
            contato.setId(id);
            contato.setNome(nome);
            contato.setSobrenome(sobrenome);

            salvaContato(contato);
        }
    }
}