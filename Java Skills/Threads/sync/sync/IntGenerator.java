public abstract class IntGenerator{

	//为了标识可见性，使用volatile修饰
	private volatile boolean canceled = false;

	public abstract int next();

	public void cancel(){
		canceled = true;
	}

	//查看对象是否已经被撤销
	public boolean isCanceled(){
		return canceled;
	}
}