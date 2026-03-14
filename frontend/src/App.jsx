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
    stack:      StackVisualizer,
    queue:      QueueVisualizer,
    array:      ArrayVisualizer,
    linkedlist: LinkedListVisualizer
};

export default function App() {
    const {
        steps, currentStep, snapshot, stepInfo,
        isPlaying, loading, error, speed,
        setSpeed, run, play, pause, next, prev, reset
    } = useSimulation();

    const [structure, setStructure] = useState("stack");

    const handleRun = (struct, operation, values, target) => {
        setStructure(struct);
        run(struct, operation, values, target);
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
            <InputPanel onRun={handleRun} loading={loading} />
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
