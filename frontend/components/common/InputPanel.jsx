import { useState, useEffect } from "react";
import styles from "./InputPanel.module.css";

const STRUCTURES = {
    stack:      ["push", "pop", "peek", "isempty"],
    queue:      ["enqueue", "dequeue", "peek", "isempty", "size", "contains"],
    array:      ["insert", "delete", "update", "search", "reverse"],
    linkedlist: ["insertathead", "insertattail", "insertatindex", "deleteathead", "deleteattail", "deleteatindex", "search", "reverse"]
};

const TARGET_OPS = ["search", "deleteatindex", "contains"];
const NO_INPUT_OPS = ["pop", "peek", "isempty", "size", "deleteathead", "deleteattail", "reverse"];

export default function InputPanel({ structure, onRun, loading }) {
    const [operation, setOperation] = useState(STRUCTURES[structure][0]);
    const [valuesInput, setValuesInput] = useState("");
    const [target, setTarget] = useState("");

    useEffect(() => {
        setOperation(STRUCTURES[structure][0]);
        setValuesInput("");
        setTarget("");
    }, [structure]);

    const handleSubmit = () => {
        const values = valuesInput
            .split(",")
            .map(v => parseInt(v.trim()))
            .filter(v => !isNaN(v));

        onRun(operation, values, parseInt(target) || 0);
    };

    const needsTarget = TARGET_OPS.includes(operation);
    const needsValues = !NO_INPUT_OPS.includes(operation);

    return (
        <div className={styles.panel}>
            <div className={styles.row}>
                <label className={styles.label}>Operation</label>
                <select
                    className={styles.select}
                    value={operation}
                    onChange={e => setOperation(e.target.value)}
                >
                    {STRUCTURES[structure].map(op => (
                        <option key={op} value={op}>{op}</option>
                    ))}
                </select>
            </div>

            {needsValues && !needsTarget && (
                <div className={styles.row}>
                    <label className={styles.label}>Values</label>
                    <input
                        className={styles.input}
                        type="text"
                        placeholder="e.g. 1, 2, 3"
                        value={valuesInput}
                        onChange={e => setValuesInput(e.target.value)}
                    />
                </div>
            )}

            {needsTarget && (
                <div className={styles.row}>
                    <label className={styles.label}>Target</label>
                    <input
                        className={styles.input}
                        type="number"
                        placeholder="e.g. 5"
                        value={target}
                        onChange={e => setTarget(e.target.value)}
                    />
                </div>
            )}

            <button className={styles.runBtn} onClick={handleSubmit} disabled={loading}>
                {loading ? "Running..." : "Run"}
            </button>
        </div>
    );
}