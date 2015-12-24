import java.util.ArrayList;

public class SteepestPathFinder {

    private int noOfColumns, noOfRows;
    private int maxValue;
    private int[][] input;
    private ArrayList<Integer> steepestPath = new ArrayList<Integer>();

    public SteepestPathFinder(int[][] inputArray, int maxColumns, int maxRows, int maxValue){
        this.input = inputArray;
        this.noOfColumns = maxColumns;
        this.noOfRows = maxRows;
        this.maxValue = maxValue;
    }

    public ArrayList<Integer> getSteepestPath(int fromX, int fromY) {
        steepestPath = new ArrayList<Integer>();
        traverseFromPoint(new Point(fromX, fromY), maxValue, new ArrayList<Integer>());
        return steepestPath;
    }

    private void traverseFromPoint(Point point, int srcValue, ArrayList<Integer> path) {
        int currentValue = input[point.srcX][point.srcY];
        int srcX = point.srcX;
        int srcY = point.srcY;

        if (path == null) {
            path = new ArrayList<Integer>();
        }

        if (!isAnySidePossible(srcX, srcY, currentValue)) {
            path.add(input[srcX][srcY]);
            if (steepestPath == null || steepestPath.size() == 0 || steepestPath.size() <= path.size()) {
                if(steepestPath.size() == path.size()){
                    int overAllSteepDepth = calculateSteepDepth(steepestPath);
                    int currentSteepDepth = calculateSteepDepth(path);
                    if (currentSteepDepth > overAllSteepDepth) {
                        steepestPath = new ArrayList<Integer>(path);
                    }
                }else{
                    steepestPath = new ArrayList<Integer>(path);
                }
            }
            System.out.println("Reached end, path is " + path.toString());
            path.remove(path.size() - 1);
            return;
        }
        path.add(input[srcX][srcY]);
        srcValue = input[srcX][srcY];

//        move up
        int prevX = srcX - 1;
        if(isNextPointPossible(prevX, srcY, srcValue) && (srcX != (noOfColumns -1))){
            traverseFromPoint(new Point(prevX, srcY), srcValue,path);
        }

        //move right
        int nextY = srcY + 1;
        if (isNextPointPossible(srcX, nextY, srcValue)) {
            traverseFromPoint(new Point(srcX, nextY), srcValue, path);
        }

        //move left
        int prevY = srcY - 1;
        if (isNextPointPossible(srcX, prevY, srcValue)) {
            traverseFromPoint(new Point(srcX, prevY), srcValue, path);
        }

        //move down
        int nextX = srcX + 1;
        if (isNextPointPossible(nextX, srcY, srcValue)) {
            traverseFromPoint(new Point(nextX, srcY), srcValue, path);
        }
        path.remove(path.size() - 1);
    }

    private boolean isNextPointPossible(int nextX, int nextY, int srcValue) {
        if ((nextX <= noOfColumns - 1 && nextX >= 0) && (nextY <= noOfRows - 1 && nextY >= 0)) {
            return srcValue > input[nextX][nextY];
        }
        return false;
    }

    private boolean isAnySidePossible(int srcX, int srcY, int srcValue){
        boolean isLeftPossible = isNextPointPossible(srcX, srcY - 1, srcValue);
        boolean isRightPossible = isNextPointPossible(srcX, srcY + 1, srcValue);
        boolean isUpPossible = isNextPointPossible(srcX - 1, srcY, srcValue);
        boolean isDownPossible = isNextPointPossible(srcX + 1, srcY, srcValue);
        return isLeftPossible || isRightPossible || isUpPossible || isDownPossible;
    }

    private static int calculateSteepDepth(ArrayList<Integer> steepestPath) {
        return steepestPath.get(0) - steepestPath.get(steepestPath.size() - 1);
    }


    class Point {
        public int srcX;
        public int srcY;

        public Point(int srcX, int srcY) {
            this.srcX = srcX;
            this.srcY = srcY;
        }
    }
}
