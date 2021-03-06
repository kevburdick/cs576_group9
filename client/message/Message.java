/*
 * This is the base abstract class for all messages
 * @author Kevin Burdick 
 */
 
package message;

/** 
 * Abstract base class for all the Messages in the protocol
 */
public abstract class Message
{
	/** 
    * Constructor called from child classes
    */
   protected Message(short pType, short pLength)
   {
   	length=pLength;
      type = pType;
   }
         
   /**  
	 * Create a Message of an appropriate type using the input buffer
    */
   public static Message makeMessage(byte[] buffer)       
   {
	   short type = (short)((buffer[0] & 0xff <<8)|buffer[1] & 0xff);  
	   switch (type) {
          case CONNECT_TYPE: { System.out.println("msg received CONNECT_TYPE");
                               return new Connect(buffer); }
          case ACKCONNECT_TYPE: { System.out.println("msg received ACKCONNECT_TYPE");
                                  return new AckConnect(buffer); }
          case OPEN_TYPE: { System.out.println("msg received OPEN_TYPE");
                            return new Open(buffer); }
			 case ACKOPEN_TYPE: 
 	 		 case ACKLOCK_TYPE:
 	 		 case ACKEDIT_TYPE: 
	 		 case SERVRELEASE_TYPE: { System.out.println("msg received ACK*");
                                  return new Ack(buffer); }
          case REQLOCK_TYPE: { System.out.println("msg received REQLOCK_TYPE");
                                  return new ReqLock(buffer); }
          case RELEASE_TYPE: { System.out.println("msg received RELEASE_TYPE");
                                  return new Release(buffer); }
          case MOVE_TYPE: { System.out.println("msg received MOVE_TYPE");
                                  return new Move(buffer); }
          case REQCONTENTS_TYPE: { System.out.println("msg received REQCONTENTS_TYPE");
                                  return new ReqContents(buffer); }
          case CONTENTS_TYPE: { System.out.println("msg received CONTENTS_TYPE");
                                  return new Contents(buffer); }
          case SYNC_TYPE: { System.out.println("msg received SYNC_TYPE");
                                  return new Sync(buffer); }
          case STATUS_TYPE: { System.out.println("msg received STATUS_TYPE");
                                  return new Status(buffer); }
          case EDIT_TYPE: { System.out.println("msg received EDIT_TYPE");
                                  return new Edit(buffer); }
		  case CLOSE_TYPE: { System.out.println("msg received CLOSE_TYPE");
                                  return new Close(buffer); }
          case ERROR_TYPE: { System.out.println("msg received ERROR_TYPE");
                                  return new Error(buffer); }
          default:  { System.out.println("Error: Invalid message type");
                                  return null; }
		}
   }

   /**  
	 * Method turns the message into a byte array for sending to transport layer
    */
    public abstract byte[] getbyteArray(); 

   /** 
    * Method returns the message type of the object  
    */
    public short getType() {
        return type;
    }
    
   /** 
    * Method returns the length of the message  
    */
	 public short getLength() {
        return length;
    }
    
    /** The following are message types
     */
	public static final short INVALID_TYPE =0; //Internal use only
    public static final short ERROR_TYPE= 1;
    public static final short CONNECT_TYPE= 2;
	public static final short ACKCONNECT_TYPE =3;
    public static final short OPEN_TYPE =4;
    public static final short ACKOPEN_TYPE =5;
 	public static final short ACKLOCK_TYPE =6;
 	public static final short ACKEDIT_TYPE =7; 
	public static final short SERVRELEASE_TYPE =8;
    public static final short REQCONTENTS_TYPE =9;
    public static final short CONTENTS_TYPE =10;
    public static final short MOVE_TYPE =11;//async
    public static final short REQLOCK_TYPE =12;
    public static final short RELEASE_TYPE =13;
    public static final short SYNC_TYPE =14;
    public static final short STATUS_TYPE =15;
    public static final short EDIT_TYPE =16;//async
    public static final short CLOSE_TYPE =17;//async
    

    protected short type;
    protected short length;
      
}
