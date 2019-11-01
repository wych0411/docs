/**
 * 服务生类
 */
public class WaitPerson implements Runnable{

	private Restaurant restaurant;

	public WaitPerson(Restaurant r){
		restaurant = r;
	}

	public void run(){

		try{
			while(!Thread.interrupted()){
				synchronized(this){
					while(restaurant.meal == null){
						wait(); // ... for the chef to produce a meal
					}
				}

				Print.print("WaitPerson got " + restaurant.meal);

				synchronized(restaurant.chef){
					
				}
			}
		}catch(InterruptedException e){
			Print.print("WaitPerson interrupted");
		}
	}
}