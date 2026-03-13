package engine.structures;

import engine.models.SimulationRequest;
import engine.models.Step;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseStructure {

    protected List<Step> steps = new ArrayList<>();

    public List<Step> run(SimulationRequest request) {
        steps.clear();
        execute(request);
        return steps;
    }

    protected abstract void execute(SimulationRequest request);

    protected void addStep(String action, String description, List<Integer> state) {
        steps.add(new Step(action, description, new ArrayList<>(state)));
    }

    protected void addError(String message, List<Integer> state) {
        steps.add(new Step("ERROR", message, new ArrayList<>(state)));
    }
}