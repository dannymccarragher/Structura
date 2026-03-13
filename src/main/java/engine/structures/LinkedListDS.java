package engine.structures;

import engine.models.SimulationRequest;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDS extends BaseStructure {

    private static class Node {
        int value;
        Node next;
        Node(int value) { this.value = value; }
    }

    private Node head;

    @Override
    protected void execute(SimulationRequest request) {
        switch (request.operation.toLowerCase()) {
            case "insertathead"  -> insertAtHead(request.values);
            case "insertattail"  -> insertAtTail(request.values);
            case "insertatindex" -> insertAtIndex(request.values);
            case "deleteathead"  -> deleteAtHead();
            case "deleteattail"  -> deleteAtTail();
            case "deleteatindex" -> deleteAtIndex(request.target);
            case "search"        -> search(request.target);
            case "reverse"       -> reverse();
            default              -> addError("Invalid operation: " + request.operation, toList());
        }
    }

    private void insertAtHead(int[] values) {
        for (int value : values) {
            Node node = new Node(value);
            node.next = head;
            head = node;
            addStep("INSERT_HEAD", "Inserted " + value + " at head", toList());
        }
    }

    private void insertAtTail(int[] values) {
        for (int value : values) {
            Node node = new Node(value);
            if (head == null) {
                head = node;
            } else {
                Node curr = head;
                while (curr.next != null) curr = curr.next;
                curr.next = node;
            }
            addStep("INSERT_TAIL", "Inserted " + value + " at tail", toList());
        }
    }

    private void insertAtIndex(int[] values) {
        if (values.length < 2) {
            addError("insertAtIndex requires [index, value]", toList());
            return;
        }
        int index = values[0];
        int value = values[1];

        if (index == 0) {
            insertAtHead(new int[]{ value });
            return;
        }

        Node curr = head;
        for (int i = 0; i < index - 1; i++) {
            if (curr == null) {
                addError("Index " + index + " out of bounds", toList());
                return;
            }
            addStep("TRAVERSE", "Traversing to index " + i + " — value is " + curr.value, toList());
            curr = curr.next;
        }

        Node node = new Node(value);
        node.next = curr.next;
        curr.next = node;
        addStep("INSERT_INDEX", "Inserted " + value + " at index " + index, toList());
    }

    private void deleteAtHead() {
        if (head == null) {
            addError("Cannot delete from empty list", toList());
        } else {
            int removed = head.value;
            head = head.next;
            addStep("DELETE_HEAD", "Deleted " + removed + " from head", toList());
        }
    }

    private void deleteAtTail() {
        if (head == null) {
            addError("Cannot delete from empty list", toList());
        } else if (head.next == null) {
            int removed = head.value;
            head = null;
            addStep("DELETE_TAIL", "Deleted " + removed + " from tail", toList());
        } else {
            Node curr = head;
            while (curr.next.next != null) curr = curr.next;
            int removed = curr.next.value;
            curr.next = null;
            addStep("DELETE_TAIL", "Deleted " + removed + " from tail", toList());
        }
    }

    private void deleteAtIndex(int index) {
        if (head == null) {
            addError("Cannot delete from empty list", toList());
            return;
        }
        if (index == 0) {
            deleteAtHead();
            return;
        }

        Node curr = head;
        for (int i = 0; i < index - 1; i++) {
            if (curr.next == null) {
                addError("Index " + index + " out of bounds", toList());
                return;
            }
            addStep("TRAVERSE", "Traversing to index " + i + " — value is " + curr.value, toList());
            curr = curr.next;
        }

        int removed = curr.next.value;
        curr.next = curr.next.next;
        addStep("DELETE_INDEX", "Deleted " + removed + " at index " + index, toList());
    }

    private void search(int target) {
        Node curr = head;
        int index = 0;
        while (curr != null) {
            addStep("SEARCH", "Checking index " + index + " — value is " + curr.value, toList());
            if (curr.value == target) {
                addStep("FOUND", "Found " + target + " at index " + index, toList());
                return;
            }
            curr = curr.next;
            index++;
        }
        addError("Value " + target + " not found", toList());
    }

    private void reverse() {
        addStep("REVERSE", "Starting reverse", toList());
        Node prev = null
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            addStep("REVERSE", "Reversed pointer at node " + prev.value, toList());
        }
        head = prev;
        addStep("REVERSE", "List reversed", toList());
    }

    private List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        Node curr = head;
        while (curr != null) {
            list.add(curr.value);
            curr = curr.next;
        }
        return list;
    }
}