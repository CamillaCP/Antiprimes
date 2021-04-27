package antiprimes;


public class NumberProcessor extends Thread {
	
	private AntiPrimesSequence currentSequence;
	private Number request = null;
	
	public NumberProcessor(AntiPrimesSequence seq) {
		this.currentSequence = seq;
	}
	
	public void run() { 
		acceptRequests();
		
		while(true) {
			try {
				Number n = getNextToProcess();
				Number next = AntiPrimes.nextAntiPrimeAfter(n);
				currentSequence.addAntiPrime(next);
				acceptRequests();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
			
		}
	}
	
	synchronized public void nextAntiPrime(Number n) throws InterruptedException {
		while (request != null) {
            if (request.getValue() == n.getValue())
                return;
            wait();
        }
        request = n;
        notify();
    }
		
	
	
	synchronized public Number getNextToProcess() throws InterruptedException {
		while (request == null) 
			wait();
		return request;
	}
	
	synchronized public void acceptRequests() {
		request = null;
		notify();
	}
 }
