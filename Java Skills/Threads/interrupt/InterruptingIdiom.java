/**
 * 创建测试类
 */
public class InterruptingIdiom{

	public static void main(String[] args) throws Exception{

		Thread thread = new Thread(new Blocked3());
		thread.start();

		TimeUnit.MICROSECONDS.sleep(3);
		thread.interrupt();

	}

}