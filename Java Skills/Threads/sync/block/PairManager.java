public abstract class PairManager{

	//线程安全的
	AtomicInteger checkCounter = new AtomicInteger(0);

	protected Pair p = new Pair();

	//集合也是线程安全的
	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

	//方法是线程安全的
	public synchronized Pair getPair(){
		// Make a copy to keep the original safe:
		return new Pair(p.getX(), p.getY());
	}

	//每次添加一次间隔50毫秒
	protected void store(Pair p){
		storage.add(p);
		try{
			TimeUnit.MILLISECONDS.sleep(50);
		}catch(InterruptedException ignore){
		}
	}

	public abstract void increment();
}