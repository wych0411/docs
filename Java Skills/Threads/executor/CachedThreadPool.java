public class CachedThreadPool{

	public static void main(String[] args){

		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i = 0;i < 3;i++){
			executorService.execute(new LiftOff());
		}
		executorService.shutdown();
	}
}