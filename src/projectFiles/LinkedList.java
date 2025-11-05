package projectFiles;

public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> current;

    public LinkedList() {
        head = current = null;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean last() {
        return current != null && current.next == null;
    }

    @Override
    public void findFirst() {
        current = head;
    }

    @Override
    public void findNext() {
        if (current != null)
            current = current.next;
    }

    @Override
    public T retrieve() {
        if (current != null)
            return current.data;
        return null;
    }

    @Override
    public void update(T e) {
        if (current != null)
            current.data = e;
    }

    @Override
    public void insert(T e) {
        Node<T> newNode = new Node<T>(e);
        if (head == null) {
            head = newNode;
            current = head;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode;
        }
    }

    @Override
    public void remove() {
        if (head == null || current == null)
            return;

        if (current == head) {
            head = head.next;
            current = head;
        } else {
            Node<T> prev = head;
            while (prev.next != current && prev.next != null)
                prev = prev.next;

            if (prev.next == current) {
                prev.next = current.next;

                if (current.next != null)
                    current = current.next;
                else 
                    current = head;
            }
        }
    }

    public Node<T> getHead() {
        return head;
    }
}