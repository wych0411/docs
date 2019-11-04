/**
 * 创建任务
 */
public class Blocked3 implements Runnable{

	private volatile double d = 0.0;

	@Override
	public void run(){

		while(!Thread.interrupted()){

			NeedsCleanup nCleanup = new NeedsCleanup(1);
			try{

				TimeUnit.SECONDS.sleep(1);
				NeedsCleanup nCleanup2 = new NeedsCleanup(2);

				try{

					for(int i=0; i< 2500000;i++){
						d = d + (Math.PI + Math.E)/d;
					}
					System.out.println("计算完毕");

				}finally{
					nCleanup2.cleanup();
				}

			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				nCleanup.cleanup();
			}

		}
	}

}