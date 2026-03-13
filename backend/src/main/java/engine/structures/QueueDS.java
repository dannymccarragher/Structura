package engine.structures;

import engine.models.SimulationRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueDS extends BaseStructure {

    private Queue<Integer> queue = new LinkedList<>();

    @Override
    protected void execute(SimulationRequest request) {
        switch (request.operation.toLowerCase()) {
            case "enqueue"  -> enqueue(request.values);
            case "dequeue"  -> dequeue();
            case "peek"     -> peek();
            case "isempty"  -> isEmpty();
            case "size"     -> size();
            case "contains" -> contains(request.values);
            default         -> addError("Invalid operation: " + request.operation, toList());
        }
    }

    private void enqueue(int[] values) {
        for (int value : values) {
            queue.offer(value);
            addStep("ENQUEUE", "Enqueued " + value + " to back of queue", toList());
        }
    }

    private void dequeue() {
        if (queue.isEmpty()) {
            addError("Cannot dequeue from empty queue", toList());
        } else {
            int removed = queue.poll();
            addStep("DEQUEUE", "Dequeued " + removed + " from front of queue", toList());
        }
    }

    private void peek() {
        if (!queue.isEmpty()) {
            int front = queue.peek();
            addStep("PEEK", "Front element is " + front, toList());
        } else {
            addError("Cannot peek from empty queue", toList());
        }
    }

    private void isEmpty() {
        boolean empty = queue.isEmpty();
        addStep("ISEMPTY", empty ? "Queue is empty" : "Queue is not empty", toList());
    }

    private void size() {
        addStep("SIZE", "Queue size is " + queue.size(), toList());
    }

    private void contains(int[] values) {
        for (int value : values) {
            boolean found = queue.contains(value);
            addStep("CONTAINS", found ? value + " is in the queue" : value + " is not in the queue", toList());
        }
    }

    private List<Integer> toList() {
        return new ArrayList<>(queue);
    }
}