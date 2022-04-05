class Solution {
    public String alienOrder(String[] words) {
        Map<Character, List<Character>> orderMap = new HashMap<>();      
        Map<Character, Integer> indegree = new HashMap<>();
        
        for(String w : words) {
            for(Character c : w.toCharArray()) {
                indegree.putIfAbsent(c, 0);
            }
        }
        
        for (int i=0; i<words.length-1; i++) {
            String w1 = words[i];
            String w2 = words[i+1];
            int min= Math.min(w1.length(), w2.length());
            int j=0;
            for (; j<min; j++) {
                if(w1.charAt(j) != w2.charAt(j) ) {
                    orderMap.putIfAbsent(w1.charAt(j), new ArrayList<>());
                    orderMap.get(w1.charAt(j)).add (w2.charAt(j));
                    indegree.put(w2.charAt(j), indegree.get(w2.charAt(j)) + 1);
                    break;
                }
            }
            
            if( j== min && w1.length() > w2.length() ) return "";
        }
        
        // bfs
        Queue<Character> queue = new LinkedList<>();
        for (Character c : indegree.keySet()) {
            if (indegree.get(c) == 0) {
                queue.add (c);
            }
        }
        
        List<Character> res = new ArrayList<>();        
        while (!queue.isEmpty()) {
            Character c = queue.poll ();
            res.add (c);
            List<Character> neighbors = orderMap.get(c);
            if (neighbors == null) continue;
            for (Character neighbor : orderMap.get(c)) {
                int idgree = indegree.get(neighbor);
                idgree--;
                indegree.put (neighbor, idgree);
                if (idgree == 0) {
                    queue.add (neighbor);
                }
            }
        }
        
        if (res.size() < indegree.size()) 
            return "";
        
        StringBuilder sb = new StringBuilder ();
        for (Character rc : res) {
            sb.append(rc);
        }
        
        return sb.toString();
        
    }
}


