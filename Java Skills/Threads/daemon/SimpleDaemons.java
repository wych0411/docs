public class SimpleDaemons implements Runnable {

	public void run(){
		try{
			while(true){
				TimeUnit.MILLISECONDS.sleep(100);
				Print.print(Thread.currentThread() + " " + this);	
			}
		}catch(InterruptedException e){
			Print.print("sleep() interrrupted");
		}
	}

	public static void main(String[] args) throws Exception{

		for(int i = 0;i < 10;i++){
			Thread daemon = new Thread(new SimpleDeamons());
			//比如在调用之前设置为后台线程才会生效
			daemon.setDeamon(true); // Must call before start()
			daemon.start();
		}

		Print.print("All daemons started");
		TimeUnit.MILLISECONDS.sleep(175);
	}
}