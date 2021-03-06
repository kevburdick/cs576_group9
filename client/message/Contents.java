/*
 * This class defines the Contents message
 *  server → client to transmit file contents
 * @author Kevin Burdick 
 */
 
package message;

import java.nio.ByteBuffer;

public class Contents extends Message
{

    /**
    *  Constructor
    */
   public Contents(int pPosition, int pLength, byte[] data)
   {
      super(Message.CONTENTS_TYPE,(short)(header_size+data.length));
      fileData = new byte[data.length];
      fileLength = pLength;
      filePosition = pPosition;
      //copy file data
      System.arraycopy(data, 0,fileData, 0,data.length);    
   }
	/**
    *  Constructor
    */
   public Contents(byte[] pMsg)
   {
	  super(Message.CONTENTS_TYPE,header_size);   
      fileData = new byte[pMsg.length-header_size];
      type = (short)(((pMsg[0]<<8) & 0xff00)|(pMsg[1] & 0xff)); 
      length = (short)(((pMsg[2]<<8) & 0xff00)|(pMsg[3] & 0xff)); 
      fileLength = (int)(((pMsg[4]<<8) & 0xff000000)|((pMsg[5]<<8) & 0xff0000)|((pMsg[6]<<8) & 0xff00)|(pMsg[7] & 0xff));   
      filePosition = (int)(((pMsg[8]<<8) & 0xff000000)|((pMsg[9]<<8) & 0xff0000)|((pMsg[10]<<8) & 0xff00)|(pMsg[11] & 0xff));    
      //copy file data
      System.arraycopy(pMsg, header_size,fileData, 0,pMsg.length-12); 
   }
    
   /**
    *  Returns the byte array of the message 
    */
	public byte[] getbyteArray() {
      byte[] output = new byte[header_size+fileData.length];
      output[0] = (byte)((type & 0xff00)>> 8);
      output[1] = (byte)(type & 0xff);
      output[2] = (byte)((length & 0xff00)>> 8);
	  output[3] = (byte)(length  & 0xff);
      byte[] temp2 = ByteBuffer.allocate(4).putInt(fileLength).array();
      for(int i=0;i<4;i++) {
          output[4+i] = temp2[i];  
      }
      temp2 = ByteBuffer.allocate(4).putInt(filePosition).array();
      for(int j=0;j<4;j++) {
          output[8+j] = temp2[j];  
      }
      //copy file data
      System.out.println("getbyteArray");
      System.arraycopy(fileData, 0,output, header_size,fileData.length); 
      return output;
   }
	
   public short getClientID() {
       return clientID;
   }
   public int getFilePosition() {
      return filePosition;
   }

   public int getSectionLen() {
      return fileLength;
   }

	public byte[] getFileData() {
       return fileData;
   }

   public void dumpMsg() {
      System.out.println("message");
      System.out.println("type = "+type);
      System.out.println("length = "+length);
      System.out.println("fileLength = "+fileLength);
      System.out.println("filePosition = "+filePosition);
      System.out.println("data size = "+fileData.length);
	}
    

   //file data 
   private  byte[] fileData;

	//this is the size of the header, does not include data field
   private static final short header_size= 12; 

   private short clientID;
   private int filePosition;
   private int fileLength;
}
