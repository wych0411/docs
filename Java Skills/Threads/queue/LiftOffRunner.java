public class LiftOffRunner implements Runnable{

	private BlockingQueue<LiftOff> rockets;

	protected LiftOffRunner(BlockingQueue<LiftOff> rockets){
		super();
		this.rockets = rockets;
	}

	public void add(LiftOff lo){
		try{
			rockets.put(lo);
		}catch(InterruptedException e){
			System.out.println("添加失败");
		}
	}

	@Override
	public void run(){
		try{

			while(!Thread.interrupted()){
				LiftOff rocket = rockets.take();
				rocket.run();
			}

		}catch(InterruptedException e){
			System.out.println("运行中断");
		}

		System.out.println("退出运行");
	}
}