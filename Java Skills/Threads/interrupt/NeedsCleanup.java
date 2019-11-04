public class NeedsCleanup{

	private final int id;

	protected NeedsCleanup(int id){
		super();
		this.id = id;
		System.out.println("NeedsCleanup:" + id);
	}

	public void cleanup(){
		System.out.println("cleanup()" + id);
	}
	
}