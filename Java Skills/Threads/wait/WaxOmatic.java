public class WaxOmatic{

	public static void main(String[] args) throws Exception{

		Car car = new Car();

		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));

		//暂停2秒钟
		TimeUnit.SECONDS.sleep(1);

		//关闭所有的任务
		exec.shutdownNow();
	}
}