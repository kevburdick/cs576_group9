/*
 * This is the main entry into the client application. This represents the Application layer for this project
 * and uses the CTP protocol to communication with the server. 
 * @author Kevin Burdick
 *
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** Main program class for the client application 
 */
public class Client
{
	static CTP ctp;
	
    public static void main(String[] args)
    {
        
        //This is one black box test that corresponds to scenario outline in the Server test file.
        //This executes the API and the protocol code is run against the Server test stub
        //An application layer would replace this and place similar calls 
    	
        String host;
        String menuSelection; 
    	InputStreamReader input = new InputStreamReader(System.in) ;
    	BufferedReader inputBuff = new BufferedReader(input) ;   	
    	
        //check for host and throw error if not provided
        if(args.length!=1) {
        	System.out.println("Application error: command line arguement missing\n Usage: \n " +
        			"java client <IPAddress> or \n java client <hostname>");
        	return;
        }
            
        //System.out.println("arg[0] "+args[0]);
        host = args[0]; 
        System.out.println("client starting");
        ctp = new CTP(host);
    	
    	while(true) {
    		DisplayMainMenu();
    		try {
	    		menuSelection = inputBuff.readLine();
	    		if(menuSelection.equals("1"))
	    			SendMessage();
	    		else if(menuSelection.equals("2"))
	    			ReadMessage();
	    		else if(menuSelection.equals("3"))
	    			RegressionTest();
	    		System.out.println("Press return key to continue");
	    		menuSelection = inputBuff.readLine();
	    		//roll screen
	    		for(int i=0;i<6;i++)
	    			System.out.print("\n"); 	
	    		
	    	}
    		catch (IOException err) {
    			System.out.println("Error reading line");	
    		}
    		
    	}//end while loop
    }
    	
    	
  	static void RegressionTest()
    { 	
  		CTPResponse response;
    
        System.out.println("client connect");
        response = ctp.Connect();
        processResponse(response);
        System.out.println("client open");
        response = ctp.Open();
        processResponse(response);
        System.out.println("client request lock");
        response = ctp.ReqLock();
        processResponse(response);
        System.out.println("client release lock");
        response = ctp.ReleaseLock();
        processResponse(response);
        System.out.println("client move");
        response = ctp.Move(255);
        processResponse(response);
        System.out.println("client request Contents");
        response = ctp.ReqContents(255,255);
        processResponse(response);
        System.out.println("client sync");
        response = ctp.Sync();
        processResponse(response);
        System.out.println("client edit");
        byte[] data = new byte[256];
        response = ctp.Edit((byte)1,245,data);
        processResponse(response);
        System.out.println("client close");
        response = ctp.Close();
        processResponse(response);
        System.out.println("client ending");
        
       
    }
    
  	static void SendMessage()
    {
        String menuSelection; 
        InputStreamReader input = new InputStreamReader(System.in) ;
    	BufferedReader inputBuff = new BufferedReader(input) ;
        
    
        try {
		    // ServerSocket listenSock = new ServerSocket(port);
			//  server = new TCPSock(listenSock.accept()); 
			  DisplaySelectMessageMenu();
			  menuSelection = inputBuff.readLine();
			  int selection = Integer.parseInt(menuSelection);
			  CTPResponse response;
			  
			  switch(selection) 
			  {
				 
				  case (1): {
					  System.out.println("2)CONNECT");
					  response = ctp.Connect();
				      processResponse(response);
					  break;
				  }
				  
				  case (2): {
					  System.out.println("4)OPEN");
					  response = ctp.Open();
				      processResponse(response);
					  break;
				  }
				  
				  
				  case (3): {
					  System.out.println("9)REQCONTENTS");
					  response = ctp.ReqContents(255,255);
				      processResponse(response);
					  break;
				  }
				  
				  case (4): {
					  System.out.println("11)MOVE");
					  response = ctp.Move(255);
				      processResponse(response);
					  break;
				  }
				  case (5): {
					  System.out.println("12)REQLOCK");
					  response = ctp.ReqLock();
				      processResponse(response);
					  break;
				  }
				  case (6): {
					  System.out.println("13)RELEASE");
					  response = ctp.ReleaseLock();
				      processResponse(response);
					  break;
				  }	
				  case (7): {
					  System.out.println("14)SYNC");
					  response = ctp.Sync();
				      processResponse(response);
					  break;
				  }	
				 
				  case (8): {
					  System.out.println("16)EDIT");
					  byte[] data = new byte[256];
				      response = ctp.Edit((byte)1,245,data);
					  break;
				  }
				  case (9): {
					  System.out.println("17)CLOSE");
					  response = ctp.Close();
				      processResponse(response);
					  break;
				  }
				  default: {
					  System.out.println("Invalid action selected");
				  }
			  }//end switch	  
		}
        catch(Exception e) {
           System.out.println("Exception"+e.toString());
		}
    	
    }
    
    static void ReadMessage()
    {
    	System.out.println("Not implemented yet");
    }
  	
    /*
     * This method displays main menu of options to user
     */
    static void DisplayMainMenu()
    {
    	System.out.println("******");
    	System.out.println("Menu");
    	System.out.println("******");
    	System.out.println("Enter Selection");
    	System.out.println("1) Send Message");
    	System.out.println("2) Receive Message");
    	System.out.println("3) Run regression test");
    }
    
    
    /*
     * This method handles the response object coming back from the CTP protocol. The object 
     * contains both the status and any application level information
     * and uses the CTP protocol to communication with the server.
     * @param respone object from CTP 
     */
    static void processResponse(CTPResponse response)
    {
    	for(int i=0;i<response.GetNumResponses();i++)
    	{
    		int type = response.getReponseType(i);
    		switch (type) {
	    		case CTPResponse.ERROR_RSP:{
	    			if(response.getStatus(i)== -1)
	    			System.out.println("ERROR_RSP Invalid client request");	
	    			else
	    			System.out.println("ERROR_RSP "+response.getErrorText(i));	
	    			break;
	    		}
	    		case CTPResponse.ACKCONNECT_RSP:{
	    			System.out.println("ACKCONNECT_RSP received");	
	    			break;
	    		}
				case CTPResponse.ACKOPEN_RSP:{
					System.out.println("ACKOPEN_RSP received");	
					break;
				}
				case CTPResponse.ACKLOCK_RSP:{
					System.out.println("ACKLOCK_RSP received");	
					break;
				}
				case CTPResponse.ACKEDIT_RSP:{
					System.out.println("ACKEDIT_RSP received");	
					break;
				}
				case CTPResponse.SERVRELEASE_RSP:{
					System.out.println("SERVRELEASE_RSP received");	
					break;
				}
				case CTPResponse.CONTENTS_RSP:{
					System.out.println("CONTENTS_RSP received");	
					System.out.println("Position in file = "+response.getPosInfile(i));
					System.out.println("file data = "+response.getFilData(i));
					break;
				}
				case CTPResponse.MOVE_RSP:{
					System.out.println("MOVE_RSP received");	
					System.out.println("Cursor position in file= "+response.getPosInfile(i));
					break;
				}
				case CTPResponse.STATUS_RSP:{
					System.out.println("STATUS_RSP received");	
					System.out.println("Checksum = "+response.getChecksum(i));
					break;
				}
				case CTPResponse.EDIT_RSP:{
					System.out.println("EDIT_RSP received");	
					System.out.println("action = "+response.getEditAction(i));
					System.out.println("Position in file = "+response.getPosInfile(i));
					System.out.println("file data = "+response.getFilData(i));
					break;
					
				}
				case CTPResponse.CLOSE_RSP:{
					System.out.println("CLOSE_RSP received");	
					break;
				}
				default: 
					System.out.println("Invalid Response type received");	
    		}//end switch
    	}//end for loop
    }
    
    static void DisplaySelectMessageMenu()
    {
    	System.out.println("*******************");
    	System.out.println("Select Message Menu");
    	System.out.println("*******************");
    	System.out.println("Enter Selection");
    	System.out.println("1)CONNECT");
    	System.out.println("2)OPEN");
    	System.out.println("3)REQCONTENTS");
    	System.out.println("4)MOVE");
    	System.out.println("5)REQLOCK");
    	System.out.println("6)RELEASE");
    	System.out.println("7)SYNC");
    	System.out.println("8)EDIT");
    	System.out.println("9)CLOSE");
    }

    
}

    
