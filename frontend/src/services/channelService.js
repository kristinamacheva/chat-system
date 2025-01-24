import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/channels`;

export const create = async (userId, channelData) => {
    const result = await request.post(`${baseUrl}?userId=${userId}`, channelData);
    return result;
};

export const getAll = async (userId, page) => {
    const result = await request.get(
        `${baseUrl}?userId=${userId}&page=${page}&size=10`
    );
    return result;
};