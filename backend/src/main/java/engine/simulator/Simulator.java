package engine.simulator;

import engine.models.SimulationRequest;
import engine.models.Step;
import engine.structures.StackDS;

import java.util.List;

public class Simulator {

    public static List<Step> run(SimulationRequest req) {

        if ("Stack".equalsIgnoreCase(req.structure)) {
            return new StackDS().run(req);
        }

        throw new IllegalArgumentException("Unsupported structure: " + req.structure);
    }
}