public class PriorityBlockingQueueDemo{

	public static void main(String[] args) throws Exception{

		Random rand = new Random(47);

		ExecutorService exec = Executors.newCachedThreadPool();

		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();

		exec.execute(new PrioritizedTaskProducer(queue, exec));
		exec.execute(new PrioritizedTaskConsumer(queue));
	}

}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask>{

	private Random rand = new Random(47);

	private static int counter = 0;
	private final int id = counter++;
	private final int priority;

	protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

	public PrioritizedTask(int priority){
		this.priority = priority;
		sequence.add(this);
	}

	public int compareTo(PrioritizedTask arg){
		return priority < arg.priority ? 1:(priority > arg.priority ? -1 : 0);
	}

	public void run(){

		try{
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
		}catch(InterruptedException e){
			// Acceptable way to exit
		}
		print(this);
	}

	public String toString(){
		return String.format("[%1$-3d]", priority) + " Task " + id;
	}

	public String summary(){
		return "(" + id + ":" + priority + ")";
	}

	public static class EndSentinel extends PrioritizedTask{

		private ExecutorService exec;

		public EndSentiel(ExecutorService e){
			super(-1);
			exec = e;
		}

		public void run(){

			int count = 0;
			for(PrioritizedTask pt : sequence){
				printnb(pt.summary());
				if(++count % 5 == 0){
					print();
				}
			}

			print();
			print(this + " Calling shutdownNow()");
			exec.shutdownNow();
		}
	}
}

class PrioritizedTaskProducer implements Runnable{

	private Random rand = new Random(47);
	private Queue<Runnable> queue;

	private ExecutorService exec;

	public PrioritizedTaskProducer()
}