import java.util.Iterator;
/**
 * Linked list implementation of the MyList interface.
 * @author El Chu HC3294
 * @version 1.0 October 6, 2022
 */
public class MyLinkedList<E> implements MyList<E> {

    //we've created our own private class node at the bottom
    private Node head, tail;
    private int size;
    /**
     * Constructs an empty list.
     */
    //you don't even have to do this, because node defaults to this
    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }
    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }
    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index    index of the element to return
     * @param element  element to be stored at the specified position
     * @return  the element at the specified position in this list
     * @throws  IndexOutOfBoundsException - if the index is out of range
     *          (index < 0 || index >= size())
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head;
        //for p.next, it means we keep going this many times, until we get to
        //the variable
        for (int i = 0; i < index; i++, p = p.next);
        E oldElement = p.element;
        p.element = element;
        return oldElement;
    }
    /**
     * Returns the element at the specified position in this list.
     * @param index  index of the element to return
     * @return       the element at the specified position in this list
     * @throws       IndexOutOfBoundsException - if the index is out of range
     *               (index < 0 || index >= size())
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head;
        //p=p.next traverses through the linked blocks
        //runtime = 0(n)
        for (int i = 0; i < index; i++, p = p.next);
        return p.element;
    }
    /**
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        Node n = new Node(element);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }
    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }
    public Iterator<E> iterator() {
        return new ListItr();
    }
    private class ListItr implements Iterator<E> {
        private Node current;
        ListItr() {
            current = head;
        }
        //always have to call hasNext before we call next
        @Override
        public boolean hasNext() {
            return current != null;
        }
        //next gives you value of what you're currently at and goes to next element
        @Override
        public E next() {
            E element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    //private because we don't want people created nodes, people have to add
    //nodes instead now.
    private class Node {
        Node next;
        E element;
        public Node(E element) {
            this.element = element;
        }
    }
    /**
     * Returns a string representation of the list. The string will begin with
     * a '[' and end with a ']'. Inside the square brackets will be a comma-
     * separated list of values, such as [Brian, Susan, Jamie].
     * @return a string representation of the list
     */
    public String toString(){
        String temp = "";
        for (Node p = head; p != null; p=p.next){
            temp += p.element + ", ";
        }
        if (temp.length() == 0){
            return "[]";
        }
        else {
            return "[" + temp.substring(0, temp.length() - 2) + "]";
        }
    }
    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * @param index    index at which the specified element is to be inserted
     * @param element  element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index > size())
     * The exception message must be:
     * "Index: " + index + ", list size: " + size
     */
    public void add(int index, E element){
        if (index < 0 || index > size()){
            throw new IndexOutOfBoundsException (
                    "Index: " + index + ", list size: " + size);
        }
        Node newNode = new Node (element);
        Node oldNode = head;

        if (index ==0){
            //makes new node become head value
            newNode.next = oldNode;
            head = newNode;
        }
        else{
            for (int i = 0; i < index-1; i++){
                oldNode = oldNode.next;
            }
            newNode.next = oldNode.next;
            oldNode.next = newNode;
        }
        size++;

    }
    /**
     * Removes the element at the specified position in this list.
     * @param index  the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index >= size())
     * The exception message must be:
     * "Index: " + index + ", list size: " + size
     */
    public E remove(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException (
                    "Index: " + index + ", list size: " + size);
        }
        Node oldNode = head;
        Node afterNode = head;
        Node removedNode = head;

        for (int i =0; i< index; i++){
            removedNode = removedNode.next;
        }
        E exitedElement = removedNode.element;

        if (index ==0){
            head = oldNode.next;
        }
        else if(index == size){
            for (int i =0; i< index -1; i++ ){
                afterNode = afterNode.next;
            }
            tail = afterNode;
        }
        else {
            for (int i =0; i< index -1; i++ ){
                oldNode = oldNode.next;
            }

            for (int i = 0; i < index + 1; i++) {
                afterNode = afterNode.next;
            }

            oldNode.next = afterNode;
        }
        size--;
        return exitedElement;

    }
    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element. More
     * formally, returns the lowest index i such that Objects.equals(o, get(i)),
     * or -1 if there is no such index.
     * @param element element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    public int indexOf(E element){
        Node p = head;
        for (int i = 0; i < size; i++){
            if((p.element).equals(element)){
                return i;
            }
            else{
                p = p.next;
            }

        }
        return -1;
    }
    /**
     * Returns an array of indexes of each occurrence of the specified element
     * in this list, in ascending order. If the specified element is not found,
     * a non-null empty array (not null) is returned.
     * @param element element to search for
     * @return an array of each occurrence of the specified element in this
     * list
     */
    public int[] indexesOf(E element){
        Node oldNode = head;
        Node newNode = head;
        int counter = 0;
        int [] indexes;
        int [] emptyArray = {};

        if (size ==0){
            return emptyArray;
        }
        for(int i =0; i<size; i++){
            if ((oldNode.element).equals(element)){
                counter++;
            }
            oldNode = oldNode.next;
        }

        indexes = new int[counter];
        int number = 0;

        for (int i = 0; i < size; i++){
            if ((newNode.element).equals(element)){
                indexes[number] = i;
                number++;
            }
            newNode = newNode.next;

        }
        return indexes;
    }
    /**
     * Reverses the data in the list.
     * For MyArrayList, the data inside the underlying array is moved. For
     * MyLinkedList, the tail must become the head, and all the pointers are
     * reversed. Both implementations must run in Theta(n).
     */
    public void reverse(){
        if (head == null || head.next ==null) {
            return;
        }
        Node headd = head;
        Node p = head.next;

        while(p!=null){
            Node temp = p.next;
            p.next = headd;
            headd = p;
            p = temp;
        }
        head.next = null;
        head = headd;
    }
}
