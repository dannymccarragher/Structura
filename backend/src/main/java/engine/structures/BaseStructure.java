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

    protected void addStep(String type, String message, Object stateSnapshot) {
        if (stateSnapshot instanceof List<?> list) {
            steps.add(new Step(type, message, new ArrayList<>(list)));
        } else {
            steps.add(new Step(type, message, stateSnapshot));
        }
    }

    protected void addError(String message, Object stateSnapshot) {
        steps.add(new Step("ERROR", message, stateSnapshot));
    }
}