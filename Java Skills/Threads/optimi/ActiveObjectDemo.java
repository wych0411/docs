public class ActiveObjectDemo{

	private ExecutorService ex = Executors.newSingleThreadExecutor();
	private Random rand = new Random(47);

	private void pause(int factor){

		try{
			TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
		}catch(InterruptedException e){
			System.out.println("sleep interrupted");
		}

	}

	public Future<Integer> calculateInt(final int x, final int y){

		return ex.submit(new Callable<Integer>(){

			@Override
			public Integer call() throws Exception{
				System.out.println("staring" + x + "+" + y);
				pause(500);
				return x + y;
			}
		});

	}

	public void shutdown(){
		ex.shutdown();
	}

	public static void main(String[] args){

		ActiveObjectDemo dl = new ActiveObjectDemo();

		List<Future<?>> results = new CopyOnWriteArrayList<>();

		for(float f = 0.0f; f < 1.0f; f += 0.2f){
			results.add(dl.calculateFloat(f, f));
		}

		for(int i=0; i < 5; i++){
			results.add(dl.calculateInt(i, i));
		}

		System.out.println("All asynch calls made");

		while(results.size() > 0){

			for(Future<?> future : results){
				if(future.isDone()){
					try{
						System.out.println(future.get());
					}catch(Exception e){
						throw new RuntimeException(e);
					}

					results.remove(future);
				}
			}
		}

		dl.shutdown();
	}
}