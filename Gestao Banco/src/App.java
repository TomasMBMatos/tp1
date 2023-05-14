import java.util.Scanner;

public class App {
    String username;
    String password;
    Cliente cliente;
    Banco banco = new Banco();

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

                    boolean registo = banco.registar(username, password);

                    if (registo) {
                        menuBanco(in, username);
                    }
                    break;
                case "2":
                    System.out.println("Nome de utilizador:");
                    username = in.nextLine();

                    System.out.println("Password:");
                    password = in.nextLine();

                    boolean login = banco.login(username, password);

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
        String montante;

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
                    System.out.println("Escolha a conta:");

                case "5":
                    System.out.println("Número da conta:");
                    System.out.println(cliente.getConta().getNumConta());
                case "6":
                    System.out.println("Montante que deseja depositar:");
                    montante = in.nextLine();
                    cliente.getConta().depositar(Double.parseDouble(montante));
                    System.out.println("Depositou: " + montante);
                case "7":
                    System.out.println("Montante que deseja levantar:");
                    montante = in.nextLine();
                    cliente.getConta().levantar(Double.parseDouble(montante));
                    System.out.println("Levantou: " + montante);
                case "8":
                    System.out.println("Saldo: " + cliente.getConta().getSaldo());
                case "9":
                    double saldoTotal = 0;
                    for(Conta cont : cliente.getContas()) {
                        saldoTotal += cont.getSaldo();
                    }
                    System.out.println("Saldo total: " + saldoTotal);
                case "0":
                    break;
            }
        } while(!option.equals("0"));
    }

    private Conta escolhaContas(Scanner in, String username) {
        String deposito;
        String option;
        String data;

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
                    data = in.nextLine();
                    return new ContaPoupancaHabitacao(deposito, data);
                case "2":
                    return new ContaOrdem(deposito);
                case "3":
                    return new ContaMultibanco(deposito);
                case "4":
                    data = in.nextLine();
                    return new ContaPrazo(deposito, data);
                case "0":
                    menuBanco(in, username);
                default:
                    System.out.println("Escolha uma opção do menu");
            }
        } while(!option.equals("0"));
        return null;
    }

    public static void main(String[] args) {
        new App().menu();
    }
}
