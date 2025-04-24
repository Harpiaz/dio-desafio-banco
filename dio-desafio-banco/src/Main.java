import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco harpyBank = new Banco("Harpy Bank", 1234);
        Agencia agenciaDigital = harpyBank.adicionarAgencia("Digital", 1);
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------------------------------------------");
        System.out.println("HARPY BANK - SISTEMA DE CADASTRO E TRANSACOES");
        System.out.println("--------------------------------------------------------");

        int opcao = 0;

        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Pesquisar Cliente por CPF");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Abrir Conta");
            System.out.println("4 - Depositar");
            System.out.println("5 - Sacar");
            System.out.println("6 - Transferir");
            System.out.println("7 - Imprimir Saldo");
            System.out.println("8 - Imprimir Extrato");
            System.out.println("0 - Sair");
            System.out.println("--------------------------------------------------------");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            String nome;
            String cpf;
            String numeroConta;
            double valor;
            
            switch (opcao) {
                case 1:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    pesquisarCliente(harpyBank, cpf);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o nome do cliente: ");
                    nome = scanner.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    cadastrarCliente(agenciaDigital, nome, cpf);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 3:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    if (pesquisarCliente(harpyBank, cpf)) {
                        System.out.print("Digite o tipo de conta (C para Corrente, P para Poupança): ");
                        char tipo = scanner.nextLine().charAt(0);
                        abrirConta(agenciaDigital, cpf, tipo);
                    }
                    System.out.println("--------------------------------------------------------");
                    break;
                case 4:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    System.out.print("Digite o valor a depositar: ");
                    valor = scanner.nextDouble();
                    scanner.nextLine(); // Limpar o buffer
                    depositar(harpyBank, numeroConta, valor);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 5:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    System.out.print("Digite o valor a sacar: ");
                    valor = scanner.nextDouble();
                    scanner.nextLine(); // Limpar o buffer
                    sacar(harpyBank, numeroConta, valor);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 6:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o número da conta de origem: ");
                    String numeroOrigem = scanner.nextLine();
                    System.out.print("Digite o número da conta de destino: ");
                    String numeroDestino = scanner.nextLine();
                    System.out.print("Digite o valor a transferir: ");
                    valor = scanner.nextDouble();
                    scanner.nextLine(); // Limpar o buffer
                    transferir(harpyBank, numeroOrigem, numeroDestino, valor);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 7:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    imprimirSaldo(harpyBank, numeroConta);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 8:
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    imprimirExtrato(harpyBank, numeroConta);
                    System.out.println("--------------------------------------------------------");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    public static boolean pesquisarCliente(Banco banco, String cpf) {
        if (banco.getClientes().containsKey(cpf)) {
            Cliente cliente = banco.getClientes().get(cpf);
            System.out.println();
            System.out.println(cliente);
            System.out.println();
            System.out.println("Contas do cliente:");
            cliente.getContas().forEach((numero, conta) -> System.out.println(conta));
            return true;
        } else {
            System.out.println();
            System.out.println("Cliente não encontrado.");
            return false;
        }
    }

    public static void cadastrarCliente(Agencia agencia, String nome, String cpf) {
        if (agencia.getBanco().getClientes().containsKey(cpf)) {
            System.out.println();
            System.out.println("Cliente já cadastrado.");
        } else {
            agencia.cadastrarCliente(nome, cpf);
            System.out.println();
            System.out.println("Cliente cadastrado com sucesso");
        }
    }

    public static void abrirConta(Agencia agencia, String cpf, char tipo) {
        try {
            Cliente cliente = agencia.getBanco().getClientes().get(cpf);
            agencia.abrirConta(cliente, tipo);
            System.out.println();
            System.out.println("Conta aberta com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
        
    }

    public static void depositar(Banco banco, String numero, double valor) {
        Conta conta = validarConta(banco, numero);
        if (conta != null) {
            conta.depositar(valor, false);
            System.out.println();
            System.out.println("Depósito realizado com sucesso.");
        }
    }

    public static void sacar(Banco banco, String numero, double valor) {
        Conta conta = validarConta(banco, numero);
        if (conta != null) {
            conta.sacar(valor, false);
            System.out.println();
            System.out.println("Saque realizado com sucesso.");
        }
    }

    public static void transferir(Banco banco, String numeroOrigem, String numeroDestino, double valor) {
        Conta contaOrigem = validarConta(banco, numeroOrigem);
        Conta contaDestino = validarConta(banco, numeroDestino);
        if (contaOrigem != null && contaDestino != null) {
            if (contaOrigem.getSaldo() < valor) {
                System.out.println("Saldo insuficiente para transferência.");
                return;
            }
            contaOrigem.sacar(valor, true);
            contaDestino.depositar(valor, true);
            System.out.println();
            System.out.println("Transferência realizada com sucesso.");
        }
    }

    public static void imprimirSaldo(Banco banco, String numero) {
        Conta conta = validarConta(banco, numero);
        if (conta != null) {
            conta.imprimirSaldo();
        }
    }

    public static void imprimirExtrato(Banco banco, String numero) {
        Conta conta = validarConta(banco, numero);
        if (conta != null) {
            conta.imprimirExtrato();
        }
    }
    
    private static Conta validarConta(Banco banco, String numero) {
        Conta conta = banco.getContas().get(numero);
        if (conta == null)
            System.out.println("Conta não encontrada.");
        return conta;
    }
}
