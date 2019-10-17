public class SingleThreadPool{

	public static void main(String[] args){

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for(int i = 0;i < 3;i++){
			executorService.execute(new LiftOff());
		}
		executorService.shutdown();
	}
}