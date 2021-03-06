/*
 * This class defines the Status message
 * server → client indicating current status information
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;


public class Status extends Message
{

   /**
    *  Constructor
    */
   public Status()
   {
      super(Message.STATUS_TYPE,header_size);
      optionsList = new ArrayList<String>(); 
      checksum = new byte[32];   
   }
	/**
    *  Constructor
    */
   public Status(byte[] pMsg)
   {
	  super((short)(pMsg[0]|(pMsg[1]<<8)),header_size);
      optionsList = new ArrayList<String>();  
      checksum = new byte[32];  
      type = (short)(((pMsg[0]<<8) & 0xff00)| (pMsg[1] & 0xff)); 
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff)); 
      System.arraycopy(pMsg, 4,checksum, 0,32);
  
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
      System.arraycopy(checksum, 0,temp, 4,32);  
      return temp;
   }

	public ArrayList<String> getOptions() {
       return optionsList;
   }

   public byte[] getChecksum() {
       return checksum;
   }

   public void setChecksum(byte[] pChecksum) {
       System.arraycopy(checksum, 0,pChecksum, 0,32); 
   }

  


   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("checksum = "+checksum);
      System.out.println("options size = "+optionsList.size());
	}
    
    //options list
   private  ArrayList<String> optionsList;
   private byte[] checksum;

	//this is the size of the header, does not include options field
   private static final short header_size= 36; 

}
