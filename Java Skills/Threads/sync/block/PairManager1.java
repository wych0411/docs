public class PairManager1 extends PairManager{

	// 在方法体上修饰表明方法是同步控制的
	@Override
	public synchronized void increment(){
		// 递增和递减是非线程安全的
		p.incrementX();
		p.incrementY();
		store(getPair());
	}
}