package logic;

import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {


    protected String username, password;
    public Banco banco = new Banco();
    public Conta conta;
    protected final ArrayList<Conta> contas = new ArrayList<>();

    public Cliente() {

    }

    public Conta getConta() {
        return conta;
    }

    public String getUsername() {
        return username;
    }

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

                    banco.registar(this);

                    menuBanco(in, username);

                    break;
                case "2":
                    System.out.println("Nome de utilizador:");
                    username = in.nextLine();

                    System.out.println("Password:");
                    password = in.nextLine();

                    boolean login = banco.login(this);

                    if (login) {
                        conta = banco.getConta(this, 0);
                        menuBanco(in, username);
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Escolha uma op��o do menu.");
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
            System.out.println("5 - Saber o n�mero da conta selecionada");
            System.out.println("6 - Depositar");
            System.out.println("7 - Levantar");
            System.out.println("8 - Saber saldo");
            System.out.println("9 � Saber total dos saldos das contas");
            System.out.println("0 - Terminar");
            String opt = in.nextLine();

            if(opt != null && opt.length() > 0)
                option = opt;
            else
                option = " ";

            switch (option) {
                case "1", "2":
                    System.out.println("Por implementar...");
                    break;
                case "3":
                    String nome;
                    String apelido;
                    String cc;
                    System.out.println("Primeiro nome:");
                    nome = in.nextLine();
                    System.out.println("�ltimo nome:");
                    apelido = in.nextLine();
                    System.out.println("NIF:");
                    cc = in.nextLine();

                    conta = escolhaContas(in, username, nome, apelido, cc);


                    System.out.println("logic.Conta criada com sucesso!");

                    break;
                case "4":
                    System.out.println("Escolha a conta:");
                    for(int i=0;i<banco.getContas(this).getLength();i++) {

                        contas.add(banco.getConta(this, i));
                        System.out.println(banco.getConta(this, i));
                    }
                    String index = in.nextLine();
                    this.conta = contas.get(Integer.parseInt(index)-1);
                    System.out.println("logic.Conta escolhida:" + conta);
                    break;
                case "5":
                    System.out.println("N�mero da conta:");
                    System.out.println(conta.getNumConta());
                    break;
                case "6":
                    System.out.println("Montante que deseja depositar:");
                    montante = in.nextLine();
                    banco.depositar(Double.parseDouble(montante), getConta());
                    System.out.println("Depositou: " + montante);
                    break;
                case "7":
                    System.out.println("Montante que deseja levantar:");
                    montante = in.nextLine();
                    boolean levantamento = banco.levantar(Double.parseDouble(montante), getConta());
                    if(levantamento)
                        System.out.println("Levantou: " + montante);
                    else
                        System.out.println("N�o � poss�vel fazer o levantamento de momento.");
                    break;
                case "8":
                    System.out.println("Saldo: " + banco.getSaldo(conta));
                    break;
                case "9":
                    double saldoTotal = banco.getSaldoTotal(this);
                    System.out.println("Saldo total: " + saldoTotal);
                    break;
                case "0":
                    break;
            }
        } while(!option.equals("0"));
    }

    private Conta escolhaContas(Scanner in, String username, String nome, String apelido, String cc) {
        String deposito;
        String option;
        String data;

        do {
            System.out.printf("--------------- Bem-vindo %s ---------------- \n", username);
            System.out.println("1 - logic.Conta Poupan�a-Habita��o");
            System.out.println("2 - logic.Conta � ordem");
            System.out.println("3 - Multibanco");
            System.out.println("4 - logic.Conta a prazo");
            System.out.println("0 - Cancelar");
            String opt = in.nextLine();

            if(opt != null && opt.length() > 0)
                option = opt;
            else
                option = " ";
            Conta aux;
            switch (option) {
                case "1":
                    System.out.println("Data (yyyy-MM-dd):");
                    data = in.nextLine();
                    aux = new ContaPoupancaHabitacao(data, nome, apelido, cc);
                    banco.addConta(this, aux);
                    return aux;
                case "2":
                    System.out.println("Dep�sito inicial:");
                    deposito = in.nextLine();
                    aux = new ContaOrdem(deposito, nome, apelido, cc);
                    banco.addConta(this, aux);
                    return aux;
                case "3":
                    System.out.println("Dep�sito inicial:");
                    deposito = in.nextLine();
                    aux = new ContaMultibanco(deposito, nome, apelido, cc);
                    banco.addConta(this, aux);
                    return aux;
                case "4":
                    System.out.println("Data (yyyy-MM-dd):");
                    data = in.nextLine();
                    aux = new ContaPrazo(data, nome, apelido, cc);
                    banco.addConta(this, aux);
                    return aux;
                case "0":
                    menuBanco(in, username);
                default:
                    System.out.println("Escolha uma op��o do menu");
            }
        } while(!option.equals("0"));
        return null;
    }

    public static void main(String[] args) {
        new Cliente().menu();
    }
}
