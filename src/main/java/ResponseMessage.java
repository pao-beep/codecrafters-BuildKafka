
import java.util.Map;
import java.util.HashMap;
public class ResponseMessage implements java.io.Serializable {
    private byte[] message_size; 
    private Map<String,byte[]> header = new HashMap<>();
    private Map<String, byte[]> body = new HashMap<>();

    public ResponseMessage(Map<String,byte[]> header, Map<String,byte[]> body) {
        //this.message_size = message_size;
        this.header = header;
        this.body = body;
    }
    public long getMessage_size() {
        return message_size.length;
    }
    public Map<String, byte[]> getHeader() {
        return header;
    }
    public Map<String, byte[]> getBody() {
        return body;
    }

    public void setMessage_size(int message_size) {
        this.message_size = new byte[message_size];
    }
    public void setHeader(Map<String, byte[]> header) {
        this.header = header;
    }
    public void setBody(Map<String, byte[]> body) {
        this.body = body;
    }


}
