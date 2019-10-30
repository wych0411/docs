public class Philosopher implements Runnable{

	private Chopstick left;
	private Chopstick right;
	private final int id;
	private final int ponderFactor;
	private Random random = new Random(47);

	public void pause() throws InterruptedException{
		if(ponderFactor == 0){
			return;
		}

		TimeUnit.MICROSECONDS.sleep(random.nextInt(ponderFactor * 250));
	}

	protected Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor){
		super();
		this.left = left;
		this.right = right;
		this.id = id;
		this.ponderFactor = ponderFactor;
	}

	@Override
	public void run(){
		try{
			while(!Thread.interrupted()){
				System.out.println(this + "开始思考");
				pause();
				System.out.println(this + "开始拿左边的筷子");
				left.take();
				System.out.println(this + "开始拿右边的筷子");
				right.take();

				System.out.println(this + "开始就餐");
				pause();
				left.drop();
				right.drop();
			}
		}catch(InterruptedException e){
			System.out.println("当前线程被中断了");
		}
	}

	@Override
	public String toString(){
		return "哲学家的编号：" + id;
	}
}