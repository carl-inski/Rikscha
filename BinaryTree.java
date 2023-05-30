public class BinaryTree<T extends Comparable<T>> {
    private T data;
    private BinaryTree<T> leftChild;
    private BinaryTree<T> rightChild;

    public BinaryTree(T data) {
        this.data = data;
        
    }

    public BinaryTree(T data, BinaryTree<T> leftChild, BinaryTree<T> rightChild) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public T getData() {
        return data;
    }

    public BinaryTree<T> getLeftChild() {
        return leftChild;
    }

    public BinaryTree<T> getRightChild() {
        return rightChild;
    }

    public void setLeftChild(BinaryTree<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(BinaryTree<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void insert(T data) {
        if (this.data == null) {
            this.data = data;
        } else {
            if (data.compareTo(this.data) <= 0) {
                if (leftChild == null) {
                    leftChild = new BinaryTree<>(data);
                } else {
                    leftChild.insert(data);
                }
            } else {
                if (rightChild == null) {
                    rightChild = new BinaryTree<>(data);
                } else {
                    rightChild.insert(data);
                }
            }
        }
    }


    public BinaryTree<T> findNode(String playerName) {
        if (data == null) {
            return null;
        } else if (data instanceof Spieler && ((Spieler) data).getName().equals(playerName)) {
            return this;
        } else {
            BinaryTree<T> foundNode = null;
            if (leftChild != null) {
                foundNode = leftChild.findNode(playerName);
            }
            if (foundNode == null && rightChild != null) {
                foundNode = rightChild.findNode(playerName);
            }
            return foundNode;
        }
    }
    
    
    
    
}
