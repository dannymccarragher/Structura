package engine.models;

import java.util.Map;

public class SimulationStep {
    public int stepIndex;
    public String description;
    public int[] state;
    public Map<String, Object> meta;
}