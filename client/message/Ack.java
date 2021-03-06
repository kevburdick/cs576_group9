/*
 * This class defines the generic Ack message
 * ACKOPEN: server → client to acknowledge a chosen file
 * ACKLOCK: server → client to acknowledge a lock
 * ACKEDIT: server → client to acknowledge edit
 * SERVRELEASE: server → client to force client to release editing lock
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;

/** 
 *
 */
public class Ack extends Message
{

    /**
    *  Constructor
    */
   public Ack(short type)
   {
      super(type,header_size);
      optionsList = new ArrayList<String>();    
   }
	/**
    *  Constructor
    */
   public Ack(byte[] pMsg)
   {
	  super((short)(pMsg[0]|(pMsg[1]<<8)),header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0]<<8) & 0xff00)| (pMsg[1] & 0xff)); 
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff));  
   }
    
   /**
    *  Returns the byte array of the message 
    */
	public byte[] getbyteArray() {
      byte[] temp = new byte[header_size];
      temp[0] = (byte)((type & 0xff00)>> 8);
      temp[1] = (byte)(type & 0xff);
      temp[2] = (byte)((length & 0xff00)>> 8);
      temp[3] = (byte)(length & 0xff);
      return temp;
   }

	public ArrayList<String> getOptions() {
       return optionsList;
   }

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("options size = "+optionsList.size());
	}
    
    //options list
   private  ArrayList<String> optionsList;

	//this is the size of the header, does not include options field
   private static final short header_size= 4; 

}
