import java.util.*;
import java.sql.Timestamp;

class ProducerConsumer{
	public static void main(String args[]){
		Queue Q = new Queue();
		// Producer P = new Producer(Q);
		// Consumer C = new Consumer(Q);
		Scanner sc = new Scanner(System.in);
		while(true){
			int n;
			try{
				Thread.sleep(1000);
			}catch(Exception e){}
			System.out.print("\n 1. Insert\n 2. Delete\n 3. Check\n 4. Exit\n Please Enter Your Choice : ");
			n = sc.nextInt();
			switch(n){
				case 1 : Producer P = new Producer(Q);break;
				case 2 : Consumer C = new Consumer(Q);break;
				case 3 : Display e = new Display(Q);break;
				default : System.exit(0);
			}
		}
	}
}
class Queue {
	int rear = -1,front=0,max=10;
	static int count=0;
    String arr[] = new String[max];
	void add() throws InterruptedException{
		synchronized(Queue.this){
			while(count == max)
			{
				System.out.println("Queue is full. Please wait...");
				Queue.this.wait();
			}
			Date date= new Date(); 
			long time = date.getTime();
			Timestamp tss = new Timestamp(time);
			String ts = tss.toString();
			Thread.sleep(500);
			try{
		        rear = (rear+1)%max;
		        arr[rear] = ts;
		        count++;
				System.out.println("Value Inserted by Producer");
				Queue.this.notify();				
			}catch (ArrayIndexOutOfBoundsException e){

			}
		}		
	}
	void remove() throws InterruptedException{
		synchronized(Queue.this){
			while(count == 0)
			{
				System.out.println("Queue is empty. Please wait...");
				Queue.this.wait();
			}
			Thread.sleep(500);
			try{
				String z = arr[front]; 
		        System.out.println("Removing " + z);			 
		        front = (front + 1) % max;
		        count--;
				System.out.println("Value Deleted by Consumer");
				Queue.this.notify();				
			}catch (ArrayIndexOutOfBoundsException e){

			}
		}
	}
	void showList(){
		int i;
		if(rear != max){
			for(i=front;i<=rear;i++)
			System.out.println(" => "+arr[i]);
		}
	}
}
class Producer implements Runnable{
	Queue q;
	Thread t;
	int count=0;
	public Producer(Queue i){
		this.q = i;
		t = new Thread(this);
		t.start();
	}
	@Override
	public void run(){		
		// while (count < 100)
		// {
			try{
				q.add();
			} 
			catch (InterruptedException ex){
				ex.printStackTrace();
			}
			count++;
		// }
	}
}
class Consumer implements Runnable{
	Queue q;
	Thread t;
	int count =0;
	public Consumer(Queue i){
		this.q = i;
		t = new Thread(this);
		t.start();
	}
	@Override
	public void run(){		
		// while (count < 100)
		// {
			try{
				q.remove();
			} 
			catch (InterruptedException ex){
				ex.printStackTrace();
			}
			count++;
		// }
	}
}
class Display implements Runnable{
	Queue q;
	Thread t;
	Display(Queue s){
		this.q = s;
		t = new Thread(this);
		t.start();
	}
	@Override
	public void run(){
		q.showList();
	}
}
