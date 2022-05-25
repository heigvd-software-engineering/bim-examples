import { httpClient } from "@/httpClient";

const baseUrl = "http://localhost:8080/files";

/**
 *
 * @return {Promise<AxiosResponse<any>>}
 */
async function findAllSummaries() {
    return httpClient.get(baseUrl);
}

/**
 *
 * @param {number} id
 * @return {Promise<AxiosResponse<any>>}
 */
async function findById(id) {
    return httpClient.get(`${baseUrl}/${id}`);
}

export {
    findAllSummaries,
    findById,
};
