public abstract class IntGenerator {

	//为了表示可见性，使用volatile修饰
	private volatile boolean canceled = false;

	public abstract int next();

	public void cancel(){
		canceled = true;
	}

	//查看该对象是否已经被撤销
	public boolean isCanceled(){
		return canceled;
	}
}