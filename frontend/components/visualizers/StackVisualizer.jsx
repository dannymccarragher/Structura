import styles from "./StackVisualizer.module.css";

export default function StackVisualizer({ snapshot, stepInfo }) {
    const stack = Array.isArray(snapshot) ? snapshot : [];
    const topIndex = stack.length - 1;

    return (
        <div className={styles.container}>
            <div className={styles.stack}>
                {stack.length === 0
                    ? <p className={styles.empty}>Stack is empty</p>
                    : stack.map((value, index) => (
                        <div
                            key={index}
                            className={`
                                ${styles.block}
                                ${index === topIndex ? styles.top : ""}
                                ${stepInfo?.type === "PUSH" && index === topIndex ? styles.highlight : ""}
                            `}
                        >
                            {value}
                            {index === topIndex && (
                                <span className={styles.label}>← top</span>
                            )}
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