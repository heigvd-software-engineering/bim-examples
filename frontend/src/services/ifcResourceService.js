import { httpClient } from "@/httpClient";

const baseUrl = "http://localhost:8080/files";

/**
 * @return {Promise<AxiosResponse<any>>}
 */
async function findAllSummaries() {
    return httpClient.get(baseUrl);
}

/**
 * @param {number} id
 * @return {Promise<AxiosResponse<any>>}
 */
async function findById(id) {
    return httpClient.get(`${baseUrl}/${id}`);
}

/**
 * @param {number} id
 * @return {Promise<AxiosResponse<any>>}
 */
async function findFileBlobById(id) {
    return httpClient.get(`${baseUrl}/${id}/blob`);
}

/**
 *
 * @param {FileDto} fileDto
 * @return {Promise<AxiosResponse<any>>}
 */
async function create(fileDto) {
    return httpClient.post(baseUrl, fileDto);
}

/**
 *
 * @param {number} id
 * @param {File} fileBlob
 * @return {Promise<AxiosResponse<any>>}
 */
async function updateFile(id, fileBlob) {
    return httpClient.put(`${baseUrl}/${id}`, fileBlob, { 'Content-Type': 'application/octet-stream' });
}

export {
    findAllSummaries,
    findById,
    findFileBlobById,
    create,
    updateFile,
};
