public class OrnamentalGraden{

	public static void main(String[] args) throws Exception{

		ExecutorService exec = Executors.newCachedThreadPool();

		for(int i=0;i<5;i++){
			exec.execute(new Entrance(i));
		}

		TimeUnit.SECONDS.sleep(3);

		Entrance.cancel();
		exec.shutdown();

		if(!exec.awaitTermination(250, TimeUnit.MICROSECONDS)){
			System.out.println("Som task were not terminated");
		}

		System.out.println("总人数" + Entrance.getTotalCount());
		System.out.println("所有的 Entrances:" + Entrance.sumEntrances());
		
	}
}