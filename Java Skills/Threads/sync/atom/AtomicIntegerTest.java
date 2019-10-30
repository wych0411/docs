public class AtomicIntegerTest implements Runnable{

	private AtomicInteger ger = new AtomicInteger(0);

	public int getValue(){
		return ger.get();
	}

	private void evenIncrement(){
		ger.addAndGet(2);
	}

	@Override
	public void run(){
		while(true){
			evenIncrement();
		}
	}

	public static void main(String[] args){

		ExecutorService exec = Executors.newCachedThreadPool();

		AtomicIntegerTest aIntegerTest = new AtomicIntegerTest();
		exec.execute(aIntegerTest);
		while(true){
			int val = aIntegerTest.getValue();
			if(val % 2 != 0){
				System.out.println(val);
				System.exit(0);
			}	
		}
	}
}