public class DeadLockingDiningPhilosophers{

	public static void main(String[] args){

		int ponder = 5;
		int size = 5;

		ExecutorService exec = Executors.newCachedThreadPool();

		Chopstick[] sChopsticks = new Chopstick[size];
		for(int i-0;i<size;i++){
			sChopsticks[i] = new Chopstick();
		}

		for(int i=0;i<size;i++){
			//每一个哲学家都会持有他左边和右边的筷子对象
			exec.execute(new Philosopher(sChopsticks[i], sChopsticks[(i+1)%size], i, ponder));
		}
		System.out.println("执行结束");
		exec.shutdownNow();
	}
}