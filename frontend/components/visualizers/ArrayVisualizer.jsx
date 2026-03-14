import styles from "./ArrayVisualizer.module.css";

export default function ArrayVisualizer({ snapshot, stepInfo }) {
    const array = Array.isArray(snapshot) ? snapshot : [];

    const getCellClass = (index) => {
        if (!stepInfo) return styles.cell;

        switch (stepInfo.type) {
            case "SEARCH":
                return `${styles.cell} ${styles.highlight}`;
            case "FOUND":
                return `${styles.cell} ${styles.found}`;
            case "INSERT":
                return index === array.length - 1
                    ? `${styles.cell} ${styles.highlight}`
                    : styles.cell;
            case "DELETE":
            case "UPDATE":
            case "REVERSE":
                return `${styles.cell} ${styles.highlight}`;
            case "ERROR":
                return `${styles.cell} ${styles.error}`;
            default:
                return styles.cell;
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.array}>
                {array.length === 0
                    ? <p className={styles.empty}>Array is empty</p>
                    : array.map((value, index) => (
                        <div key={index} className={getCellClass(index)}>
                            {value}
                            <span className={styles.index}>{index}</span>
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