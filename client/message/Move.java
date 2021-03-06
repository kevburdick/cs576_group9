/*
 * This class defines the Move message
 * client → server and server → client to indicate client’s cursor position
 * @author Kevin Burdick 
 */
 
package message;

import java.util.ArrayList;
import java.nio.ByteBuffer;


public class Move extends Message
{

   /**
    *  Constructor
    */
   public Move(short pClientID, int pCursorPos)
   {
      super(Message.MOVE_TYPE,header_size);
      optionsList = new ArrayList<String>();     
      clientID = pClientID;
      cursorPos = pCursorPos;
 
   }
	/**
    *  Constructor
    */
   public Move(byte[] pMsg)
   {
	  super(Message.REQLOCK_TYPE,header_size);
      optionsList = new ArrayList<String>();    
      type = (short)(((pMsg[0]<<8) & 0xff00)|(pMsg[1] & 0xff));  
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff)); 
	  clientID = (short)(((pMsg[4]<<8) & 0xff00)|(pMsg[5] & 0xff));      
      ByteBuffer bb = ByteBuffer.wrap(pMsg,6, 4);
      cursorPos = bb.getInt();
      
      
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
      temp[4] = (byte)((clientID & 0xff00)>> 8);
      temp[5] = (byte)(clientID & 0xff);
      byte[] temp2 = ByteBuffer.allocate(4).putInt(cursorPos).array();
      for(int i=0;i<4;i++) {
          temp[6+i] = temp2[i];  
      }
      return temp;
   }
    
   public short getClientID() {
       return clientID;
   }

   public int getCursorPos() {
       return cursorPos;
   }

	public ArrayList<String> getOptions() {
       return optionsList;
   }

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("clientID = "+clientID);
      System.out.println("cursorPos = "+cursorPos);
      System.out.println("options size = "+optionsList.size());
	}
    

    //options list
   private  ArrayList<String> optionsList;

	//this is the size of the header, does not include options field
   private static final short header_size= 10; 

   private short clientID;
   private int cursorPos;
}
