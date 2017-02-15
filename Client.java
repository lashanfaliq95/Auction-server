import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client { 
    public static final String host = "localhost"; 

    private int private_socket = 0; 
    private Socket socket; 
    private BufferedReader in; 
    private PrintWriter out;
    

    public Client(int socket_number) throws IOException { 
	socket = new Socket(host, socket_number); 
	in  = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
	out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); 
	
	out.print(Server.init_request + "\n"); 
	out.flush(); 

	String comPort = in.readLine(); 
	System.out.println(comPort); 

	try { 
	    int newPortNum = Integer.valueOf(comPort);
	    private_socket = newPortNum; 
	    System.out.println("New Port " + newPortNum); 
	} catch (NumberFormatException e) { 
	    System.out.println("Server did not give port");
	} finally { // clean up
	    in.close(); 
	    in = null; 
	    out.close(); 
	    out = null;
	    socket.close(); 
	    socket = null; 
	}

    }

    public void handle() throws IOException { 
	System.out.println("In handle with " + private_socket);
	try { 
	    socket = new Socket(host, private_socket); 
	    in  = new 
		BufferedReader(new InputStreamReader(socket.getInputStream())); 
	    out = new 
		PrintWriter(new OutputStreamWriter(socket.getOutputStream())); 

	    while(true) { 
		out.print("This is a test\n"); 
		out.flush(); 
		System.out.println(in.readLine());
		try { 	    
		    Thread.sleep(500); 
		} catch (InterruptedException e) { 
		    System.out.println(e);
		}
	    }
	} finally { 
	    in.close(); 
	    out.close(); 
	    socket.close();
	}
    }    

    public static void main(String [] agrs) throws IOException { 
	Client c = new Client(Server.BASE_PORT); 
	c.handle();
    }
	    
	
	
}

	    
    

    

    
    