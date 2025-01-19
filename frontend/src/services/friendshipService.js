import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/friendships`;

export const getAll = async (userId, page) => {
    const result = await request.get(`${baseUrl}?userId=${userId}&page=${page}&size=10`);
    return result;
};
