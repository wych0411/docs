/**
 * Lock方式
 */
public class LockingTest extends Incrementable{

	private Lock lock = new ReentrantLock();

	@Override
	public void increment(){
		lock.lock();

		try{
			++counter;
		} finally {
			lock.unlock();
		}
	}
	
}