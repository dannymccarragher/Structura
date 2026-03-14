import { useState, useRef, useCallback } from "react";
import { simulate } from "../services/api";

export default function useSimulation() {
    const [steps, setSteps] = useState([]);
    const [currentStep, setCurrentStep] = useState(0);
    const [isPlaying, setIsPlaying] = useState(false);
    const [speed, setSpeed] = useState(1000);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const intervalRef = useRef(null);

    const run = useCallback(async (structure, operation, values, target) => {
        try {
            setLoading(true);
            setError(null);
            stopPlayback();

            const data = await simulate(structure, operation, values, target);
            setSteps(data.steps);
            setCurrentStep(0);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }, []);

    const stopPlayback = () => {
        clearInterval(intervalRef.current);
        setIsPlaying(false);
    };

    const play = useCallback(() => {
        if (steps.length === 0) return;
        setIsPlaying(true);
        intervalRef.current = setInterval(() => {
            setCurrentStep(prev => {
                if (prev >= steps.length - 1) {
                    stopPlayback();
                    return prev;
                }
                return prev + 1;
            });
        }, speed);
    }, [steps, speed]);

    const pause = useCallback(() => stopPlayback(), []);

    const next = useCallback(() => {
        setCurrentStep(prev => Math.min(prev + 1, steps.length - 1));
    }, [steps]);

    const prev = useCallback(() => {
        setCurrentStep(prev => Math.max(prev - 1, 0));
    }, []);

    const reset = useCallback(() => {
        stopPlayback();
        setCurrentStep(0);
    }, []);

    return {
        steps,
        currentStep,
        snapshot: steps[currentStep]?.stateSnapshot ?? [],
        stepInfo: steps[currentStep] ?? null,
        isPlaying,
        loading,
        error,
        speed,
        setSpeed,
        run,
        play,
        pause,
        next,
        prev,
        reset
    };
}