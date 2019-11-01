public class NotifyVsNotifyAll{

	public static void main(String[] args) throws Exception{

		ExecutorService exec = Executors.newCachedThreadPool();

		for(int i=0;i<3;i++){
			exec.execute(new Task());
		}

		exec.execute(new Task2());

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			boolean prod = true;

			@Override
			public void run(){
				if(prod){
					System.out.println("notify");
					Task.blocker.prod();
					prod = false;
				}else{
					System.out.println("notifyAll");
					Task.blocker.prodAll();
					prod = true;
				}
			}
		}, 400, 400);

		TimeUnit.SECONDS.sleep(5);
		timer.cancel();

		System.out.println("Time cancle");
		TimeUnit.MILLISECONDS.sleep(500);

		System.out.println("Task2.blocker.prodAll() ");
		Task2.blocker.prodAll();
		TimeUnit.MILLISECONDS.sleep(500);
		
		System.out.println("\n Shutting down");
		exec.shutdownNow(); //Interrupt all tasks
	}
}