/*
 * This class defines the Ack Connect message
 * server → client to acknowledge connection and assign unique ID
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;

/** 
 *
 */
public class AckConnect extends Message
{

    /**
    *  Constructor
    */
   public AckConnect(short pVersion, short pClientID)
   {
      super(Message.ACKCONNECT_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      version = pVersion;   
      clientID = pClientID;
   }
	/**
    *  Constructor
    */
   public AckConnect(byte[] pMsg)
   {
	  super(Message.ACKCONNECT_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0]<<8) & 0xff00)|(pMsg[1] & 0xff));  
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff)); 
	  version = (short)(((pMsg[4]<<8) & 0xff00)|(pMsg[5] & 0xff));  
      clientID = (short)(((pMsg[6]<<8) & 0xff00)|(pMsg[7] & 0xff));   
   }
    
   /**
    *  Returns the byte array of the message 
    */
	public byte[] getbyteArray() {
      byte[] temp = new byte[header_size];
      temp[0] = (byte)((type & 0xff00)>> 8) ;
      temp[1] = (byte)(type & 0xff);
      temp[2] = (byte)((length & 0xff00) >> 8);
      temp[3] = (byte)(length & 0xff);
      temp[4] = (byte)((version & 0xff00)>> 8);
      temp[5] = (byte)(version & 0xff);
      temp[6] = (byte)((clientID & 0xff00)>> 8);
      temp[7] = (byte)(clientID & 0xff);
      return temp;
   }
	
   public short getVersion() {
      return version;
   }
    
   public short getClientID() {
       return clientID;
   }

	public ArrayList<String> getOptions() {
       return optionsList;
   }

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("version = "+version);
      System.out.println("clientID = "+clientID);
      System.out.println("options size = "+optionsList.size());
	}
    
   private short version;

    //options list
   private  ArrayList<String> optionsList;

	//this is the size of the header, does not include options field
   private static final short header_size= 8; 

   private short clientID;
}
