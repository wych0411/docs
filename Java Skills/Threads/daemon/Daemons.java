public class Daemons{

	public static void main(String[] args){

		Thread d = new Thread(new Deamon());
		d.setDeamon(true);
		d.start();

		Print.printnb("d.isDaemon()=" + d.isDaemon() + ",");

		// Allow the daemon threads to finish their startup processes
		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}