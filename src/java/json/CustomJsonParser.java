package json;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class CustomJsonParser {

    private String key;
    private HttpServletRequest request;
    private Map<String, String> map;

    public CustomJsonParser(String key, HttpServletRequest request) {
        this.key = key;
        this.request = request;
        setMap(getStringFromAjaxRequest());
    }

    public CustomJsonParser() {

    }

    private Map<String, String> getStringFromAjaxRequest() {
        String response = "";
        try {
            InputStream is = getRequest().getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] buf = new byte[32];
            int r = 0;
            while (r >= 0) {
                r = is.read(buf);
                if (r >= 0) {
                    os.write(buf, 0, r);
                }
            }
            String s = new String(os.toByteArray(), "UTF-8");

            Map<String, String> map = makeQueryMap(s);
            return map;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public String getString() throws JSONException {
        JSONObject obj = new JSONObject(getMap());
        return obj.getString(getKey());
    }

    private static Map<String, String> makeQueryMap(String query) throws UnsupportedEncodingException {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] split = param.split("=");
            map.put(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8"));
        }
        return map;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the map
     */
    private Map<String, String> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public final void setMap(Map<String, String> map) {
        this.map = map;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
}
