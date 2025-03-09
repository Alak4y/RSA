package com.lecc.rsa;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    // Definição dos valores primos
    private static final BigInteger p = BigInteger.valueOf(29);
    private static final BigInteger q = BigInteger.valueOf(23);
    private static final BigInteger n = p.multiply(q); // n = p * q
    private static final BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // φ(n) = (p-1)(q-1)
    private static final BigInteger e = BigInteger.valueOf(5); // Escolha de 'e'
    private static final BigInteger d = e.modInverse(phi); // d = e⁻¹ mod φ(n)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Criptografar mensagem");
            System.out.println("2 - Descriptografar mensagem");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    criptografar(scanner);
                    break;
                case 2:
                    descriptografar(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void criptografar(Scanner scanner) {
        System.out.println("\n--- Criptografia RSA ---");
        System.out.println("Passo 1: Escolha de valores primos p e q");
        System.out.println("  p = " + p);
        System.out.println("  q = " + q);

        System.out.println("Passo 2: Cálculo de n e φ(n)");
        System.out.println("  n = p * q = " + n);
        System.out.println("  φ(n) = (p-1) * (q-1) = " + phi);

        System.out.println("Passo 3: Escolha de e e cálculo de d");
        System.out.println("  e = " + e);
        System.out.println("  d = e⁻¹ mod φ(n) = " + d);

        System.out.println("\n🔑 Chave Pública: (" + e + ", " + n + ")");
        System.out.println("🔒 Chave Privada: (" + d + ", " + n + ")");

        System.out.println("\nTabela de mapeamento (Caractere → ASCII)");
        System.out.print("Digite a mensagem para criptografar: ");
        String mensagem = scanner.nextLine();

        // Converter para números baseados em ASCII
        StringBuilder numeros = new StringBuilder();
        for (char c : mensagem.toCharArray()) {
            int valor = (int) c; // Converte o caractere para seu valor ASCII
            numeros.append(valor).append(" ");
        }
        System.out.println("Mensagem convertida em números (ASCII): " + numeros);

        // Criptografar cada número
        StringBuilder criptografado = new StringBuilder();
        for (String num : numeros.toString().split(" ")) {
            if (!num.isEmpty()) {
                BigInteger m = new BigInteger(num);
                BigInteger c = m.modPow(e, n);
                criptografado.append(c).append(" ");
                System.out.println("  Criptografando " + m + " → " + c);
            }
        }
        System.out.println("🔐 Mensagem criptografada: " + criptografado);
    }

    private static void descriptografar(Scanner scanner) {
        System.out.println("\n--- Descriptografia RSA ---");

        // O usuário deve inserir a chave privada
        System.out.print("Digite o valor de d (chave privada): ");
        BigInteger dUser = new BigInteger(scanner.nextLine());

        System.out.print("Digite o valor de n: ");
        BigInteger nUser = new BigInteger(scanner.nextLine());

        System.out.print("Digite a mensagem criptografada (números separados por espaço): ");
        String criptografado = scanner.nextLine();

        // Descriptografar cada número
        StringBuilder numeros = new StringBuilder();
        StringBuilder texto = new StringBuilder();
        for (String num : criptografado.split(" ")) {
            if (!num.isEmpty()) {
                BigInteger c = new BigInteger(num);
                BigInteger m = c.modPow(dUser, nUser);
                numeros.append(m).append(" ");
                texto.append((char) m.intValue()); // Converte o valor de volta para o caractere ASCII
                System.out.println("  Descriptografando " + c + " → " + m);
            }
        }
        System.out.println("📜 Números descriptografados (ASCII): " + numeros);
        System.out.println("✉️ Mensagem original: " + texto);
    }
}
