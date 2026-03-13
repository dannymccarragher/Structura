package engine.structures;

import engine.models.SimulationRequest;
import engine.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StackDS {

    private List<Integer> stack = new ArrayList<>();

    public List<Step> run(SimulationRequest request) {
        List<Step> steps = new ArrayList<>();
        String op = request.operation.toLowerCase();

        if (op.equals("push")) {
            push(request.values, steps);
        } else if (op.equals("pop")) {
            pop(steps);
        } else if (op.equals("peek")) {
            peek(steps);
        } else if (op.equals("isempty")) {
            isEmpty(steps);
        } else {
            steps.add(new Step(
                    "ERROR",
                    "Invalid operation: " + request.operation,
                    new ArrayList<>(stack)
            ));
        }

        return steps;
    }

    private void push(int[] values, List<Step> steps) {
        for (int value : values) {
            stack.add(value);
            steps.add(new Step(
                    "PUSH",
                    "Pushed " + value + " onto stack",
                    new ArrayList<>(stack)
            ));
        }
    }

    private void pop(List<Step> steps) {
        if (stack.isEmpty()) {
            steps.add(new Step(
                    "ERROR",
                    "Cannot pop from empty stack",
                    new ArrayList<>(stack)
            ));
        } else {
            int removed = stack.remove(stack.size() - 1);
            steps.add(new Step(
                    "POP",
                    "Popped " + removed,
                    new ArrayList<>(stack)
            ));
        }
    }

    private void peek(List<Step> steps) {
        if (!stack.isEmpty()) {
            int top = stack.get(stack.size() - 1);
            steps.add(new Step(
                    "PEEK",
                    "Top element is " + top,
                    new ArrayList<>(stack)
            ));
        } else {
            steps.add(new Step(
                    "ERROR",
                    "Cannot peek from empty stack",
                    new ArrayList<>(stack)
            ));
        }
    }

    private void isEmpty(List<Step> steps) {
        boolean empty = stack.isEmpty();
        steps.add(new Step(
                "ISEMPTY",
                empty ? "Stack is empty" : "Stack is not empty",
                new ArrayList<>(stack)
        ));
    }
}