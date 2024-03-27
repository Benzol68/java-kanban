package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.HistoryManager;
import com.sysoev.taskmanager.util.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    //    public static final int MAX_SIZE = 10;
    private Map<Integer, Node<Task>> history = new HashMap<>();
    private NewLinkedList<Task> historyList = new NewLinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        historyList.removeNode(history.get(task.getId()));

        Node<Task> node = historyList.linkLast(task);
        history.put(task.getId(), node);
    }

    @Override
    public List<Task> getHistory() {
        return historyList.getAll();
    }

    @Override
    public void remove(int id) {
        historyList.removeNode(history.remove(id));
    }


    static class NewLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;


        Node<T> linkLast(T e) {
            final Node<T> newNode = new Node<>(e, tail, null);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;
            return newNode;
        }

        List<T> getAll() {
            ArrayList<T> list = new ArrayList<>();
            Node<T> current = head;
            while (current != null) {
                list.add(current.data);
                current = current.next;
            }
            return list;
        }

        public void removeNode(Node<T> node) {
            if (node == null) {
                return;
            }
            final Node<T> prev = node.prev;
            final Node<T> next = node.next;
            if (prev == null && next == null) {
                head = null;
                tail = null;
            } else if (prev != null && next != null) {
                prev.next = next;
                next.prev = prev;
            } else if (next == null) {
                prev.next = null;
                tail = prev;
            } else {
                next.prev = null;
                head = next;
            }
        }
    }
}