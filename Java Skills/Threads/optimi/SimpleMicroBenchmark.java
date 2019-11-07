/**
 * 测试synchronized vs lock
 */
public class SimpleMicroBenchmark{

	static long test(Incrementable incrementable){
		long start = System.nanoTime();
		for(int i=0;i<10000000L;i++){
			incrementable.increment();
		}
		return System.nanoTime()-start;
	}

	public static void main(String[] args){

		long synchTime = test(new SynchronizingTest());
		long lockTime = test(new LockingTest());

		System.out.println("Synchronizing: %1$10d\n", synchTime);
		System.out.println("lockTime: %1$10d\n", lockTime);
	}
}