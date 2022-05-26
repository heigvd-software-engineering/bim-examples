import axios from "axios";

class HttpClient {

    /**
     * @param {string} url
     */
    async get(url) {
        return axios.get(url);
    }

    /**
     * @param {string} url
     * @param {any} body
     */
    async post(url, body) {
        return axios.post(url, body);
    }

    /**
     * @param {string} url
     * @param {any} body
     * @param {Object} headers
     */
    async put(url, body, headers = {}) {
        return axios.put(url, body, {
            headers,
        });
    }
}

export const httpClient = new HttpClient();
