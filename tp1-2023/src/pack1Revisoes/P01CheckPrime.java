package pack1Revisoes;

import java.io.IOException;
import java.util.Scanner;

public class P01CheckPrime {

    /**
     * Main, método de arranque da execução
     */
    public static void main(String[] args)  {
        Scanner in = new Scanner(System.in);
        System.out.println("Intruduza um número inteiro:");
        int number = in.nextInt();
        if(isPrime(number)) {
            System.out.println("O número " + number + " é primo");
        }
        else {
            System.out.println("O número " + number + " não é primo");
        }
    }

    public static boolean isPrime(int number) {
        int i = number-1;
        while(i > 1) {
            if(number % i == 0) return false;
            i--;
        }
        return true;
    }
}
