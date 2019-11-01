public class TestBlockingQueues{

	static void getkey(){
		try{
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	static void getkey(String message){
		Print.print(message);
		getkey();
	}

	static void test(String msg, BlockingQueue<LiftOff> queue){

		LiftOffRunner runner = new LiftOffRunner(queue);

		Thread thread = new Thread(runner);
		thread.start(); // 启动了，但是内容是空的，就一直挂起，等待有新的内容进去

		for(int i=0; i<5; i++){
			runner.add(new LiftOff(5));
		}

		getkey("Press Enter " + msg);
		thread.interrupt();
	}

	public static void main(String[] args){
		test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));
		test("SynchronousQueue", new SynchronousQueue<>());
	}
}