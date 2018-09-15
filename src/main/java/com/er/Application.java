package com.er;

import java.util.*;

public class Application {

    private static Set<Long> primeNumbers = new TreeSet<>();
    private static Integer counter = 0;

    public static void main(String[] args) {
        // 10 pow 9
        int n = 1000000000;

        primeNumbers.add(2L);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            Long input = scanner.nextLong();
            counter = 0;
            if(isPrime(input, true)) {
                System.out.println("Value "+input+" is prime");
                System.out.println(input+" "+counter);
            } else {
                boolean condition = false;
                while(!condition) {
                    List<Long> primeFactors = calculatePrimeFactors(input);
                    Long sum = 0L;
                    for(Long eachFactor: primeFactors) sum += eachFactor;
                    if(isPrime(sum, true)) {
                        System.out.println("Value "+sum+" is prime");
                        System.out.println(sum+" "+counter);
                        condition = true;
                    } else input = sum;
                }
            }
        }
    }

    private static List calculatePrimeFactors(Long input) {
        List<Long> primeFactors = new ArrayList<>();

        while(input > 1) {
            Long inputFinal = input;
            Optional<Long> primeFactor = primeNumbers.stream().filter(number -> inputFinal % number == 0).findFirst();
            if(primeFactor.isPresent()) {
                primeFactors.add(primeFactor.get());
                input = input / primeFactor.get();
            } else searchMorePrimeNumbers();
        }

        return primeFactors;
    }

    private static void searchMorePrimeNumbers() {
        Long lastPrimeNumber = getLastPrimeNumber();
        for(long i = lastPrimeNumber + 1; i < Math.pow(lastPrimeNumber, 2); i++) {
            System.err.println("Examining number:" +i);
            if(isPrime(i)) primeNumbers.add(i);
        }
        System.out.println(primeNumbers);
    }

    private static Long getLastPrimeNumber() {
        Object[] primeNumbersArray = primeNumbers.toArray();
        return (Long) primeNumbersArray[primeNumbersArray.length-1];
    }

    public static boolean isPrime(Long number, Boolean accountable) {
        if(accountable) counter++;
        for(int i = 2; i < number; i++) {
            if(number % i == 0) return false;
        }
        return true;
    }

    public static boolean isPrime(Long number) {
        return isPrime(number, false);
    }

    //checks whether an int is prime or not.
//    public static boolean enhancedIsPrime(Integer number) {
//        for(int i  = 2; 2*i < number; i++) {
//            if(number % i ==0 )
//                return false;
//        }
//        System.out.println("Value "+number+" is prime");
//        enhancedPrimeNumbers.add(number);
//        return true;
//    }

    //checks whether an int is prime or not.
//    public static boolean superEnhancedIsPrime(int n) {
//        //check if n is a multiple of 2
//        if (n%2==0) return false;
//        //if not, then just check the odds
//        for(int i=3;i*i<=n;i+=2) {
//            if(n%i==0)
//                return false;
//        }
//        System.out.println("Value "+n+" is prime");
//        superEnhancedPrimeNumbers.add(n);
//        return true;
//    }


}
