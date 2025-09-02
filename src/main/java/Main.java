
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
// import ResponseMessage if needed, e.g.:;
  // Example: Convert object to byte array
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
public class Main {



  public static byte[] toBytes(Object obj) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(obj);
      oos.flush();
      return bos.toByteArray();
  }
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 9092;
    try {
      System.out.println("Read byte: " + port);
      serverSocket = new ServerSocket(port);
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      // Wait for connection from client.
      clientSocket = serverSocket.accept();
      System.out.println("Accepted connection from " + clientSocket);
      // Now you can read/write from/to clientSocket.
      
      // Example: read a byte
      int read_val= clientSocket.getInputStream().read();
      System.out.println("Read byte: " + read_val);

      ResponseMessage rcvivedMessage = new ResponseMessage( null, null) ;
      java.util.HashMap<String, byte[]> headerMap = new java.util.HashMap<>();
      ByteBuffer buffer = ByteBuffer.allocate(4);
      buffer.putInt(7);
      headerMap.put("correlation_id", buffer.array());
      rcvivedMessage.setHeader(headerMap);
      rcvivedMessage.setMessage_size(5);
      byte[] bodyContent = new byte[]{10,20,30,40};
      java.util.HashMap<String, byte[]> bodyMap = new java.util.HashMap<>();
      bodyMap.put("body", bodyContent);
      rcvivedMessage.setBody(bodyMap);
      System.out.println("Message Size: " + rcvivedMessage.getMessage_size());
      System.out.println("Headder correlation_id: " + rcvivedMessage.getHeader().get("correlation_id"));
      for (byte b : rcvivedMessage.getHeader().get("correlation_id")) {
          System.out.print(b + " ");
      }
      System.out.print("Body content: ");
      for(byte b : rcvivedMessage.getBody().get("body")) {
          System.out.print(b + " ");
      }

      byte[] rcvivedBytes = toBytes(rcvivedMessage);
      // System.out.println("\nTotal bytes to send: " + rcvivedBytes.length);
      // clientSocket.getOutputStream().write(rcvivedBytes);
      clientSocket.getOutputStream().write(rcvivedMessage.getHeader().get("correlation_id"));
      
      

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}
