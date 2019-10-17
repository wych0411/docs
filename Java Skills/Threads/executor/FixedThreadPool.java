public class FixedThreadPool{
	
	public static void main(String[] args){

		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for(int i = 0;i < 3;i++){
			executorService.execute(new LiftOff());
		}
		executorService.shutdown();
	}
}