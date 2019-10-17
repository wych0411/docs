public class TaskWithResult implements Callable<String>{

	private int id;

	protected TaskWithResult(int id){
		super();
		this.id = id;
	}

	public String call() throws Exception {
		return "任务执行完毕"+id;
	}

	public static void main(String[] args){

		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<String>> result = new ArrayList<>();
		for(int i = 0;i < 3;i++){
			result.add(executorService.submit(new TaskWithResult(i)));
		}

		for(Future<String> future : result){
			try{
				System.out.println(future.get());
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}finally{
				executorService.shutdown();
			}	
		}
	}
}