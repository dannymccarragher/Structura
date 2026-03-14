import { useState } from "react";

const STRUCTURES = {
    stack:      ["push", "pop", "peek", "isempty"],
    queue:      ["enqueue", "dequeue", "peek", "isempty", "size", "contains"],
    array:      ["insert", "delete", "update", "search", "reverse"],
    linkedlist: ["insertathead", "insertattail", "insertatindex", "deleteathead", "deleteattail", "deleteatindex", "search", "reverse"]
};

// operations that need a target value instead of array values
const TARGET_OPS = ["search", "deleteatindex", "contains"];

// operations that need no input at all
const NO_INPUT_OPS = ["pop", "peek", "isempty", "size", "deleteathead", "deleteattail", "reverse"];

export default function InputPanel({ onRun, loading }) {
    const [structure, setStructure] = useState("stack");
    const [operation, setOperation] = useState("push");
    const [valuesInput, setValuesInput] = useState("");
    const [target, setTarget] = useState("");

    const handleStructureChange = (e) => {
        const s = e.target.value;
        setStructure(s);
        setOperation(STRUCTURES[s][0]);
        setValuesInput("");
        setTarget("");
    };

    const handleSubmit = () => {
        const values = valuesInput
            .split(",")
            .map(v => parseInt(v.trim()))
            .filter(v => !isNaN(v));

        onRun(structure, operation, values, parseInt(target) || 0);
    };

    const needsTarget = TARGET_OPS.includes(operation);
    const needsValues = !NO_INPUT_OPS.includes(operation);

    return (
        <div className="input-panel">
            <div className="input-panel__row">
                <label>Structure</label>
                <select value={structure} onChange={handleStructureChange}>
                    {Object.keys(STRUCTURES).map(s => (
                        <option key={s} value={s}>{s}</option>
                    ))}
                </select>
            </div>

            <div className="input-panel__row">
                <label>Operation</label>
                <select value={operation} onChange={e => setOperation(e.target.value)}>
                    {STRUCTURES[structure].map(op => (
                        <option key={op} value={op}>{op}</option>
                    ))}
                </select>
            </div>

            {needsValues && !needsTarget && (
                <div className="input-panel__row">
                    <label>Values</label>
                    <input
                        type="text"
                        placeholder="e.g. 1, 2, 3"
                        value={valuesInput}
                        onChange={e => setValuesInput(e.target.value)}
                    />
                </div>
            )}

            {needsTarget && (
                <div className="input-panel__row">
                    <label>Target</label>
                    <input
                        type="number"
                        placeholder="e.g. 5"
                        value={target}
                        onChange={e => setTarget(e.target.value)}
                    />
                </div>
            )}

            <button onClick={handleSubmit} disabled={loading}>
                {loading ? "Running..." : "Run"}
            </button>
        </div>
    );
}