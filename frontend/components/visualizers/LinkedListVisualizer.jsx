import styles from "./LinkedListVisualizer.module.css";

export default function LinkedListVisualizer({ snapshot, stepInfo }) {
    const list = Array.isArray(snapshot) ? snapshot : [];

    const getBlockClass = (index) => {
        if (!stepInfo) return index === 0 ? `${styles.block} ${styles.head}` : styles.block;

        switch (stepInfo.type) {
            case "TRAVERSE":
            case "SEARCH":
                return `${styles.block} ${styles.highlight}`;
            case "FOUND":
                return `${styles.block} ${styles.found}`;
            case "INSERT_HEAD":
                return index === 0
                    ? `${styles.block} ${styles.highlight}`
                    : styles.block;
            case "INSERT_TAIL":
                return index === list.length - 1
                    ? `${styles.block} ${styles.highlight}`
                    : styles.block;
            case "INSERT_INDEX":
            case "DELETE_INDEX":
                return `${styles.block} ${styles.highlight}`;
            case "DELETE_HEAD":
                return index === 0
                    ? `${styles.block} ${styles.highlight}`
                    : styles.block;
            case "DELETE_TAIL":
                return index === list.length - 1
                    ? `${styles.block} ${styles.highlight}`
                    : styles.block;
            case "ERROR":
                return `${styles.block} ${styles.error}`;
            default:
                return index === 0 ? `${styles.block} ${styles.head}` : styles.block;
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.list}>
                {list.length === 0
                    ? <p className={styles.empty}>List is empty</p>
                    : <>
                        {list.map((value, index) => (
                            <div key={index} className={styles.node}>
                                <div className={getBlockClass(index)}>
                                    {value}
                                    {index === 0 && <span className={styles.label}>head</span>}
                                </div>
                                <span className={styles.arrow}>→</span>
                            </div>
                        ))}
                        <span className={styles.null}>null</span>
                    </>
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