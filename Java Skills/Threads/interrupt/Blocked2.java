/**
 * 任务
 */
public class Blocked2 implements Runnable{

	BlockedMutex mutex = new BlockedMutex();

	@Override
	public void run(){

		System.out.println("开始线程执行");
		mutex.f();
		System.out.println("被中断了");

	}
}