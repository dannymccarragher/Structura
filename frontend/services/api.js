// TODO: change to prod URL
const BASE_URL = "http://localhost:3001"

export async function simulate(structure, operation, values, target) {
    const response = await fetch(`${BASE_URL}/simulate`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ structure, operation, values, target })
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Simulation failed");
    }

    return response.json();
}

export async function reset(structure) {
    const response = await fetch(`${BASE_URL}/reset`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ structure })
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Reset failed");
    }
}