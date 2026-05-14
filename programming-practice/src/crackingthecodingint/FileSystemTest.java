package crackingthecodingint;

import java.util.*;

public class FileSystemTest {
    public static void main(String[] args) {

        FileSystem fs=new FileSystem();
        System.out.println(fs.ls("/"));
        fs.mkdir("/a/b/c");
        fs.addContentToFile("/a/b/c/d", "hello");
        System.out.println(fs.ls("/"));
        System.out.println(fs.readContentFromFile("/a/b/c/d"));

        List<Interval> res=new ArrayList<>();
        Collections.sort(res, (o1,o2) -> Integer.compare(o1.start,o2.start));
    }
}


class FileSystem {

    DNode root;
    public FileSystem() {
        root=new DNode(false, "");
    }

    public List<String> ls(String path) {
        String[] p=path.split("/");
        DNode curr = root;
        for(int i=1;i<p.length;i++) {
            curr = curr.children.get(p[i]);
        }

        List<String> list = new ArrayList<>();
        if(curr!=null) {
            if(!curr.isFile) {
                list = new ArrayList<>(curr.children.keySet());
//                Collections.sort(list);
                return list;
            }
            else {
                return List.of(curr.name);
            }
        }
        return list;
    }

    public void mkdir(String path) {
        String[] p=path.split("/");
        DNode curr = root;
        for(int i=1;i<p.length;i++) {
            curr.children.putIfAbsent(p[i], new DNode(false, p[i]));
            curr = curr.children.get(p[i]);
        }

    }

    public void addContentToFile(String filePath, String newContent) {
        String[] p=filePath.split("/");
        DNode curr=root;

        for(int i=1;i<p.length-1;i++) {
            curr.children.putIfAbsent(p[i], new DNode(false, p[i]));
            curr = curr.children.get(p[i]);
        }

        String fileName = p[p.length-1];
        DNode newNode = new DNode(true, fileName);
        curr.children.putIfAbsent(fileName, newNode);
        curr = curr.children.get(fileName);

        if(curr!=null && curr.isFile)
            curr.content.append(newContent);
    }

    public String readContentFromFile(String filePath) {
        String[] p=filePath.split("/");
        DNode curr=root;
        for(int i=1;i<p.length;i++) {
            if(curr == null)
                return "";
            curr = curr.children.get(p[i]);
        }

        if(curr!=null && curr.isFile)
            return curr.content.toString();
        else return "";
    }

//    private DNode traverse(String filePath) {
//        String[] p=filePath.split("/");
//        DNode curr=root;
//
//        for(int i=1;i<p.length-1;i++) {
//            curr.children.putIfAbsent(p[i], new DNode(false, p[i]));
//            curr = curr.children.get(p[i]);
//        }
//        return curr;
//    }
}

class DNode {
    boolean isFile;
    StringBuilder content;
    String name;
    TreeMap<String, DNode> children;
    public DNode(boolean isFile, String name) {
        this.isFile = isFile;
        this.name = name;
        if(!isFile) {
            content = null;
            children = new TreeMap<>();
        }
        else content=new StringBuilder();


    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */



class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
}

