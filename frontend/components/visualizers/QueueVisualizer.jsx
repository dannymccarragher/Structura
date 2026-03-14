import styles from "./QueueVisualizer.module.css";

export default function QueueVisualizer({ snapshot, stepInfo }) {
    const queue = Array.isArray(snapshot) ? snapshot : [];
    const frontIndex = 0;
    const backIndex = queue.length - 1;

    return (
        <div className={styles.container}>
            {queue.length > 0 && (
                <div className={styles.arrows}>
                    <span>⬇ front (dequeue)</span>
                    <span>back (enqueue) ⬇</span>
                </div>
            )}

            <div className={styles.queue}>
                {queue.length === 0
                    ? <p className={styles.empty}>Queue is empty</p>
                    : queue.map((value, index) => (
                        <div
                            key={index}
                            className={`
                                ${styles.block}
                                ${index === frontIndex ? styles.front : ""}
                                ${stepInfo?.type === "ENQUEUE" && index === backIndex ? styles.highlight : ""}
                                ${stepInfo?.type === "DEQUEUE" && index === frontIndex ? styles.highlight : ""}
                            `}
                        >
                            {value}
                            {index === frontIndex && <span className={styles.label}>front</span>}
                            {index === backIndex && index !== frontIndex && <span className={styles.label}>back</span>}
                        </div>
                    ))
                }
            </div>

            {stepInfo && (
                <div className={styles.stepInfo}>
                    <span className={styles.type}>{stepInfo.type}</span>
                    <span>{stepInfo.message}</span>
                </div>
            )}
        </div>
    );
}