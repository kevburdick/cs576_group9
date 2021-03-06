/*
 * This class is the upper application API and interface to the CTP protocol. It
 * defines all the public methods to the protocol layer 
 * @author Kevin Burdick 
 */
 
import message.Connect;
import message.Open;
import message.Move;
import message.ReqLock;
import message.Release;
import message.ReqContents;
import message.Sync;
import message.Edit;
import message.Close;

/** Main program class for the client application 
 */
public class CTP
{
	final short VERSION =1;
    final int PORT = 5449;
    static short clientID=0;
	static Transport transport;
	static StateMachine sMachine;
	private static CTP instance;
	
    public CTP(String host) {
    	transport = new Transport(host,PORT); 
        sMachine = new StateMachine(transport); 
        instance = this;
	 }
    
   
    
    /** 
     * Method connects to the server 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse Connect() 
    {
    	Connect connect = new Connect(VERSION);
    	return sMachine.Process(sMachine.SENDING, connect);	
	}
   
    /** 
     * Method Opens a file on the server 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse Open() 
	{
    	Open openMsg = new Open(clientID,3214);
    	openMsg.dumpMsg();
    	return sMachine.Process(sMachine.SENDING, openMsg);
    }

    /** 
     * Method request the file lock from the server 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse ReqLock() 
	{
    	ReqLock rlock = new ReqLock(clientID);
    	return sMachine.Process(sMachine.SENDING, rlock);
    }

    /** 
     * Method requests the file lock be release from the server
     * requires that this client have ownership of the lock 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse ReleaseLock() 
	{
    	Release rel = new Release(clientID);
    	return sMachine.Process(sMachine.SENDING, rel);
    }

    /** 
     * Method sends a MOVE message to the server indicating the new position 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse Move(int cursorPos) 
	{
    	Move move = new Move(clientID,cursorPos);
        return sMachine.Process(sMachine.SENDING, move);
	}
    
    /** 
     * Method requests the contents of a file
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse ReqContents(int cursorLoc, int size) 
	{
        ReqContents req = new ReqContents(clientID,cursorLoc,size);
        return sMachine.Process(sMachine.SENDING, req);
    }

    /** 
     * Method sends a Sync message to the server request a status message
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse Sync() 
	{
    	Sync sync = new Sync(clientID);
    	return sMachine.Process(sMachine.SENDING, sync);
    }

    /** 
     * Method sends a Edit message to the server 
     * returns 1 on success
     *         0 on a failed response from server
     *        -1 on a invalid request to the state machine 
     */
    public CTPResponse Edit(byte action, int cursorLoc, byte[] data) 
	{
        Edit edit = new Edit(clientID,action,cursorLoc, data);
        return sMachine.Process(sMachine.SENDING, edit);
    }
    
    /** 
     * Method sends a Close message to the server 
     * returns 1 on success
     */
    public CTPResponse Close() 
	{
        //request
        Close close = new Close();
        CTPResponse response =  sMachine.Process(sMachine.SENDING, close);
        close();//now close the socket
        return response;
        
    }

   /** 
    * Method closes the transport layer 
    */
    private void close()
    {
        //close socket
        transport.close();
    }
    
    /** 
     * Method sets the id from the AckConnect response message
     */
     public void setClientID(short id)
     {
         //close socket
    	 System.out.println("client id "+id);
         clientID = id;
     }
     
     static CTP getInstance() {
    	 return instance;
     }
}
    
