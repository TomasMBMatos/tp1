import java.util.Scanner;

public class App {
    String username;
    String password;
    Cliente cliente;

    private void menu() {
        Scanner in = new Scanner(System.in);
        String option;

        do {
            System.out.println("--------------- Bem-vindo ao seu banco ----------------");
            System.out.println("1 - Registar");
            System.out.println("2 - Login");
            System.out.println("0 - Terminar");
            String opt = in.nextLine();

            if(opt != null && opt.length() > 0)
                option = opt;
            else
                option = " ";

            switch (option) {
                case "1":
                    System.out.println("Nome de utilizador:");
                    username = in.nextLine();

                    System.out.println("Password:");
                    password = in.nextLine();

                    boolean registo = registar(username, password);

                    if (registo) {
                        menuBanco(in, username);
                    }
                    break;
                case "2":
                    System.out.println("Nome de utilizador:");
                    username = in.nextLine();

                    System.out.println("Password:");
                    password = in.nextLine();

                    boolean login = login(username, password);

                    if (login) {
                        menuBanco(in, username);
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Escolha uma opção do menu.");
            }
        } while(!option.equals("0"));
    }

    private void menuBanco(Scanner in, String username) {
        String option;

        do {
            System.out.printf("--------------- Bem-vindo %s ---------------- \n", username);
            System.out.println("1 - Modificar data");
            System.out.println("2 - Saber data");
            System.out.println("3 - Criar conta");
            System.out.println("4 - Selecionar conta");
            System.out.println("5 - Saber o número da conta selecionada");
            System.out.println("6 - Depositar");
            System.out.println("7 - Levantar");
            System.out.println("8 - Saber saldo");
            System.out.println("9 – Saber total dos saldos das contas");
            System.out.println("0 - Terminar");
            String opt = in.nextLine();

            if(opt != null && opt.length() > 0)
                option = opt;
            else
                option = " ";

            switch (option) {
                case "1", "2":
                    System.out.println("Por implementar...");
                    menuBanco(in, username);
                case "3":
                    String nome;
                    String apelido;
                    String cc;
                    Conta conta;
                    System.out.println("Primeiro nome:");
                    nome = in.nextLine();
                    System.out.println("Último nome:");
                    apelido = in.nextLine();
                    System.out.println("NIF:");
                    cc = in.nextLine();

                    conta = escolhaContas(in, username);
                    cliente = new Cliente(nome, apelido, cc, conta);

                    System.out.println("Conta criada com sucesso!");

                    menuBanco(in, username);
                case "4":

                case "5":

                case "6":

                case "7":

                case "8":

                case "9":

                case "0":

            }
        } while(!option.equals("0"));
    }

    private Conta escolhaContas(Scanner in, String username) {
        String deposito;
        String option;

        do {
            System.out.printf("--------------- Bem-vindo %s ---------------- \n", username);
            System.out.println("1 - Conta Poupança-Habitação");
            System.out.println("2 - Conta á ordem");
            System.out.println("3 - Multibanco");
            System.out.println("4 - Conta a prazo");
            System.out.println("0 - Cancelar");
            String opt = in.nextLine();

            if(opt != null && opt.length() > 0)
                option = opt;
            else
                option = " ";

            System.out.println("Depósito inicial:");
            deposito = in.nextLine();
            switch (option) {
                case "1":
                    return new ContaPoupancaHabitacao();
                case "2":
                    return new ContaOrdem();
                case "3":
                    return new ContaMultibanco();
                case "4":
                    return new ContaPrazo();
                case "0":
                    menuBanco(in, username);
                default:
                    System.out.println("Escolha uma opção do menu");
            }
        } while(!option.equals("0"));
        return null;
    }

    public boolean registar(String username, String password) {
        return false;
    }

    public boolean login(String username, String password) {
        return true;
    }

    public static void main(String[] args) {
        new App().menu();
    }
}
