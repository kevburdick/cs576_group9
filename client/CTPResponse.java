/*
 * This class defines the Response from the CTP protocol which
 * may include multiple responses if there are async messages received 
 * in addition to the expected response 
 * @author Kevin Burdick 
 */
 
import java.util.ArrayList;

import message.Contents;
import message.Edit;
import message.Message;
import message.Move;
import message.Status;
import message.Error;

public class CTPResponse 
{

   /**
    *  Constructor
    *  @param is the specific response type the client application was waiting for
    */
   public CTPResponse(int waitingMsgResponseType)
   {
	   wMsgResponseType = waitingMsgResponseType; 
	   responseList = new ArrayList<Response>();
   }
   
   /**
    *  Constructor that is used in the case there was an invalid operation by user
    *  so there is no response from server only a error returned and no message sent
    *  @param is the response type the client application was waiting for
    *  @param is the status returned 
    */
   public CTPResponse(int waitingMsgResponseType,int pStatus)
   {
	   wMsgResponseType = waitingMsgResponseType; 
	   responseList = new ArrayList<Response>();
	   Message msg = null;
	   errorStatus = pStatus;
	   Response resp = new Response(msg);
	   responseList.add(resp);
   }
   
   /**
    *  This method return the sync response the protocol is waiting for
    */
   public int getWaitResponseType()
   {
       return wMsgResponseType;
   }
   
   /**
    *  Constructor
    *  @return number of message responses in the object, should 
    *  be equal or greater then 1 
    */
   public int GetNumResponses()
   {
	   return responseList.size();
   }
   
   /**
    *  Add additional response to the object
    */
   public void LoadReponse(Message msg)
   {
	   Response resp = new Response(msg);
	   responseList.add(resp);
   }
   
   /**
    *  @param the list index of the response
    *  @returns the response type
    */    
   public int getReponseType(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getReponseType();
   }
   
   /**
    *  Method provides status of response:  1 on success, 0 on failed response,
    *  -1 on a invalid request to the state machine 
    *  @param the list index of the response
    */
   public int getStatus(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getStatus();
   }
   
   /**
    *  Method provides file data from an edit or contents messages 
    *  @param the list index of the response
    */
   public byte[] getFilData(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getFilData();
   }
   
   /**
    *  Method provides Error text from an error message 
    *  @param the list index of the response
    */
   public String getErrorText(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getErrorText();
   }
     
   /**
    *  Method provides position in file from contents message, move 
    *  message, or Edit message
    *  @param the list index of the response
    */
   public int getPosInfile(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getPosInfile();
   }
  
   /**
    *  Method provides checksum from status message
    *  @param the list index of the response
    */
   public byte[] getChecksum(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getChecksum();
   }
   
   /**
    *  Method provides checksum from Edit message
    *  @param the list index of the response
    */
   public byte getEditAction(int reponseIndex)
   {
	   return responseList.get(reponseIndex).getEditAction();
   }
   
   /**
    *  Method provides overall error status of response
    *  A -1 is invalid client user request and 0 is a no response from server
    */
   public int getErrorStatus()
   {
	   return errorStatus;
   }

   private ArrayList <Response> responseList = new ArrayList<Response>();
   private int wMsgResponseType; //holds the sync response type that client is waiting for
   private int errorStatus;//holds the error status set by the state machine, -1 invalid request, 0 no response from server 
   
	   
 //Numbers are skipped to keep in sync with CTP message types
   public static final short INVALID_REQ_RSP= 0;
   public static final short ERROR_RSP= 1;
   public static final short ACKCONNECT_RSP= 3;
   public static final short ACKOPEN_RSP= 5;
   public static final short ACKLOCK_RSP= 6;
   public static final short ACKEDIT_RSP= 7;
   public static final short SERVRELEASE_RSP= 8;
   public static final short CONTENTS_RSP= 10;
   public static final short MOVE_RSP= 11;
   public static final short STATUS_RSP= 15;
   public static final short EDIT_RSP= 16;
   public static final short CLOSE_RSP= 17; 
   //edit types
   public static final short INS_EDIT = 1; 
   public static final short OVR_EDIT = 2;
   public static final short DEL_EDIT = 3;
   
   //////////////////////////////////////////////////////////////////
   
   /**
    *  The private inner class holds a single response info coming from CTP
    *  and is data provided to the user 
    */
   protected class Response 
   {
	   Response(Message msg) 
	   {
		   //case is invalid client request
		   if(msg == null) {
			   //invalid user request
			   if(getErrorStatus()==-1) {
				   responseType = ERROR_RSP;
				   status = -1;
				   errtext = "Error: Invalid client request";  
			   }
			   else {//no server response
				   responseType = ERROR_RSP;
			   	   status = 0;
			   	   errtext = "Error: No server response";  
			   }
		   }   
		   //if is an error store error text for user
		   else if(msg.getType()==Message.ERROR_TYPE) {
			   responseType = ERROR_RSP;
			   //Error err = (Error)msg;
			   errtext = Error.errorText[Message.ERROR_TYPE];  
		       status = 0;
		   }
		   //if its a edit message store data
		   else if(msg.getType()==Message.ACKCONNECT_TYPE) {
			   responseType = ACKCONNECT_RSP;
			   status = 1;
		   }
		   else if(msg.getType()==Message.ACKOPEN_TYPE) {
			   responseType = ACKOPEN_RSP;
			   status = 1;	   
		   }
		   else if(msg.getType()==Message.ACKLOCK_TYPE) {
			   responseType = ACKLOCK_RSP;
			   status = 1;	   
		   }	   
		   else if(msg.getType()==Message.ACKEDIT_TYPE) {
			   responseType = ACKEDIT_RSP;
			   status = 1;	   
		   }
		   else if(msg.getType()==Message.SERVRELEASE_TYPE) {
			   responseType = SERVRELEASE_RSP;
			   status = 1;	   
		   }
		   else if(msg.getType()==Message.CONTENTS_TYPE) {
			   responseType = CONTENTS_RSP;
			   Contents contents = (Contents)msg;
			   status = 1;	  
			   positionInfile = contents.getFilePosition();
			   byte[] temp = contents.getFileData();
			   fileData = new byte[ temp.length];
			   System.arraycopy(temp, 0, fileData, 0, temp.length);   
		   }
		   else if(msg.getType()==Message.MOVE_TYPE) {
			   responseType = MOVE_RSP;
			   Move move = (Move)msg;
			   status = 1;	  
			   positionInfile = move.getCursorPos();
		   }
		   else if(msg.getType()==Message.STATUS_TYPE) {
			   responseType = STATUS_RSP;
			   Status statusMsg = (Status)msg;
			   status = 1;	 
			   byte[] temp = statusMsg.getChecksum();
			   checksum= new byte[32];
			   System.arraycopy(temp,0,checksum,0,32);  
		   }
		   else if(msg.getType()==Message.EDIT_TYPE) {
			   responseType = EDIT_RSP;
			   Edit edit = (Edit)msg;
			   status = 1;	 
			   editAction = edit.getAction();
			   positionInfile= edit.getFilePosition();
			   byte[] temp = edit.getFileData();
			   fileData = new byte[temp.length];
			   System.arraycopy(temp, 0, fileData, 0, temp.length);    
		   }
		   else if(msg.getType()==Message.CLOSE_TYPE) {
			   responseType = CLOSE_RSP;  
			   status = 1;	   
		   }
		   		   
	   }
	   
	   public int getReponseType()
	   {
		   return responseType;
	   }
	   
	   public int getStatus()
	   {
		   return status;
	   }
	   
	   public byte[] getFilData()
	   {
		   return fileData;
	   }
	   
	   public String getErrorText()
	   {
		   return errtext;
	   }
	   
	      
	   //from contents message, move message, or Edit message
	   public int getPosInfile()
	   {
		   return positionInfile;
	   }
	   
	   //from status message
	   public byte[] getChecksum()
	   {
		   return checksum;
	   }
	   
	  //from Edit message
	   public byte getEditAction()
	   {
		   return editAction;
	   }
	   
	   private int responseType;
	   private int status;//1 on success, 0 on failed response, -1 on a invalid request to the state machine 
	   private  byte[] fileData = null;//from an edit or contents messages
	   private String errtext = null;//from error message
	   private int positionInfile =0;//from contents message, move or Edit message
	   private byte[] checksum;//from status message
	   private byte editAction;//from Edit message
	   
	   
   }//end response class
   
  

}
