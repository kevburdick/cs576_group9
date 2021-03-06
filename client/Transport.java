/*
 * This is the main entry into the Transport Layer. This implementation provides
 * access to a TCP/IP socket. It was originally developed and based off of the example 
 * in Computer Networks by Forouzan and Mosharraf  
 *
 */
 
import java.net.*;
import java.io.*;

import message.Message;

/** Main program class for the client application 
 */
public class Transport
{
	Socket socket;
	OutputStream sendStream;
	InputStream recStream;
	static int TIMEDELAY =20; //delay is 20 seconds 
	static int timer_delay =0; 
	ResponseTimer responseTimer;

	Transport(String server, int port) 
	{
		//System.out.println("Transport ");
		try {
			socket = new Socket(server,port);
			sendStream = socket.getOutputStream();
			recStream = socket.getInputStream();
		}
		catch(Exception ex) {
			System.out.println("Exception in Transport "+ex.toString());
		}
		responseTimer = new ResponseTimer();
	}
	 
	public void sendRequest(byte[] pBuffer)
	{
		//System.out.println("sendRequest ");
		try {
			byte[] buffer = pBuffer;
			sendStream.write(buffer,0,buffer.length);
		}
		catch(IOException ex) {
			 System.out.println("Exception "+ex.toString());
		}
	}
   
   public byte[] getResponse()
   {
	   //reset and start timer
	   responseTimer.reset();
	   try {		
		   int  dataSize;
		   //poll for data and check timer
		   while ((dataSize=recStream.available())==0 && responseTimer.expired()==false);
		   responseTimer.stop();
		   byte[] recvBuff = new byte[dataSize];
		   recStream.read(recvBuff,0,dataSize);
		   //String response = new String(recvBuff,0,dataSize);
		   //System.out.println("Message Received "+response);
		   return recvBuff;
		}
		catch(IOException ex) {
			System.out.println("Exception in getResponse"+ex.toString());
			return null;
		}
	}
   
   /*
    * This method closes the socket and cleans up
    */
   public void close()
   {
		try {
	   		sendStream.close();
	   		recStream.close();
	        socket.close();
		}
		catch(IOException ex) {
			System.out.println("Exception "+ex.toString());
		}
	}
}
    
