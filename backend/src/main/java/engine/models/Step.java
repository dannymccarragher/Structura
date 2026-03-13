package engine.models;

public class Step {

    private String type;

    private String message;

    private Object stateSnapshot;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getStateSnapshot() {
        return stateSnapshot;
    }

    public void setStateSnapshot(Object stateSnapshot) {
        this.stateSnapshot = stateSnapshot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Step(String type, String message, Object stateSnapshot){
        this.message = message;
        this.type = type;
        this.stateSnapshot = stateSnapshot;
    }
}
