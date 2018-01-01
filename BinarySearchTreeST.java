import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class BinarySearchTreeST<Key extends Comparable<Key>, Value> {

    Node root;

    private class Node{
        Key key;
        Value value;
        Node left , right;

        boolean isTeminator;

        public Node(Key key , Value value){
            this.key = key;
            this.value = value;
        }

        public boolean isTeminator() {
            return isTeminator;
        }

        public void setTeminator(boolean teminator) {
            isTeminator = teminator;
        }
    }

    public void put(Key key , Value value){

        root = put(root , key, value);

    }

    public Value get(Key key){
        return get(root , key);
    }

    private Value get(Node currentNode , Key key){
        if (currentNode == null)
            return null;
        int cmp = key.compareTo(currentNode.key);
        if (cmp < 0 )
            return get(currentNode.left , key);
        else if (cmp > 0)
            return get(currentNode.right , key);
        else return currentNode.value;

    }


    private Node put(Node currentNode , Key key , Value value){
        if (currentNode == null)
            currentNode = new Node(key , value);
        else {
            int cmp = key.compareTo(currentNode.key);
            if (cmp < 0)
                currentNode.left = put(currentNode.left, key, value);
            else if (cmp > 0)
                currentNode.right = put(currentNode.right, key, value);
            else if (cmp == 0)
                currentNode.value = value;
        }

        return currentNode;

    }

    public Queue<Node> levelOrder(){
        Queue<Node> inorder = new ArrayBlockingQueue<Node>(10);
        inorder.add(root);
        Node terminator = new Node(null , null);
        terminator.setTeminator(true);
        inorder.add(terminator);
        levelOrder(inorder);
        return inorder;
    }

    private void levelOrder( Queue<Node> queue){
        Node currentNode = queue.poll();
        if (currentNode == null)
            return;
        if (currentNode.isTeminator()){
            if (queue.size() == 0) // encountered last terminator.
                return;
            Node terminator = new Node(null , null);
            terminator.setTeminator(true);
            queue.add(terminator);
            System.out.println();

        }else {
            System.out.print(" " + currentNode.value);
            if (currentNode.left != null)
                queue.add(currentNode.left);
            if (currentNode.right != null)
                queue.add(currentNode.right);
        }
        levelOrder(queue);
    }

    public Queue<Value> inorder(){
        Queue<Value> inorder = new ArrayBlockingQueue<Value>(10);
        inOrder(root,inorder);
        return inorder;
    }

    private void inOrder(Node currentNode , Queue<Value> queue){
        if (currentNode == null)
            return;
        inOrder(currentNode.left , queue);
        queue.add(currentNode.value);
        inOrder(currentNode.right , queue);

    }

    public Queue<Value> postOrder(){
        Queue<Value> postorder = new ArrayBlockingQueue<Value>(10);
        postOrder(root,postorder);
        return postorder;
    }

    private void postOrder(Node currentNode , Queue<Value> queue){
        if (currentNode == null)
            return;
        postOrder(currentNode.right , queue);
        queue.add(currentNode.value);
        postOrder(currentNode.left , queue);

    }

    public Stack<Value> reverseInOrder(){
        Stack<Value> inorder = new Stack<Value>();
        reverseInOrder(root,inorder);
        return inorder;
    }

    private void reverseInOrder(Node currentNode , Stack<Value> queue){
        if (currentNode == null)
            return;
        reverseInOrder(currentNode.left , queue);
        queue.push(currentNode.value);
        reverseInOrder(currentNode.right , queue);

    }

    public Value max(){
        Value value = max(root);
        return value;
    }

    private Value max(Node currentNode){
        if (currentNode.right == null)
            return currentNode.value;
        else return max(currentNode.right); // finally ends when currentNde is null
        //return null; // orevious to null node.
    }

    public Value min(){
        return min(root);
    }

    private Value min(Node currentNode){
        if (currentNode.left == null)
            return currentNode.value;
        else return max(currentNode.left);
    }




    public static void main(String[] args) {
        BinarySearchTreeST st = new BinarySearchTreeST();
        System.out.println("Get "+st.get("H"));
        st.put("A" , 10);
        st.put("C" , 30);
        st.put("G" , 60);
        st.put("H" , 70);
        st.put("B" , 20);
        st.put("D" , 5);
        st.put("T" , 2);
        System.out.println("Get "+st.get("H"));
        System.out.println("Get "+st.get("D"));
        System.out.println("Get "+st.get("C"));

        Queue<Integer> values = st.inorder();
        for (Integer value : values){
            System.out.print(" "+value);
        }
        System.out.println();
        values = st.postOrder();
        for (Integer value : values){
            System.out.print(" "+value);
        }

        System.out.println("\n reverse Inroder");
        Stack<Integer> vs = st.reverseInOrder();
        int length = vs.size();
        for (int i = 0; i <length ; i++) {
            System.out.print(" "+vs.pop());
        }

        System.out.println("Max = "+st.max());
        System.out.println("Min = "+st.min());
        st.levelOrder();


    }

}
