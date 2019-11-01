public class Task2 implements Runnable{

	// A separate Blocker object:
	static Blocker blocker = new Blocker();

	public void run(){
		blocker.waitingCall();
	}
}