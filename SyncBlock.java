/*
Sync.java 
uses 'synchronized' keyword 
 */ 
import static java.lang.System.out;; 
import java.io.*; 

class SumArray{ 

    private int sum; //in this particular case, static object sa will only have one copy of sum

    //thread locks object until finished 
    int sumArray(int nums[]){ //removed 'synchronized keyword' 
	sum=0; 

	for(int i=0; i<nums.length; i++){
	    sum+=nums[i]; 
	    out.println("Running total for: " + 
			Thread.currentThread().getName() +
			" is " + sum);  
	    //Thread.currentThread simply refers to 'this' object thread 
	    //This method may have to be used when not apart of a class that implements 'Runnable' 
	    try{ 
		Thread.sleep(10); //going to have task switching occur 

	    } 
	    catch(InterruptedException iexc){ 
		out.println("Thread interrupted"); 
	    }
	    		       
	}

	return sum; 
    } 


} 

class MyThread implements Runnable{ 

    Thread thrd; 
    static SumArray sa=new SumArray(); 
    int a[]; 
    int answer; 

    MyThread(String name, int nums[]){ 
	thrd=new Thread(this, name); 
      
	a=nums; //assigns to instance variable 
	thrd.start(); //starts the actual thread 
    } 


    public void run(){ 
	int sum; 

	out.println(thrd.getName() + " starting."); 

	//synchronized block 
	synchronized(sa){//synchronized(objref) - -> refers to particular object 
	    answer=sa.sumArray(a); //only one static object 


	}
	out.println("Sum for " + thrd.getName() + 
		    " is " + answer); 

	out.println(thrd.getName() + " terminating"); 
    }

}  

public class SyncBlock{ 

    public static void main(String args[]){  

	int a[]={1, 2, 3, 4, 5}; 

	MyThread mt1=new MyThread("Child#1", a); 
	MyThread mt2=new MyThread("Child#2", a);

	try{ 

	    mt1.thrd.join(); 
	    mt2.thrd.join(); 

	} 

	catch(InterruptedException exc){ 
	    out.println("Main thread interrupted!"); 
	} 

       
    } 

} 
