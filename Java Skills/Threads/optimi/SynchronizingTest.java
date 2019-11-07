/**
 * synchronized方式
 */
public class SynchronizingTest extends Incrementable{

	@Override
	public synchronized void increment(){
		++counter;
	}

}