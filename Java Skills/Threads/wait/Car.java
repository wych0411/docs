public class Car{

	//涂蜡和抛光的状态
	private boolean waxOn = false;

	//打蜡
	public synchronized void waxed(){
		waxOn = true;
		notifyAll();
	}

	//抛光
	public synchronized void buffed(){
		waxOn = false;
		notifyAll();
	}

	//抛光结束被挂起即将开始打蜡任务
	public synchronized void waitForWaxing() throws InterruptedException{
		while(waxOn == false){
			wait();
		}
	}

	//打蜡结束被挂起即将开始抛光任务
	public synchronized void waitForBuffing() throws InterruptedException{
		while(waxOn == true){
			wait();
		}
	}
}