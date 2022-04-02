package Airbnb;

import java.util.*;

public class BoggleGame
{

    char[][] board;
    String[] words;
    boolean[][] visited;
    TrieNode root;
    int row;
    int col;
    List<String> res;

    public BoggleGame (char[][] board, String[] words) {
        this.board = board;
        this.words = words;
        this.row = board.length;
        this.col = board[0].length;
        this.visited = new boolean[row][col];
        this.root = new TrieNode();
        this.res = new ArrayList<>();
    }

    public void getAllWords () {
        for (String word : words) {
            addToTrie(word);
        }

        findMaxWords (0, 0, new ArrayList<>());
    }

    public void addToTrie (String word) {
        TrieNode head = root;
        for (int i=0; i< word.length(); i++) {
            head.children.putIfAbsent(word.charAt(i), new TrieNode());
            head = head.children.get(word.charAt(i));
        }
        head.isWord = true;
    }

    public void findMaxWords (int x, int y, List<String> currRes) {
        for (int i=x; i<row; i++) {
            for(int j=y; j < col; j ++) {
                // find all possible path;
                List<List<Integer>> paths = new ArrayList<>();
                getAllPath (i, j, root, new ArrayList<>(), paths);
                // for each path, we add to the currRes ( dfs )
                for (List<Integer> path : paths) {
                    StringBuilder sb = new StringBuilder();
                    for (int idx : path) {
                        int pathX = idx / col;
                        int pathY = idx % col;
                        sb.append(board[pathX][pathY]);
                        visited[pathX][pathY] = true;
                    }
                    currRes.add(sb.toString());

                    if (currRes.size() > res.size()) {
                        res.clear();
                        res.addAll(currRes);
                    }

                    findMaxWords(x, y, currRes);

                    currRes.remove(currRes.size() - 1);
                    for (int idx : path) {
                        int pathX = idx / col;
                        int pathY = idx % col;
                        visited[pathX][pathY] = false;
                    }
                }
            }

        }
    }

    public void getAllPath (int x, int y, TrieNode node, List<Integer> curr, List<List<Integer>> path) {
        if (x<0 || x >= row || y < 0 || y >= col || visited[x][y] || node.children.get(board[x][y]) == null) return;
        TrieNode nextNode = node.children.get(board[x][y]);
        if (nextNode.isWord) {
            curr.add(x * col + y);
            path.add(new ArrayList<>(curr));
            return;
        }
        curr.add(x * col + y);
        visited[x][y] = true;
        getAllPath(x+1, y, nextNode, curr, path);
        getAllPath(x-1, y, nextNode, curr, path);
        getAllPath(x, y + 1, nextNode, curr, path);
        getAllPath(x, y - 1, nextNode, curr, path);
        visited[x][y] = false;
        curr.remove(curr.size() - 1);
    }


    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'}
        };
        String[] words = new String[] {
                "abc", "cfi", "beh", "defi", "gh"
        };

        BoggleGame s = new BoggleGame(board, words);
        s.getAllWords();
        for (String str : s.res) {
            System.out.println(str);
        }
    }

    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord;
    }



}
