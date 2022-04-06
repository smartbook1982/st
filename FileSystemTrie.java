package Airbnb;

import java.util.*;

public class FileSystemTrie
{
    TrieNode root = new TrieNode();

    public boolean create (String path, Integer value) {
        String[] pathArr = path.split("/");
        if (pathArr.length < 2) return false;
        TrieNode head = root;
        for (int i=1; i<pathArr.length - 1; i++) {
            if (!head.children.containsKey(pathArr[i])) {
                return false;
            }
            head = head.children.get(pathArr[i]);
        }
        head.children.putIfAbsent(pathArr[pathArr.length-1], new TrieNode());
        head = head.children.get(pathArr[pathArr.length-1]);
        head.value = value;
        return true;
    }

    public boolean set (String path, Integer value) {
        String[] pathArr = path.split("/");
        if (pathArr.length < 2) return false;
        TrieNode head = root;
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (int i=1; i<pathArr.length; i++) {
            if (!head.children.containsKey(pathArr[i])) {
                return false;
            }
            head = head.children.get(pathArr[i]);
            runnables.addAll(head.runnableList);
        }
        head.value = value;
        for (Runnable ra : runnables) {
            ra.run();
        }
        return true;
    }

    public Integer get (String path) {
        String[] pathArr = path.split("/");
        if (pathArr.length < 2) return null;
        TrieNode head = root;
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (int i=1; i<pathArr.length; i++) {
            if (!head.children.containsKey(pathArr[i])) {
                return null;
            }
            head = head.children.get(pathArr[i]);
        }
        return head.value;
    }

    public boolean watch (String path, Runnable runnable) {
        String[] pathArr = path.split("/");
        if (pathArr.length < 2) return false;
        TrieNode head = root;
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (int i=1; i<pathArr.length; i++) {
            if (!head.children.containsKey(pathArr[i])) {
                return false;
            }
            head = head.children.get(pathArr[i]);
            runnables.addAll(head.runnableList);
        }
        head.runnableList.add(runnable);
        return true;
    }





    public static void main(String[] args) {

        FileSystemTrie s = new FileSystemTrie();
        s.create("/a", 1);
        s.create("/a/b", 2);
        s.create("/a/b/c", 3);
        System.out.println(s.get("/a/b/c"));
        System.out.println(s.set("/a/b", 4));
        System.out.println(s.watch("/a/b", new MyRunnableTask()));
        System.out.println(s.watch("/a", new MyRunnableTask()));
        System.out.println(s.set("/a/b/c", 5));
        System.out.println(s.get("/a/b/c"));
        System.out.println(s.get("/a/b"));
    }



}

class TrieNode {
    Map<String, TrieNode> children;
    Integer value;
    List<Runnable> runnableList;

    public TrieNode () {
        children = new HashMap<>();
        runnableList = new ArrayList<>();
    }
}

class MyRunnableTask implements Runnable {
    public void run() {
        System.out.println("HIHI1");
    }
}
