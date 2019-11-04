public class Entrance implements Runnable{

	private static Count count = new Count();
	private static List<Entrance> entrances = new ArrayList<>();
	private int number = 0;
	private final int id;

	private static volatile boolean canceled = false;

	public static void cancel(){
		canceled = true;
	}

	protected Entrance(int id){
		super();
		this.id = id;
		entrances.add(this);
	}

	@Override
	public void run(){

		while(!canceled){

			synchronized(this){
				++number;
			}

			System.out.println(this + "Total" + count.increment());

			try{
				TimeUnit.MICROSECONDS.sleep(100);
			}catch(InterruptedException e){
				System.out.println("SLEEP INTERRUPTED");
			}
		}

		System.out.println("结束了");
	}

	public synchronized int getValue(){
		return number;
	}

	@Override
	public String toString(){
		return "Entrance" + id + ": " + getValue();
	}

	public static int getTotalCount(){
		return count.value();
	}

	public static int sumEntrances(){
		int sum = 0;
		for(Entrance entrance : entrances){
			sum += entrance.getValue();
		}
		return sum;
	}
}