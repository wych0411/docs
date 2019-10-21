public class HandlerThreadFactory implements ThreadFactory{

	public Thread newThread(Runnable r){
		System.out.println("创建一个新的线程");

		Thread thread = new Thread(r);
		thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("添加异常捕获结束");
		return thread;
	}
}