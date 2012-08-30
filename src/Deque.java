import java.util.Iterator;

/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-29th 2012
 * This program is for double ended Queues
 * ---------------------------------------------------------*/
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;

    private int count;

    /*---------------------------------------------------------
     * 
     * ---------------------------------------------------------*/
    public Deque() {
        first = new Node<Item>(null);
        last = new Node<Item>(null);
        first.next = last;
        count++;
    }

    public boolean isEmpty() {
        return first.current == null;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (isEmpty()) {
            first = new Node<Item>(item);
            first.next = null;
        } else {
            Node newFirst = new Node<Item>(item);
            newFirst.next = first;
            first = newFirst;
            if (first.next == null) {
                last = first;

            }

        }
        count++;
    }

    public void addLast(Item item) {
        count++;
        if (isEmpty()) {
            first = new Node<Item>(item);
            first.next = null;
        } else {
            if (last.current == null) {
                last = new Node<Item>(item);
                last.next = null;
                first.next = last;
            } else {

                Node oldlast = last;
                last = new Node<Item>(item);

                last.next = null;
                oldlast.next = last;

            }

        }

    }

    public Item removeFirst() {
        Node temp = first;
        first = temp.next;
        return (Item) temp.current;

    }

    public Item removeLast() {
        return TravereseLast(first, first.next);

    }

    private Item TravereseLast(Node n1, Node n2) {
        if (n2.next == null) {
            n1.next = null;
            return (Item) n1.current;
        } else {
            TravereseLast(n2, n2.next);
        }
        return null;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private Node curr = first;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return curr != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            Item item = (Item) curr.current;
            curr = curr.next;
            return item;
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException(
                    "This operation is not supported");
        }
    }

    public static void main(String[] args) {
        Deque queue = new Deque<String>();
        for (String x : args) {
            queue.addLast(x);
        }

        queue.removeFirst();
        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeFirst();
        queue.removeFirst();

        Iterator i = queue.iterator();
        do {
            Object s = i.next();
            StdOut.println(s);
        } while (i.hasNext());
    }
}
