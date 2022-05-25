import { httpClient } from "@/httpClient";

async function findAllSummaries() {
    return httpClient.get("http://localhost:8080/hello");
}

export {
    findAllSummaries,
};
