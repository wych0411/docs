public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler{

	public void uncaughtException(Thread t, Throwable e){
		System.out.println("自定义的异常捕获" + e);
	}
}