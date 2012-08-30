public class Node<Item> {
    public Node(Item item) {
        current = item;
    }

    public Item current;
    public Node<Item> next;

}
