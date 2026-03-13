package engine;

import engine.models.SimulationRequest;
import engine.models.Step;
import engine.structures.*;

import java.util.List;

public class SimulationDispatcher {

    public static List<Step> dispatch(SimulationRequest request) {
        return switch (request.structure.toLowerCase()) {
            case "stack" -> new StackDS().run(request);
            case "queue" -> new QueueDS().run(request);
            case "array" -> new ArrayDS().run(request);
            case "linkedlist" -> new LinkedListDS().run(request);
            default -> throw new IllegalArgumentException("Unknown structure: " + request.structure);
        };
    }
}