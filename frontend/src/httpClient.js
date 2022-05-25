import axios from "axios";

class HttpClient {
    /**
     * @param {string} url
     */
    async get(url) {
        return axios.get(url);
    }
}

export const httpClient = new HttpClient();
