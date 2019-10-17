public class Daemon implements Runnable{

	private Thread[] t = new Thread[10];

	public void run(){
		for(int i = 0;i < t.length;i++){
			t[i] = new Thread(new DeamonSpawn());
			t[i].start();
			Print.printnb("DaemonSpawn " + i + " started,");
		}

		for(int i = 0;i < t.length;i++){
			Print.printnb("t[" + i + "].isDaemon()=" + t[i].isDaemon() + ",");
			while(true){
				Thread.yield();
			}
		}
	}

	class DaemonSpawn implements Runnable{

		public void run(){

			while(true){
				Thread.yield();
			}
		}
	}
}