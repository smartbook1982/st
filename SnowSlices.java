package Airbnb;

import java.util.*;

public class Snow
{
    public static void main(String[] args)
    {
        String[][] slide = new String[][] {{"start", "3", "A"}, {"A", "4", "B"}
                , {"A", "4", "C"}, {"B", "4", "END"},  {"C","5","END"}};

        String[][] point = new String[][] {{"A","5"},{"B","6"},{"C","3"},{"END","3"}};
        Snow s = new Snow();
        List<String> res = s.getPathWithMostPoint(slide, point);
        System.out.println(res);

    }

    class SliceDest {
        double cost;
        String dest;

        public SliceDest (double cost, String dest) {
            this.cost = cost;
            this.dest = dest;
        }

        public String toString() {
            return "dest:" + dest +", cost:" +cost;
        }
    }

    public List<String> getPathWithMostPoint (String[][] slide , String[][] pointArr) {
        Map<String, List<SliceDest>> slideDestMap = new HashMap<>();
        populateSliceDestMap (slide, slideDestMap);

        Map<String, Double> destPointMap = new HashMap<>();
        populateDestPointMap (pointArr, destPointMap);

        //
        PriorityQueue<SliceDest> queue = new PriorityQueue<>((a,b) -> {
            return Double.compare(b.cost , a.cost);
        });
        Map<String, String> routeMap = new HashMap<>();
        queue.add(new SliceDest(0, "start"));


        while (!queue.isEmpty()) {
            SliceDest curr = queue.poll();
            if (curr.dest.equals("END"))
                break;

            List<SliceDest> neighbors = slideDestMap.get(curr.dest);
            if (neighbors != null) {
                for (SliceDest sd : neighbors) {
                    double point = destPointMap.get(sd.dest);
                    routeMap.put(sd.dest, curr.dest);
                    queue.add(new SliceDest(point - sd.cost, sd.dest));
                }
            }
        }

        if (!routeMap.containsKey("END"))
            return new ArrayList<>();

        List<String> res = new ArrayList<>();
        res.add("END");
        String next = "END";
        while(next != null) {
            next = routeMap.get(next);
            if (next != null)
                res.add(next);
        }

        Collections.reverse(res);
        return res;


    }

    public void populateDestPointMap (String[][] pointArr, Map<String, Double> destPoint) {
        for (int i=0; i<pointArr.length; i++) {
            String dest = pointArr[i][0];
            double point = Double.valueOf(pointArr[i][1]);
            destPoint.put(dest, point);
        }
    }

    public void populateSliceDestMap (String[][] slide, Map<String, List<SliceDest>> slideDestMap) {
        for (int i=0; i<slide.length; i++) {
            String start = slide[i][0];
            double cost = Double.valueOf(slide[i][1]);
            String dest = slide[i][2];
            slideDestMap.putIfAbsent(start, new ArrayList<>());
            slideDestMap.get(start).add(new SliceDest(cost, dest));
        }
    }

}
