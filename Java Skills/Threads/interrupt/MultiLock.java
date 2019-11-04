public class MultiLock{

	public synchronized void f1(int count){
		if(count-- > 0){
			System.out.println("f1 调用 f2" + count);
			f2(count);
		}
	}

	public synchronized void f2(int count){
		if(count-- > 0){
			System.out.println("f2 调用 f1" + count);
			f1(count);
		}
	}

	public static void main(String[] args) throws Exception{

		final MultiLock multiLock = new MultiLock();
		new Thread(){

			public void run(){
				multiLock.f1(5);
			}

		}.start();
	}
	
}