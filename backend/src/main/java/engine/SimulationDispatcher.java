package engine;

import engine.models.SimulationRequest;
import engine.models.Step;
import engine.structures.*;

import java.util.*;
import java.util.function.Supplier;

public class SimulationDispatcher {

    private final Map<String, Supplier<BaseStructure>> factories = Map.of(
            "stack",      StackDS::new,
            "queue",      QueueDS::new,
            "array",      ArrayDS::new,
            "linkedlist", LinkedListDS::new
    );

    private final Map<String, BaseStructure> instances = new HashMap<>();

    public SimulationDispatcher() {
        factories.forEach((k, v) -> instances.put(k, v.get()));
    }

    public List<Step> dispatch(SimulationRequest request) {
        BaseStructure ds = instances.get(request.structure.toLowerCase());
        if (ds == null)
            throw new IllegalArgumentException("Unknown structure: " + request.structure);
        return ds.run(request);
    }

    public void reset(String structure) {
        Supplier<BaseStructure> factory = factories.get(structure);
        if (factory == null)
            throw new IllegalArgumentException("Unknown structure: " + structure);
        instances.put(structure, factory.get());
    }
}