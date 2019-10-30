public class Atomicity{
	int i;

	void f(){
		i++;
	}

	void g(){
		i += 3;
	}
}