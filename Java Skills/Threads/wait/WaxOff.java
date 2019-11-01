public class WaxOff implements Runnable{

	private Car car;

	protected WaxOff(Car car){
		super();
		this.car = car;
	}

	@Override
	public void run(){

		try{
			while(!Thread.interrupted()){

				//如果还是在打蜡就挂起
				car.waitForWaxing();
				System.out.println("Wax off");
				TimeUnit.MICROSECONDS.sleep(200);

				//开始抛光
				car.buffed();
			}
		}catch(InterruptedException e){
			System.out.println("Wxting via interrupt");
		}
		System.out.println("Ending wax off task");
	}
}