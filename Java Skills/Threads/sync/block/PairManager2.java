public class PairManager2 extends PairManager{

	@Override
	public void increment(){
		Pair temp;

		// 同步代码块，计算完毕之后赋值
		synchronized(this){
			p.incrementX();
			p.incrementY();
			temp = getPair();
		}

		store(temp);
	}
}