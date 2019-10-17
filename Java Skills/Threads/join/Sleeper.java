public class Sleeper extends Thread{

	private int duration;

	public Sleeper(String name, int sleepTime){
		super(name);
		duration = sleepTime;
		start();
	}

	public void run(){
		try{
			sleep(duration);
		}catch(InterruptedException e){
			Print.print(getName() + " was interrupted. " + "isInterrupted():" + isInterrupted());
			return;
		}
		Print.print(getName() + " has awakened");
	}
}