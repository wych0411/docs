/**
 * 厨师类
 */
public class Chef implements Runnable{

	private Restaurant restaurant;

	private int count = 0;

	public Chef(Restaurant r){
		restaurant = r;
	}

	public void run(){
		try{

			while(!Thread.interrupted()){
				synchronized(this){
					while(restaurant.meal != null){
						wait(); // ...for the meal to be taken
					}
				}

				if(++count == 10){
					Print.print("Out of food, closing");
					restaurant.exec.shutdownNow();
				}

				Print.printnb("Order up!");

				synchronized(restaurant.waitPerson){
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}

				TimeUnit.MILLISECONDS.sleep(100);
			}

		}catch(InterruptedException e){
			Print.print("Chef interrupted");
		}
	}


}