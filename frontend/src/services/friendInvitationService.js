import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/friend-invitations`;

export const getAll = async (recipientId, page) => {
    const result = await request.get(
        `${baseUrl}?recipientId=${recipientId}&page=${page}&size=1`
    );
    return result;
};

export const accept = async (invitationId) => {
    await request.put(`${baseUrl}/${invitationId}/accept`);
};

export const decline = async (invitationId) => {
    await request.put(`${baseUrl}/${invitationId}/decline`);
};
