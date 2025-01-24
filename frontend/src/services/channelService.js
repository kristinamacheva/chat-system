import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/channels`;

export const create = async (userId, channelData) => {
    const result = await request.post(
        `${baseUrl}?userId=${userId}`,
        channelData
    );
    return result;
};

export const getAll = async (userId, page) => {
    const result = await request.get(
        `${baseUrl}?userId=${userId}&page=${page}&size=10`
    );
    return result;
};

export const getOne = async (id) => {
    const result = await request.get(`${baseUrl}/${id}`);
    return result;
};

export const update = async (channelId, userId, channelData) => {
    const result = await request.put(
        `${baseUrl}/${channelId}?userId=${userId}`,
        channelData
    );
    return result;
};

export const addMember = async (channelId, userId, memberData) => {
    const result = await request.post(
        `${baseUrl}/${channelId}/members?userId=${userId}`,
        memberData
    );
    return result;
};

export const remove = async (channelId, userId) => {
    await request.remove(`${baseUrl}/${channelId}?userId=${userId}`);
};
