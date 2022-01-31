package gfg.AVL;

import java.util.Scanner;

public class AVLTree {
    static boolean rebalanceFlag = false;
    static boolean isRebalanceDone = false;
    static String insertOrder = "";
    static boolean increamentHeightFlag = false;

    public static void main(String[] args) {
        System.out.println("Enter number of elements to create BST");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Enter elements in one line separated by spaces and hit enter to insert");
        AVLNode root = new AVLNode(sc.nextInt());
        while (--n > 0) {
            root = insert(root, sc.nextInt());
            insertOrder = "";
            increamentHeightFlag = false;
            isRebalanceDone = false;
            rebalanceFlag = false;
        }
        System.out.println(" BST created and root is having data " + root.data);
        inOrderTraverse(root);

    }

    private static void inOrderTraverse(AVLNode root) {
        if (root == null)
            return;
        else {
            inOrderTraverse(root.left);
            System.out.println(" " + root.data + "(" + (root.lHeight - root.rHeight) + ")");
            inOrderTraverse(root.right);
        }
    }

    private static AVLNode insert(AVLNode root, int key) {
        if (root == null) {
            return new AVLNode(key);
        } else {
            if (key > root.data) {
                if (root.right == null && root.left == null)
                    increamentHeightFlag = true;
                else if (root.right == null)
                    root.rHeight++;
                insertOrder = insertOrder + "R";
                root.right = insert(root.right, key);
                if (increamentHeightFlag)
                    root.rHeight++;
            } else {
                if (root.right == null && root.left == null)
                    increamentHeightFlag = true;
                else if (root.left == null)
                    root.lHeight++;
                insertOrder = insertOrder + "L";
                root.left = insert(root.left, key);
                if (increamentHeightFlag)
                    root.lHeight++;
            }
            System.out.println("Checking rebalance at " + root.data + " after inserting " + key);
            if (!isRebalanceDone)
                checkRebalance(root);
        }
        return root;
    }

    private static void checkRebalance(AVLNode root) {
        int diff = root.lHeight - root.rHeight;
        if (rebalanceFlag) {
            System.out.println("Rebalance flag is on, so going to rebalance with head as " + root.data);
            rebalance(root);
            System.out.println("Rebalance completed with head " + root.data + " , so setting isRebalanceDone to true");
            isRebalanceDone = true;
            return;
        }
        if (Math.abs(diff) > 1) {
            rebalanceFlag = true;
            System.out.println("Setting rebalance flag true at node " + root.data);
        }
    }

    private static void rebalance(AVLNode root) {
        AVLNode head = root;
        int len = insertOrder.length();
        System.out.println("In Rebalance with insertOrder " + insertOrder + " and head " + root.data);
        if (len == 2) {
            System.out.println("len is 2, Not yet implemented");
        } else if (len == 3) {
            System.out.println("len is 3, Not yet implemented");
        } else {
            String disbalanceType = insertOrder.substring(1, 3);
            System.out.println("Disbalance type is : " + disbalanceType);
            char headToParent = insertOrder.charAt(0);
            char parentToMid = insertOrder.charAt(1);
            char midtoChild = insertOrder.charAt(2);
            AVLNode parent = ('R' == headToParent) ? head.right : head.left;
            AVLNode mid = ('R' == parentToMid) ? parent.right : parent.left;
            AVLNode child = ('R' == midtoChild) ? mid.right : mid.left;
            switch (disbalanceType) {
                case "RR":
                    performLeftRotation(head, parent, mid, headToParent);
                    break;
                case "LL":
                    performRightRotation(head, parent, mid, headToParent);
                    break;
                case "RL":
                    performRightRotation(parent, mid, child, parentToMid);
                    performLeftRotation(head, parent, child, headToParent);
                    break;
                case "LR":
                    performLeftRotation(parent, mid, child, parentToMid);
                    performRightRotation(head, parent, child, headToParent);
                    break;
            }
            System.out.println("Rotations completed");
        }
    }

    private static void performRightRotation(AVLNode head, AVLNode parent, AVLNode mid, char headToParent) {
        AVLNode temp;
        if ('R' == headToParent)
            head.right = mid;
        else
            head.left = mid;
        temp = mid.right;
        mid.right = parent;
        parent.left = temp;
    }

    private static void performLeftRotation(AVLNode head, AVLNode parent, AVLNode mid, char headToParent) {
        AVLNode temp;
        if ('R' == headToParent)
            head.right = mid;
        else
            head.left = mid;
        temp = mid.left;
        mid.left = parent;
        parent.right = temp;
    }
}

class AVLNode {
    int data;
    int lHeight;
    int rHeight;
    AVLNode left;
    AVLNode right;

    public AVLNode(int data, int lHeight, int rHeight, AVLNode left, AVLNode right) {
        this.data = data;
        this.lHeight = lHeight;
        this.rHeight = rHeight;
        this.left = left;
        this.right = right;
    }

    public AVLNode(int data) {
        this.data = data;
        this.lHeight = 0;
        this.rHeight = 0;
        this.left = null;
        this.right = null;
    }
}
