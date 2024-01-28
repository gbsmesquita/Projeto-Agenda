import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Agenda {

    // Imprime lista completa de contatos com Id e Nome.
    public static void imprimeContatos() {
        System.out.println(">>>> Contatos <<<<\n" +
            "Id | Nome");

        File arquivo = new File("contatos.txt");
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

    // Imprime menu de opções.
    public static void imprimeMenu() {
        System.out.println("\n>>>> Menu <<<<\n" +
            "1 - Adicionar Contato\n" +
            "2 - Remover Contato\n" +
            "3 - Editar Contato\n" +
            "4 - Sair\n");
    }

    // Imprime cabeçalho do programa.
    public static void imprimeAgenda() {
        System.out.println("##################\n" +
        "##### AGENDA #####\n" +
        "##################\n");
    }

    // Verifica se um telefone já existe no banco de dados.
    public static boolean telefoneJaExiste(String telefone) {
        File arquivo = new File("contatos.txt");


            try (Scanner scanner = new Scanner(arquivo)) {
                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] linhaQuebrada = linha.split(",");
                    String[] telefones = Arrays.copyOfRange(linhaQuebrada, 3, linhaQuebrada.length);

                    if (Arrays.asList(telefones).contains(telefone)) {
                        System.out.println("Já existe um contato com este telefone. O telefone não será salvo.");
                        return true;
                    }
                }

                scanner.close();
            } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo.");
        }
        return false;
    }

    // Adiciona um novo contato no banco de dados
    public static void salvaContato(Contato contato) {
        if (idJaExiste(contato.getId())) {
            return;
        }

        String telefones = "";
        for (int i = 0; i < contato.getTelefones().size(); i++) {
            Telefone telefoneAtual = contato.getTelefones().get(i);
            String telefoneAtualString = telefoneAtual.getDDD() + " " + telefoneAtual.getNumero();
            if (!telefoneJaExiste(telefoneAtualString)) {
                telefones += telefoneAtualString;

                if (i != contato.getTelefones().size() - 1) {
                    telefones += ",";
                }
            }
        }

        try {
            FileWriter arquivo = new FileWriter("contatos.txt", true);
            BufferedWriter writer = new BufferedWriter(arquivo);

            if (telefones.length() != 0) {
                String novoContato = contato.getId() + "," + contato.getNome() + "," + contato.getSobrenome() + "," + telefones;
                writer.write(novoContato);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar contato: " + e.getMessage());
        }
    }

    // Verifica se já existe algum contato com o mesmo id.
    public static boolean idJaExiste(Long id) {
        File arquivo = new File("contatos.txt");
        try (Scanner scanner = new Scanner(arquivo)) {
                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] linhaQuebrada = linha.split(",");
                    Contato contato = new Contato();
                    contato.setId(Long.valueOf(linhaQuebrada[0]).longValue());


                    if (id == contato.getId()) {
                        return true;
                    }
                }
                scanner.close();
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo.");
            return false;
        }
    }

    public static Long geraIdAleatorio() {
        Random random = new Random();
        Long id = 10000L + random.nextLong(90000L);

        return id;
    }

    // Procura um contato no banco de dados pelo id e remove do arquivo.
    public static void deletaContato(Long id) {
        File arquivo = new File("contatos.txt");
        File arquivoTemporario = new File("contatos_temp.txt");
        String idString = Long.toString(id);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemporario));
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] linhaQuebrada = currentLine.split(",");
                String idContatoAtual = linhaQuebrada[0];

                if (!idContatoAtual.equals(idString)) {
                    writer.write(currentLine + "\n");
                }
            }
            writer.close();
            reader.close();
        } catch(Exception e) {
            System.out.println("Erro ao deletar contato.");
        }

        boolean deletou = arquivo.delete();
        if (!deletou) {
            System.out.println("Falha ao deletar o arquivo contatos.txt. Verifique se o mesmo não se encontra aberto em algum editor.");
        }

        boolean renomeou = arquivoTemporario.renameTo(arquivo);
        if (!renomeou) {
            System.out.println("Falha ao renomear contatos_temp.txt.");
        }
    }

    // Verifica se uma string possui tamanho maior que zero, caso contrário lança exceção.
    public static void verificaString(String string) {
        if (string.length() <= 0) {
            throw new RuntimeException("Input inválido pois possui 0 caracteres.");
        }
    }

    // Procura um contato no banco de dados pelo Id e atualiza as informações
    public static void editaContato(Long id) {
        File arquivo = new File("contatos.txt");
        String idString = id.toString();
        Scanner scannerTerminal = new Scanner(System.in);

        try {
            Scanner scannerArquivo = new Scanner(arquivo);

            while (scannerArquivo.hasNextLine()) {
                String linha = scannerArquivo.nextLine();
                String[] linhaQuebrada = linha.split(",");
                String idAtual = linhaQuebrada[0];

                if (idAtual.equals(idString)) {
                    System.out.print("Digite o nome do contato: ");
                    String nome = scannerTerminal.nextLine();
                    verificaString(nome);

                    System.out.print("Digite o sobrenome do contato: ");
                    String sobrenome = scannerTerminal.nextLine();
                    verificaString(sobrenome);

                    System.out.print("Digite a quantidade de telefones: ");
                    int quantidadeTelefones = scannerTerminal.nextInt();
                    scannerTerminal.nextLine();
                    if (quantidadeTelefones <= 0) {
                        System.out.println("O contato deve possuir pelo menos 1 telefone.");
                    }

                    List<Telefone> telefones = new ArrayList<>();
                    for (int i = 0; i < quantidadeTelefones; i++) {
                        System.out.print("Digite o DDD do telefone: ");
                        String ddd = scannerTerminal.nextLine();
                        verificaString(ddd);

                        System.out.print("Digite o número do telefone: ");
                        Long numero = scannerTerminal.nextLong();
                        scannerTerminal.nextLine();

                        Telefone telefone = new Telefone();
                        Long idTelefone = geraIdAleatorio();
                        telefone.setId(idTelefone);
                        telefone.setDDD(ddd);
                        telefone.setNumero(numero);

                        telefones.add(telefone);
                    }

                    Contato contato = new Contato();
                    contato.setId(id);
                    contato.setNome(nome);
                    contato.setSobrenome(sobrenome);
                    contato.setTelefones(telefones);
                    scannerArquivo.close();

                    deletaContato(id);
                    salvaContato(contato);
                    scannerTerminal.close();
                    break;
                }
            }

            scannerArquivo.close();
            scannerTerminal.close();

        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo.");
            e.printStackTrace();
        }
    }

    // Inicio do programa.
    public static void main(String[] args) {
        imprimeAgenda();
        imprimeContatos();
        imprimeMenu();
        try {
            new File("contatos.txt").createNewFile();
        } catch (Exception e) {
            System.out.println("Erro ao carregar database.");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Escolha uma opcao do menu: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                Long id = geraIdAleatorio();
                while (idJaExiste(id)) {
                    id = geraIdAleatorio();
                }

                System.out.print("Digite o nome do contato: ");
                String nome = scanner.nextLine();
                verificaString(nome);

                System.out.print("Digite o sobrenome do contato: ");
                String sobrenome = scanner.nextLine();
                verificaString(sobrenome);

                System.out.print("Digite a quantidade de telefones: ");
                int quantidadeTelefones = scanner.nextInt();
                scanner.nextLine();

                List<Telefone> telefones = new ArrayList<>();

                for (int i = 0; i < quantidadeTelefones; i++) {
                    System.out.print("Digite o DDD do telefone: ");
                    String ddd = scanner.nextLine();
                    verificaString(ddd);

                    System.out.print("Digite o número do telefone: ");
                    Long numero = scanner.nextLong();
                    scanner.nextLine();

                    Telefone telefone = new Telefone();
                    Long idTelefone = geraIdAleatorio();
                    telefone.setId(idTelefone);
                    telefone.setDDD(ddd);
                    telefone.setNumero(numero);

                    telefones.add(telefone);
                }

                Contato contato = new Contato();
                contato.setId(id);
                contato.setNome(nome);
                contato.setSobrenome(sobrenome);
                contato.setTelefones(telefones);
                salvaContato(contato);
            } else if (opcao == 2) {
                System.out.print("Digite o id do usuário que deseja remover: ");
                Long id = scanner.nextLong();
                scanner.nextLine();

                deletaContato(id);
            } else if (opcao == 3) {
                System.out.print("Digite o id do usuário que deseja editar: ");
                Long id = scanner.nextLong();
                scanner.nextLine();

                editaContato(id);
            } else if (opcao == 4) {
                System.out.println("Até a próxima! :)");
            }
        }
    }
}
