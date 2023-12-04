package tinyUrl;

import java.util.HashMap;
import java.util.Map;

public class Codec {


    private Map<String, String> codeDB = new HashMap<>();
    private Map<String, String> urlDB = new HashMap<>();

    private final static String alp="0123456789abcdefjhijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ";
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if(urlDB.containsKey(longUrl))
            return urlDB.get(longUrl);

        String code=generateCode();
        codeDB.put(code, longUrl);
        urlDB.put(longUrl, code);
        return code;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return codeDB.get(shortUrl);
    }

    public String generateCode() {
        char[] c=new char[6];
        for(int i=0;i<6;i++) {
            c[i]= alp.charAt((int)(Math.random() * 62));
        }

        String code = "http://tinyurl.com/"+String.valueOf(c);
        return code;
    }

}
