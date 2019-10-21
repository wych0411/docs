public class SettingDefaultHandler{

	public static void main(String[] args){
		Thread.setDefaultUncaughtExceptionHandler(
			new MyUncaughtExceptionHandler()
		);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new ExceptionThread());
	}
}