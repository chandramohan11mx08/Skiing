import java.io.*;
import java.util.ArrayList;

public class Skiing {
    final static int maxColumns = 1000, maxRows = 1000;
    final static int[][] sampleInput = new int[][]{
            {4, 8, 7, 3},
            {2, 5, 9, 3},
            {6, 3, 2, 5},
            {4, 4, 1, 6}};
    static int[][] input = new int[maxColumns][maxRows];

    static ArrayList<Integer> overAllSteepestPath = new ArrayList<Integer>();

    public static void readLines() {
        File file = new File("/Users/chandramohanr/project/Skiing/src/map.txt");
        BufferedReader reader = null;
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String[] words = text.split(" ");
                int j = 0;
                for (String word : words) {
                    input[i][j] = Integer.parseInt(word);
                    j++;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static void print() {
        for (int i = 0; i < maxColumns; i++) {
            for (int j = 0; j < maxRows; j++) {
                System.out.print("\t" + input[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        readLines();

        //uncomment below line to test for sample input
//        SteepestPathFinder steepestPathFinder = new SteepestPathFinder(sampleInput, 4, 4, 1500);

        SteepestPathFinder steepestPathFinder = new SteepestPathFinder(input, maxColumns, maxRows, 1500);

        findOverAllSteepestDrop(steepestPathFinder);
    }

    private static void findOverAllSteepestDrop(SteepestPathFinder steepestPathFinder) {
        for (int i = 0; i < maxColumns; i++) {
            for (int j = 0; j < maxRows; j++) {
                ArrayList<Integer> steepestPath = new ArrayList<Integer>(steepestPathFinder.getSteepestPath(i, j));

                int overAllSteepestPathSize = overAllSteepestPath.size();
                int steepestPathSize = steepestPath.size();
                if (((overAllSteepestPathSize == 0 || overAllSteepestPathSize <= steepestPathSize)) && steepestPathSize > 0) {
                    if (overAllSteepestPathSize == steepestPathSize) {
                        int overAllSteepDepth = calculateSteepDepth(overAllSteepestPath);
                        int currentSteepDepth = calculateSteepDepth(steepestPath);
                        if (currentSteepDepth > overAllSteepDepth) {
                            setNewSteepestPath(steepestPath);
                        }
                    } else {
                        setNewSteepestPath(steepestPath);
                    }
                }
            }
        }
        System.out.print("Steepest path is " + overAllSteepestPath.toString() + " of length " + overAllSteepestPath.size()+" and depth "+calculateSteepDepth(overAllSteepestPath));
    }

    private static int calculateSteepDepth(ArrayList<Integer> steepestPath) {
        return steepestPath.get(0) - steepestPath.get(steepestPath.size() - 1);
    }

    private static void setNewSteepestPath(ArrayList<Integer> steepestPath) {
        overAllSteepestPath = new ArrayList<Integer>(steepestPath);
    }
}
