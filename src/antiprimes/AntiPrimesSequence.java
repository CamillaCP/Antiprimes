package antiprimes;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent the sequence of antiprimes found so far.
 */
public class AntiPrimesSequence {

    /**
     * The numbers in the sequence.
     */
    private List<Number> antiPrimes = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private NumberProcessor numberProc;

    /**
     * Create a new sequence containing only the first antiprime (the number '1').
     */
    public AntiPrimesSequence() {
    	numberProc = new NumberProcessor(this);
        this.reset();
        numberProc.start();
    }

    /**
     * Clear the sequence so that it contains only the first antiprime (the number '1').
     */
    public void reset() {
        antiPrimes.clear();
        antiPrimes.add(new Number(1, 1));
    }

    public void addObserver(Observer obs) {
    	observers.add(obs);
    }
    
    /**
     * Find a new antiprime and add it to the sequence.
     */
    public void computeNext() {
       try {
    	   numberProc.nextAntiPrime(getLast());
       } catch (InterruptedException e) {
    	   e.printStackTrace();
       }
    }

    /**
     * Return the last antiprime found.
     */
    synchronized public Number getLast() {
        int n = antiPrimes.size();
        return antiPrimes.get(n - 1);
    }

    /**
     * Return the last k antiprimes found.
     */
    synchronized public List<Number> getLastK(int k) {
        int n = antiPrimes.size();
        if (k > n)
            k = n;
        return antiPrimes.subList(n - k, n);
    }
    
    public void addAntiPrime(Number ap) {
    	antiPrimes.add(ap);
    	for (Observer observer : observers) {
    		observer.update();
    	}
    }
}
