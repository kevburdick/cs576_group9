/*
 * This class is a timer class to be used with the CTP protocol to support waiting on a response
 * from the server. This class is a separate thread that counts down and indicates when it expires. 
 * Another thread polls this class to check if it expired. This is not a high resolution timer.
 */
 
import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


public class ResponseTimer
{
	static int TIMEDELAY =20000; //delay is 20 seconds 
	Timer timer;
	boolean expired = false;
	static ResponseTimer instance;
	
	ResponseTimer()
	{
		//System.out.println("ResponseTimer ");
		instance = this;
	}
	
	/** 
	 * Stop and restart a timer; 
	 */
	public void reset()
	{
		//System.out.println("ResponseTimer reset ");
		//reset flag and start timer
		setExpired(false);	
		if(timer!=null)
			stop();
		timer = new Timer();
		timer.schedule(new RTimer(), TIMEDELAY); 
	}
	
	public void stop()
	{
		//System.out.println("ResponseTimer stop ");
		if(timer!=null)
			timer.cancel();
	}
	 
	/*
	 * Check to see if timer has gone off 
	 */	
	public synchronized boolean expired()
	{
	//	System.out.println("ResponseTimer expired ");
		return expired; 
	}
	
	/*
	 * Sets flag indicating timer has gone off 
	 */	
	public synchronized void setExpired(boolean flag)
	{
	//	System.out.println("ResponseTimer setExpired ");
		expired= flag;
	}
	
	public static ResponseTimer getInstance()
	{
		return instance;
	}
		
	/*
	 * This timer class fires after the delay set when created then it updates a flag for the 
	 * main thread signaling time has expired,
	 */	
	protected class RTimer extends TimerTask {
		public void run() 
		{
		//	System.out.println("ResponseTimer RTimer expired");
			setExpired(true);
			
		}
	}
}
    
