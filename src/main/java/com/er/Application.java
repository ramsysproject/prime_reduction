package com.er;

import java.util.*;

public class Application {

    private static Set<Long> primeNumbers = new TreeSet<>();
    private static Integer counter = 0;
    private static final String NUMBER_LENGTH_ERROR = String.format("The given input is higher than the maximum supported value - %s", Math.pow(10,9));
    private static final String NUMBER_RANGE_ERROR = "The given input must be greater than 0";
    private static final String RESULT_FORMAT = "%d %d";

    public static void main(String[] args) {
        primeNumbers.add(2L);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            Long input = scanner.nextLong();
            runValidations(input);
            counter = 0;
            if(isPrime(input, true)) {
                System.out.println(String.format(RESULT_FORMAT, input, counter));
            } else {
                boolean condition = false;
                while(!condition) {
                    List<Long> primeFactors = calculatePrimeFactors(input);
                    Long sum = 0L;
                    for(Long eachFactor: primeFactors) sum += eachFactor;
                    if(isPrime(sum, true)) {
                        System.out.println(String.format(RESULT_FORMAT, sum, counter));
                        condition = true;
                    } else input = sum;
                }
            }
        }
    }

    /**
     * This method takes an input Long value and calculates it´s prime factors
     * For that it´ll check the value against a Collection of ordered prime numbers
     * If the input value has not reached 1 and the prime numbers to evaluate has exhausted, it´ll start a process
     * method to search more prime numbers. This allows only to find more prime numbers when necessary.
     * @param input The value of which we want to obtain it´s prime factors
     * @return A List containing the input prime factors
     */
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

    /**
     * This method is executed when we need to obtain more prime numbers to evaluate inputs
     */
    private static void searchMorePrimeNumbers() {
        Long lastPrimeNumber = getLastPrimeNumber();
        for(long i = lastPrimeNumber + 1; i < lastPrimeNumber * 2; i++) {
            if(isPrime(i)) primeNumbers.add(i);
        }
        System.err.println(primeNumbers);
    }

    /**
     * This method retrieves the last prime number from the Collection
     * @return The last prime number in the Collection
     */
    private static Long getLastPrimeNumber() {
        Object[] primeNumbersArray = primeNumbers.toArray();
        return (Long) primeNumbersArray[primeNumbersArray.length-1];
    }

    /**
     * Overloaded method to call when it´s not necessary to count how many times the process has tried to determine if
     * the input is a prime number. Necessary to reutilize the same method when we search for more prime numbers.
     * @param number The number to evaluate
     * @return If the input value is a prime number
     */
    private static boolean isPrime(Long number) {
        return isPrime(number, false);
    }

    /**
     * This method executes calculations to determine if a number is prime. It first perform a quick check if the number
     * is multiple of 2. We analyze up to the sqrt of the input value.
     * @param n The number to evaluate
     * @param accountable Indicated if we need to increment counter, which represents number of times we evaluated
     *                    if a number is prime during the prime reduction process.
     * @return A Boolean flag indicating if it´s prime or not
     */
    private static boolean isPrime(Long n, Boolean accountable) {
        if(accountable) counter++;
        if (n == 1) return false;
        if (n % 2 == 0) return false;
        //if not, then just check the odds
        for(int i = 3; i*i <= n; i += 2) {
            if(n % i==0)
                return false;
        }
        return true;
    }

    private static void runValidations(Long input) {
        if(input > 1000000000) throw new IllegalArgumentException(NUMBER_LENGTH_ERROR);
        if(input < 0) throw new IllegalArgumentException(NUMBER_RANGE_ERROR);
        if(input == 4) System.exit(0);
    }
}
