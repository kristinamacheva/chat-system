import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/users`;

export const getOne = async (id) => {
    const result = await request.get(`${baseUrl}/${id}`);
    return result;
};
