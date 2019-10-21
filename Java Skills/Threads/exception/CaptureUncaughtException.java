public class CaptureUncaughtException{

	public static void main(String[] args){
		ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
		executorService.execute(new ExceptionThread());
	}
}