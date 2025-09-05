
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
// import ResponseMessage if needed, e.g.:;
  // Example: Convert object to byte array
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
      
      /*In this stage, you'll replace the hard-coded correlation ID with the actual correlation ID from the request.

Request message
A request message has three parts:

message_size
Header
Body
To get the correlation_id field, you need to find its offset. You already know that message_size is 4 bytes long. And here's what the request header looks like (in this stage, we're using request header v2):

Field	Data type	Description
request_api_key	INT16	The API key for the request
request_api_version	INT16	The version of the API for the request
correlation_id	INT32	A unique identifier for the request
client_id	NULLABLE_STRING	The client ID for the request
TAG_BUFFER	COMPACT_ARRAY	Optional tagged fields
To learn more about the different data types, see Protocol Primitive Types.

Example
Here's an example of a request message:

00 00 00 23  // message_size:        35
00 12        // request_api_key:     18
00 04        // request_api_version: 4
6f 7f c6 61  // correlation_id:      1870644833 */

      //first 12 bytes in header before correlation_id
      byte[] headerBytes = new byte[12];
      int bytesRead = clientSocket.getInputStream().read(headerBytes);
      if (bytesRead != 12) {
          System.out.println("Error: Expected to read 12 bytes for header, but got " + bytesRead);
      } else {
          System.out.print("Header bytes: ");
          for (byte b : headerBytes) {
              System.out.printf("%02x ", b);
          }
          System.out.println();
      }

      ByteBuffer headerBuffer = ByteBuffer.wrap(headerBytes);
      headerBuffer.position(8); // Move to the position of correlation_id
      int correlationId = headerBuffer.getInt();
      System.out.println("Correlation ID: " + correlationId);

      ByteBuffer responsByteBuffer = ByteBuffer.allocate(4);
      responsByteBuffer.putInt(correlationId);

      ResponseMessage rcvivedMessage = new ResponseMessage( null, null) ;
      java.util.HashMap<String, byte[]> headerMap = new java.util.HashMap<>();
      headerMap.put("correlation_id", responsByteBuffer.array());
      rcvivedMessage.setHeader(headerMap);
      clientSocket.getOutputStream().write(rcvivedMessage.getHeader().get("correlation_id"));
      // ByteBuffer buffer = ByteBuffer.allocate(8);
      // buffer.putLong(7);
      //headerMap.put("correlation_id", buffer.array());
      // rcvivedMessage.setHeader(headerMap);
      // rcvivedMessage.setMessage_size(5);
      // byte[] bodyContent = new byte[]{10,20,30,40};
      // java.util.HashMap<String, byte[]> bodyMap = new java.util.HashMap<>();
      // bodyMap.put("body", bodyContent);
      // rcvivedMessage.setBody(bodyMap);
      // System.out.println("Message Size: " + rcvivedMessage.getMessage_size());
      // System.out.println("Headder correlation_id: " + rcvivedMessage.getHeader().get("correlation_id"));
      // for (byte b : rcvivedMessage.getHeader().get("correlation_id")) {
      //     System.out.print(b + " ");
      // }
      // System.out.print("Body content: ");
      // for(byte b : rcvivedMessage.getBody().get("body")) {
      //     System.out.print(b + " ");
      // }

      //byte[] rcvivedBytes = toBytes(rcvivedMessage);
      // System.out.println("\nTotal bytes to send: " + rcvivedBytes.length);
      // clientSocket.getOutputStream().write(rcvivedBytes);
      //clientSocket.getOutputStream().write(rcvivedMessage.getHeader().get("correlation_id"));
     
      

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
