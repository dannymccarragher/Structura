package engine;

import engine.models.SimulationRequest;
import engine.models.Step;
import engine.simulator.Simulator;

import java.util.*;

public class EngineServer {

    public static void main(String[] args) {

        SimulationRequest request = new SimulationRequest();

        request.structure = "Stack";
        request.operation = "push";
        request.values = new int[]{10, 20, 30};

        List<Step> steps = Simulator.run(request);

        for (Step step : steps) {
            System.out.println("Action: " + step.getType());
            System.out.println("Message: " + step.getMessage());
            System.out.println("State: " + step.getStateSnapshot());
            System.out.println("--------------------");
        }
    }

}
