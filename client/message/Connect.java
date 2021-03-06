/*
 * This class defines the Connect message
 * client → server to set up a link
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;


public class Connect extends Message
{
   /**
    *  Default constructor called when generating a message from scratch
    */
    public Connect(short pVersion)
    {
    	 super(Message.CONNECT_TYPE,header_size);
       optionsList = new ArrayList<String>();    
       version = pVersion;   
    }

   /**
    *  constructor called when generating a message object from a byte stream
    */
   public Connect(byte[] pMsg)
   {
      super(Message.CONNECT_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0] <<8) & 0xff00)|(pMsg[1] & 0xff));  
      length = (short)(((pMsg[2] <<8) & 0xff00)|(pMsg[3] & 0xff)); 
		version = (short)(((pMsg[4] <<8) & 0xff00)|(pMsg[5] & 0xff));
   }

   /**
    *  Returns the version of the protocol 
    */
   public short getVersion() {
      return version;
   }
    
	/**
    *  Returns the Arraylist of options
    */
	public ArrayList<String> getOptions() {
      return optionsList;
   }

   /**
    *  Debug method that dumps the message to the screen
    */
   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("version = "+version);
      System.out.println("options size = "+optionsList.size());
	}

	/**
    *  Returns the byte array of the message object
    */
	public byte[] getbyteArray() {
      byte[] temp = new byte[header_size];
      temp[0] = (byte)((type & 0xff00)>> 8);
      temp[1] = (byte)(type & 0xff);
      temp[2] = (byte)((length & 0xff00)>> 8);
      temp[3] = (byte)(length & 0xff);
      temp[4] = (byte)((version & 0xff00)>> 8);
      temp[5] = (byte)(version & 0xff);
      
      return temp;
   }
    
   private short version;
   
	//this is the size of the header, does not include options field
   private static final short header_size= 6; 

   //options list
   private  ArrayList<String> optionsList;
}
