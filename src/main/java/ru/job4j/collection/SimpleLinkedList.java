package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements SimpleLinked<E> {
    private int size;
    private int modCount;
    private Node<E> head;

    @Override
    public void add(E value) {
        Node<E> newNode = new Node<>(value, null);
        Node<E> last = head;
        if (head == null) {
            head = newNode;
        } else {
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> element = head;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            final int expectedModCount = modCount;
            SimpleLinkedList.Node<E> last = head;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return last != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = last.item;
                last = last.next;
                return value;
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }
}
