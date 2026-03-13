package engine.structures;

import engine.models.SimulationRequest;

import java.util.ArrayList;
import java.util.List;

public class StackDS extends BaseStructure {

    private List<Integer> stack = new ArrayList<>();

    @Override
    protected void execute(SimulationRequest request) {
        switch (request.operation.toLowerCase()) {
            case "push"    -> push(request.values);
            case "pop"     -> pop();
            case "peek"    -> peek();
            case "isempty" -> isEmpty();
            default        -> addError("Invalid operation: " + request.operation, stack);
        }
    }

    private void push(int[] values) {
        for (int value : values) {
            stack.add(value);
            addStep("PUSH", "Pushed " + value + " onto stack", stack);
        }
    }

    private void pop() {
        if (stack.isEmpty()) {
            addError("Cannot pop from empty stack", stack);
        } else {
            int removed = stack.remove(stack.size() - 1);
            addStep("POP", "Popped " + removed, stack);
        }
    }

    private void peek() {
        if (!stack.isEmpty()) {
            int top = stack.get(stack.size() - 1);
            addStep("PEEK", "Top element is " + top, stack);
        } else {
            addError("Cannot peek from empty stack", stack);
        }
    }

    private void isEmpty() {
        boolean empty = stack.isEmpty();
        addStep("ISEMPTY", empty ? "Stack is empty" : "Stack is not empty", stack);
    }
}