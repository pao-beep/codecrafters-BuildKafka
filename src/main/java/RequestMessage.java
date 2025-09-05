public 
import java.util.Map;
import java.util.HashMap;

public class RequestMessage implements java.io.Serializable {
    private int message_size = 4096; 
    private int correlation_id = 0;
    int request_api_key = 0;
    int request_api_version = 0;
    int client_id = 0;
    int tag_buffer =0;
    private Map<String,byte[]> header = new HashMap<>();
    private Map<String, byte[]> body = new HashMap<>();

    public RequestMessage(Map<String,byte[]> header, Map<String,byte[]> body) {
        //this.message_size = message_size;
        this.header = header;
        this.body = body;
    }
    public RequestMessage(Map<String,byte[]> header, Map<String,byte[]> body, int correlation_id, int request_api_key, int request_api_version, int client_id, int tag_buffer) {
        //this.message_size = message_size;
        this.header = header;
        this.body = body;
        this.correlation_id = correlation_id;
        this.request_api_key = request_api_key;
        this.request_api_version = request_api_version;
        this.client_id = client_id;
        this.tag_buffer = tag_buffer;
    }
    public long getMessage_size() {
        return message_size;
    }
    public Map<String, byte[]> getHeader() {
        return header;
    }
    public Map<String, byte[]> getBody() {
        return body;
    }

    public void setMessage_size(int message_size) {
        this.message_size = message_size;
    }
    public void setHeader(Map<String, byte[]> header) {
        this.header = header;
    }
    public void setBody(Map<String, byte[]> body) {
        this.body = body;
    }
    
}
