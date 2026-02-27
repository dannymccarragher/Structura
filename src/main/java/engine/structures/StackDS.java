package engine.structures;

import engine.models.*;
import java.util.*;

public class StackDS {

    private List<Integer> stack = new ArrayList<>();

    public List<Step> run(SimulationRequest request) {

        List<Step> steps = new ArrayList<>();

        if (request.operation.equalsIgnoreCase("push")) {

            for (int value : request.values) {
                stack.add(value);

                steps.add(new Step(
                        "PUSH",
                        "Pushed " + value + " onto stack",
                        new ArrayList<>(stack)
                ));
            }

        } else if (request.operation.equalsIgnoreCase("pop")) {

            if (stack.isEmpty()) {
                steps.add(new Step(
                        "ERROR",
                        "Cannot pop from empty stack",
                        new ArrayList<>(stack)
                ));
            } else {
                int removed = stack.removeLast();

                steps.add(new Step(
                        "POP",
                        "Popped " + removed,
                        new ArrayList<>(stack)
                ));
            }
        }

        return steps;
    }
}