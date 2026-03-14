export default function StepControls({
    isPlaying, onPlay, onPause, onNext, onPrev, onReset,
    speed, onSpeedChange, currentStep, totalSteps
}) {
    return (
        <div>
            <div>
                <button onClick={onReset}>⏮</button>
                <button onClick={onPrev} disabled={currentStep === 0}>⬅</button>
                {isPlaying
                    ? <button onClick={onPause}>⏸</button>
                    : <button onClick={onPlay} disabled={totalSteps === 0}>▶</button>
                }
                <button onClick={onNext} disabled={currentStep === totalSteps - 1}>➡</button>
            </div>

            <div>
                <label>Speed</label>
                <input
                    type="range"
                    min={200}
                    max={2000}
                    step={100}
                    value={speed}
                    onChange={e => onSpeedChange(Number(e.target.value))}
                />
                <span>{speed}ms</span>
            </div>

            <div>
                Step {totalSteps === 0 ? 0 : currentStep + 1} / {totalSteps}
            </div>
        </div>
    );
}