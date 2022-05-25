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
     */
    async put(url, body) {
        return axios.put(url, body);
    }
}

export const httpClient = new HttpClient();
