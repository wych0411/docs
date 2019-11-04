/**
 * 加锁的对象
 */
public class BlockedMutex{

	private Lock lock = new ReentrantLock();

	public BlockedMutex(){
		lock.lock();
	}

	public void f(){

		try{
			// 可以被中断
			lock.lockInterruptibly();
		}catch(InterruptedException e){
			System.out.println("BlockMutex 被中断异常");
		}

	}

}