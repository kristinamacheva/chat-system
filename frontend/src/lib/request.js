const buildOptions = (data) => {
    const options = {};

    if (data) {
        options.body = JSON.stringify(data);
        options.headers = {
            "content-type": "application/json",
        };
    }

    return options;
};

const request = async (method, url, data) => {
    try {
        const response = await fetch(url, {
            ...buildOptions(data),
            method,
        });

        // 204 - no content
        if (response.status === 204) {
            return {};
        }

        const result = await response.json();

        if (!response.ok) {
            const error = {
                status: response.status || 500,
                message: result.message || "Invalid request",
                errors: result.errors || [],
            };
            throw error;
        }

        return result;
    } catch (error) {
        // Handle network errors or server unreachable scenarios
        if (error instanceof TypeError && error.message === "Failed to fetch") {
            // If fetch failed due to network issues (server down, connection lost, etc.)
            throw new Error(
                "The server is unreachable."
            );
        } else {
            // For other errors, re-throw the original error for higher-level error handling
            throw error;
        }
    }
};

export const get = request.bind(null, "GET");
export const post = request.bind(null, "POST");
export const put = request.bind(null, "PUT");
export const remove = request.bind(null, "DELETE");
export const patch = request.bind(null, "PATCH");
