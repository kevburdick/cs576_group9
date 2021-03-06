/*
 * This class defines the Error message
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;


public class Error extends Message
{

   /**
    *  Constructor
    */
   public Error(short code)
   {
      super(Message.ERROR_TYPE,header_size);
      errorCode = code;
 
   }
   
	/**
    *  Constructor
    */
   public Error(byte[] pMsg)
   {
	  super(Message.CLOSE_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0]<<8) & 0xff00)|(pMsg[1] & 0xff));  
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff)); 
      errorCode = (short)(((pMsg[4]<<8) & 0xff00)|(pMsg[5] & 0xff)); 
   }
   
   public short getErrorCode()
   {
	   return errorCode;
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
      temp[4] = (byte)((errorCode & 0xff00)>> 8);
      temp[5] = (byte)(errorCode & 0xff);
      return temp;
   }

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("errorCode = "+errorCode);
	}
    

    //options list
   private  ArrayList<String> optionsList;

	//this is the size of the header, does not include options field
   private static final short header_size= 6;

   public static final short BAD_MESSAGE_ERR = 1;
   public static final short UNSUPPORTED_VERSION_ERR = 2;
   public static final short BAD_FILEID_ERR = 3;
   public static final short CANT_LOCK_ERR = 4;
   public static final short REJECT_EDIT_ERR = 5;

   //text associated with error number
   public static final String errorText[] = {"BAD_MESSAGE_ERR",
	                                        "UNSUPPORTED_VERSION_ERR",
	                                        "BAD_FILEID_ERR",
	                                        "CANT_LOCK_ERR",
	                                        "REJECT_EDIT_ERR"};
   
   private short errorCode;

}
