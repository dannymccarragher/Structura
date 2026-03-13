package engine.structures;

import engine.models.SimulationRequest;

import java.util.ArrayList;
import java.util.List;

public class ArrayDS extends BaseStructure {

    private List<Integer> array = new ArrayList<>();

    @Override
    protected void execute(SimulationRequest request) {
        switch (request.operation.toLowerCase()) {
            case "insert"  -> insert(request.values);
            case "delete"  -> delete(request.values);
            case "update"  -> update(request.values);
            case "search"  -> search(request.target);
            case "reverse" -> reverse();
            default        -> addError("Invalid operation: " + request.operation, array);
        }
    }

    private void insert(int[] values) {
        for (int value : values) {
            array.add(value);
            addStep("INSERT", "Inserted " + value + " at index " + (array.size() - 1), array);
        }
    }

    private void delete(int[] values) {
        for (int value : values) {
            int index = array.indexOf(value);
            if (index == -1) {
                addError("Value " + value + " not found in array", array);
            } else {
                array.remove(index);
                addStep("DELETE", "Deleted " + value + " at index " + index, array);
            }
        }
    }

    private void update(int[] values) {
        if (values.length < 2) {
            addError("Update requires two values: [index, newValue]", array);
            return;
        }
        int index = values[0];
        int newValue = values[1];
        if (index < 0 || index >= array.size()) {
            addError("Index " + index + " out of bounds", array);
        } else {
            int old = array.set(index, newValue);
            addStep("UPDATE", "Updated index " + index + " from " + old + " to " + newValue, array);
        }
    }

    private void search(int target) {
        for (int i = 0; i < array.size(); i++) {
            addStep("SEARCH", "Checking index " + i + " — value is " + array.get(i), array);
            if (array.get(i) == target) {
                addStep("FOUND", "Found " + target + " at index " + i, array);
                return;
            }
        }
        addError("Value " + target + " not found in array", array);
    }

    private void reverse() {
        addStep("REVERSE", "Starting reverse", array);
        int left = 0, right = array.size() - 1;
        while (left < right) {
            int tmp = array.get(left);
            array.set(left, array.get(right));
            array.set(right, tmp);
            addStep("REVERSE", "Swapped index " + left + " and " + right, array);
            left++;
            right--;
        }
        addStep("REVERSE", "Array reversed", array);
    }
}