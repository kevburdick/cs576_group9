/*
 * This class defines the Close message
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;


public class Close extends Message
{

   /**
    *  Constructor
    */
   public Close()
   {
      super(Message.CLOSE_TYPE,header_size);
 
   }
	/**
    *  Constructor
    */
   public Close(byte[] pMsg)
   {
	  super(Message.CLOSE_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0]<<8) & 0xff00)|(pMsg[1] & 0xff));  
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

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
	}
    

    //options list
   private  ArrayList<String> optionsList;

	//this is the size of the header, does not include options field
   private static final short header_size= 4; 

}
