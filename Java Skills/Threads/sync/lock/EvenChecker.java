public class EvenChecker implements Runnable{
	private IntGenerator generator;
	private final int id;

	protected EvenChecker(IntGenerator generator, int id){
		super();
		this.generator = generator;
		this.id = id;
	}

	@Override
	public void run(){
		while(!generator.isCanceled()){
			int val = generator.next();
			if(val % 2 != 0){
				System.out.println("不是偶数");
				generator.cancel();
			}
		}
	}

	public static void test(IntGenerator gp, int count){
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<count;i++){
			service.execute(new EvenChecker(gp, i));
		}
		service.shutdown();
	}

	public static void test(IntGenerator gp){
		test(gp, 10);
	}
}