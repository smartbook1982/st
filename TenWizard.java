package Airbnb;

import java.util.*;

public class TenWizard
{

    public static void main(String[] args) {
        TenWizard sol = new TenWizard();
        int[][] ids = {{1, 5, 9}, {2, 3, 9}, {4}, {}, {}, {9}, {}, {}, {}, {}};
        Map<Integer, List<Integer>> wizardMap = new HashMap<>();
        List<List<Integer>> wizards = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            List<Integer> wizardList = new ArrayList<>();
            for (int j = 0; j < ids[i].length; j++) {
                wizardList.add(ids[i][j]);
            }
            wizardMap.put (i, wizardList);
        }
        List<Integer> res = sol.getShortestPath(wizardMap, 0, 9);
        for (int i : res) System.out.println(i);//0, 5, 9
    }

    public List<Integer> getShortestPath(Map<Integer, List<Integer>> wizardMap, int start, int end) {

        PriorityQueue<WizardCost> queue = new PriorityQueue<>((a, b) -> {
            return Double.compare(a.cost, b.cost);
        });
        queue.add (new WizardCost(start, 0));
        Map<Integer, Integer> shortPathMap = new HashMap<>();

        Set<Integer> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            WizardCost wizardCost = queue.poll();
            int wizardId = wizardCost.wizardId;
            double cost = wizardCost.cost;
            if (wizardId == end) break;
            visited.add(wizardId);
            List<Integer> knownWizard = wizardMap.get(wizardId);
            for (Integer knowWizardId : knownWizard) {
                if (visited.contains(knowWizardId)) continue;
                shortPathMap.put(knowWizardId, wizardId);
                double newCost = Math.pow(knowWizardId - wizardId, 2);
                queue.add (new WizardCost(knowWizardId, cost+newCost));
            }
        }

        List<Integer> res = new ArrayList<>();
        res.add(end);
        int t = end;
        while (shortPathMap.get(t) != null) {
            t = shortPathMap.get(t);
            res.add(t);
        }
        return res;
    }

    class WizardCost {
        int wizardId;
        double cost;

        public WizardCost (int wizardId, double cost) {
            this.wizardId = wizardId;
            this.cost = cost;
        }

        public String toString() {
            return "wizardId:" + wizardId + ", cost:" + cost;
        }
    }
}
