public class SleepTask extends LiftOff{

	public void run(){

		try{

			while(countDown-- > 0){
				System.out.print(status());
				//Thread.sleep(100);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}catch(InterruptedException e){
			System.err.println("interrupted");
		}
	}

	public static void main(String[] args){

		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i = 0;i < 5;i++){
			executorService.execute(new SleepTask());
		}
		executorService.shutdown();
	}
}