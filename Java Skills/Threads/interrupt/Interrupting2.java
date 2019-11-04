/**
 * 线程启动任务，并中断
 */
public class Interrupting2{

	public static void main(String[] args) throws Exception{

		Thread thread = new Thread(new Blocked2());
		thread.start();

		TimeUnit.SECONDS.sleep(1);
		System.out.println("线程调用 interrupt()");
		thread.interrupt();

	}

}