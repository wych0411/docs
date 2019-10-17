public class DaemonsDontRunFinally{

	public static void main(String[] args) throws Exception {

		Thread t = new Thread(new ADaemon());
		t.setDaemon(true);
		t.start();
	}
}

class ADaemon{

	public void run(){
		try{
			print("Starting ADaemon");
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e){
			print("Exiting via interruptedException");
		}finally{
			print("This should always run?");
		}
	}
}