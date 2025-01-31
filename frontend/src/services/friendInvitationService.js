import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/friend-invitations`;

export const getAll = async (recipientId, page) => {
    const result = await request.get(
        `${baseUrl}?recipientId=${recipientId}&page=${page}&size=6`
    );
    return result;
};

export const create = async (senderId, recipientId) => {
    await request.post(`${baseUrl}?userId=${senderId}`, { recipientId });
};

export const accept = async (userId, invitationId) => {
    await request.put(`${baseUrl}/${invitationId}/accept?userId=${userId}`);
};

export const decline = async (userId, invitationId) => {
    await request.put(`${baseUrl}/${invitationId}/decline?userId=${userId}`);
};
