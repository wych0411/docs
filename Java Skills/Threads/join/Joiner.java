public class Joiner extends Thread{
	private Sleeper sleeper;

	public Joiner(String name, Sleeper sleeper){
		super(name);
		this.sleeper = sleeper;
		start();
	}

	public void run(){
		try{
			sleeper.join();
		}catch(InterruptedException e){
			Print.print("Interrupted");
		}
		Print.print(getName() + " join completed");
	}
}