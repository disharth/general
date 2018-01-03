package interval;
interface Rng<T> extends Comparable<T>{
    int getStart();
    int getEnd();

}
public class IntervalST<Key extends Rng, Value> {
    Node root;

    private class Node{

        Key key;
        Value value;
        Node left , right;
        int max=0;

        public Node(Key key , Value value){
            this.key = key;
            this.value = value;
        }

    }

    public void put(Key key , Value value){
        root = put(root , key , value);

    }

    private Node put(Node currentNode , Key key , Value value){

        if (currentNode == null) {
            Node nNode = new Node(key, value);
            nNode.max = key.getEnd();
            return nNode;
        }

        int cmp = key.compareTo(currentNode.key);

        if (cmp < 0) {
            currentNode.left = put(currentNode.left, key, value);
            if (currentNode.left.max > currentNode.max)
                currentNode.max = currentNode.left.max;
        }
        if (cmp > 0) {
            currentNode.right = put(currentNode.right, key, value);
            if (currentNode.right.max > currentNode.max)
                currentNode.max = currentNode.right.max;
        }
        if (cmp == 0)
            currentNode.value = value;
        return currentNode;

    }

    public int get(Key key){
        return get(root, key);

    }

    private int get(Node currentNode , Key key){
        if (currentNode == null)
            return -1;
        int cmp = key.compareTo(currentNode.key);
        if (cmp <0) return get(currentNode.left , key);
        else if (cmp >0) return get(currentNode.right , key);
        else return currentNode.max;

    }
    public Key getIntersection(Key key){
        return getIntersection(root , key);
    }
    private Key getIntersection(Node currentNode , Key key){
        if (currentNode == null)
            return null;
        if (intersects(key , currentNode.key))
            return currentNode.key;
        else if (currentNode.left == null)
            return getIntersection(currentNode.right , key);
        else if (currentNode.left.max < key.getStart())
            return getIntersection(currentNode.right , key);
        else return getIntersection(currentNode.left , key);

    }
    private boolean intersects(Key keyFirst , Key keySecond) {
        if (keyFirst.getStart() >= keySecond.getStart() && keyFirst.getStart() < keySecond.getEnd())
            return true;
        return false;
    }

    private static class Range implements Rng<Range>{
        Integer start , end;
        public Range(int start , int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Range other) {
            return this.start.compareTo(other.start); // based on begin of the range.
        }

        @Override
        public String toString() {
            return "("+start+","+end+")";
        }

        @Override
        public int getStart() {
            return start;
        }

        @Override
        public int getEnd() {
            return end;
        }

    }

    public static void main(String[] args) {
        IntervalST<Range , Integer> intervalST = new IntervalST<>();
        Range range = new Range(17 , 19);
        intervalST.put(range , 1);
        range = new Range(21,24);
        intervalST.put(range , 2);
        range = new Range(5,8);
        intervalST.put(range , 3);
        range = new Range(15,18);
        intervalST.put(range , 4);
        range = new Range(4,8);
        intervalST.put(range , 5);
        range = new Range(7,10);
        intervalST.put(range , 6);
        range = new Range(16,22);
        intervalST.put(range , 7);

        System.out.println(intervalST.getIntersection(new Range(21,23)));



    }



}
