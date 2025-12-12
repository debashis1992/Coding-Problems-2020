package consistenthashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ConsistentHashing ch = new ConsistentHashing(3);
        ch.addServer("s1");
        ch.addServer("s2");
        ch.addServer("s3");

        System.out.println(ch.getServer("k1"));
        System.out.println(ch.getServer("k2"));
        System.out.println(ch.getServer("k3"));

        ch.removeServer("s3");
        System.out.println("-----");

        System.out.println(ch.getServer("k1"));
        System.out.println(ch.getServer("k2"));
        System.out.println(ch.getServer("k3"));



    }
}

class ConsistentHashing {
    private final TreeMap<Long, String> ring;
    private final int instances;
    private final MessageDigest md5;

    public ConsistentHashing(int instances) throws NoSuchAlgorithmException {
        ring=new TreeMap<>();
        this.instances=instances;
        md5=MessageDigest.getInstance("MD5");
    }

    public void addServer(String serverName) {
        for(int i=0;i<instances;i++) {
             long hashValue = generateHashWithSuffix(i, serverName);
             ring.put(hashValue, serverName);
        }
    }

    public void removeServer(String serverName) {
        for(int i=0;i<instances;i++) {
            long hashValue = generateHashWithSuffix(i, serverName);
            ring.remove(hashValue);
        }
    }

    public String getServer(String key) {
        if(ring.isEmpty()) return null;

        long hash = generateHash(key);
        if(!ring.containsKey(hash)) {
            SortedMap<Long, String> map = ring.tailMap(hash);
            hash = map.isEmpty() ? ring.firstKey() :  map.firstKey();
        }
        return ring.get(hash);
    }

    public long generateHashWithSuffix(int suffix, String serverName) {
        String name = serverName+"_"+suffix;
        return generateHash(name);
    }
    public long generateHash(String serverName) {
        md5.reset();
        md5.update(serverName.getBytes());
        byte[] digest = md5.digest();
        long res = new BigInteger(digest).longValue();
        return res;
//        return new String(digest);
    }
}
