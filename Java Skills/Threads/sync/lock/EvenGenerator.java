public class EvenGenerator extends IntGenerator{

	private int currentEvenValue = 0;

	//创建锁
	private Lock lock = new ReentrantLock();

	@Override
	public int next(){
		//锁定
		lock.lock();
		try{
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;

			return currentEvenValue;
		}finally{
			//计算完毕后释放锁
			lock.unlock();
		}
	}

	public static void main(String[] args){
		EvenChecker.test(new EvenGenerator());
	}
}