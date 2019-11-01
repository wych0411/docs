public class WaxOn implements Runnable{

	private Car car;

	protected WaxOn(Car car){
		super();
		this.car = car;
	}

	@Override
	public void run(){

		try{
			while(!Thread.interrupted()){
				System.out.println("Wax one");
				TimeUnit.MICROSECONDS.sleep(200);

				//开始打蜡
				car.waxed();

				//当前任务被挂起
				car.waitForBuffing();
			}
		}catch(InterruptedException e){
			System.out.println("Exiting via interrupt");
		}

		System.out.println("Ending wax on task");
	}
}