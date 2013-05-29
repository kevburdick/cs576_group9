/* * This class defines the Open message * client → server to choose a file * @author Kevin Burdick  */ package message;import java.util.ArrayList;import java.nio.ByteBuffer;import java.nio.ByteOrder;/**  * */public class Open extends Message{    /**    *  Constructor    */   public Open(short pClientID, int pFileID)   {      super(Message.OPEN_TYPE,header_size);      optionsList = new ArrayList<String>();         clientID = pClientID;      fileID = pFileID;   }	/**    *  Constructor    */   public Open(byte[] pMsg)   {      //FIXME should throw exception		super(Message.ACKCONNECT_TYPE,header_size);      optionsList = new ArrayList<String>();           type = (short)((pMsg[0] & 0xff<<8)|(pMsg[1] & 0xff));       length = (short)((pMsg[2] & 0xff<<8)|(pMsg[3] & 0xff)); 		clientID = (short)((pMsg[4]& 0xff<<8)|(pMsg[5] & 0xff));      fileID = (int)((pMsg[6]& 0xff<<8)|(pMsg[7] & 0xff<<8)|(pMsg[8]& 0xff<<8)|(pMsg[9] & 0xff));                        }       /**    *  Returns the byte array of the message     */	public byte[] getbyteArray() {      byte[] temp = new byte[header_size];      temp[0] = (byte)(type >> 8);      temp[1] = (byte)type;      temp[2] = (byte)(length >> 8);		temp[3] = (byte)length;      temp[4] = (byte)(clientID >> 8);      temp[5] = (byte)clientID;      byte[] temp2 = ByteBuffer.allocate(4).putInt(fileID).array();      for(int i=0;i<4;i++) {          temp[6+i] = temp2[i];        }      return temp;   }	   public int getfileID() {      return fileID;   }       public short getClientID() {       return clientID;   }	public ArrayList<String> getOptions() {       return optionsList;   }   public void dumpMsg() {      System.out.println("message");      System.out.println("type = "+type);      System.out.println("length = "+length);      System.out.println("clientID = "+clientID);      System.out.println("fileID = "+fileID);      System.out.println("options size = "+optionsList.size());	}        //options list   private  ArrayList<String> optionsList;	//this is the size of the header, does not include options field   private static final short header_size= 10;    private short clientID;   private int fileID;}