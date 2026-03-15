import { useState } from 'react'
import useSimulation from "../hooks/useSimulation.js";
import InputPanel from "../components/common/InputPanel.jsx";
import StepControls from "../components/common/StepControls.jsx";
import StackVisualizer from "../components/visualizers/StackVisualizer";
import QueueVisualizer from "../components/visualizers/QueueVisualizer.jsx";
import ArrayVisualizer from "../components/visualizers/ArrayVisualizer.jsx";
import LinkedListVisualizer from "../components/visualizers/LinkedListVisualizer.jsx";
import styles from "./App.module.css";

const VISUALIZERS = {
    stack: StackVisualizer,
    queue: QueueVisualizer,
    array: ArrayVisualizer,
    linkedlist: LinkedListVisualizer,
};

const STRUCTURE_LABELS = {
    stack: "Stack",
    queue: "Queue",
    array: "Array",
    linkedlist: "Linked List",
};

export default function App() {
    const {
        steps, currentStep, snapshot, stepInfo,
        isPlaying, loading, error, speed,
        setSpeed, run, play, pause, next, prev, reset
    } = useSimulation();

    const [structure, setStructure] = useState("stack");

    const handleStructureChange = (s) => {
        if (s !== structure) {
            reset(s);
            setStructure(s);
        }
    };

    const handleRun = (operation, values, target) => {
        run(structure, operation, values, target);
    };

    const handleReset = () => {
        reset(structure);
    };

    const Visualizer = VISUALIZERS[structure];

    return (
        <div className={styles.app}>
            <h1 className={styles.title}>Algo<span>Mentor</span></h1>

            {error && <p className={styles.error}>{error}</p>}

            <div className={styles.visualizer}>
                <Visualizer snapshot={snapshot} stepInfo={stepInfo} />
            </div>

            <div className={styles.panel}>
                <p className={styles.panelTitle}>Configuration</p>

                <div className={styles.structureTabs}>
                    {Object.keys(VISUALIZERS).map(s => (
                        <button
                            key={s}
                            className={`${styles.tabBtn} ${structure === s ? styles.tabBtnActive : ""}`}
                            onClick={() => handleStructureChange(s)}
                            disabled={loading}
                        >
                            {STRUCTURE_LABELS[s]}
                        </button>
                    ))}
                </div>

                <InputPanel structure={structure} onRun={handleRun} loading={loading} />

                <button className={styles.resetBtn} onClick={handleReset} disabled={loading}>
                    Reset Structure
                </button>
            </div>

            <div className={styles.controls}>
                <StepControls
                    isPlaying={isPlaying}
                    onPlay={play}
                    onPause={pause}
                    onNext={next}
                    onPrev={prev}
                    onReset={reset}
                    speed={speed}
                    onSpeedChange={setSpeed}
                    currentStep={currentStep}
                    totalSteps={steps.length}
                />
            </div>
        </div>
    );
}
