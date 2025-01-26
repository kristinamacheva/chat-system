import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/messages`;

export const createChannelMessage = async (senderId, channelId, messageData) => {
    await request.post(`${baseUrl}/channels/${channelId}?userId=${senderId}`, messageData);
};

export const createFriendMessage = async (senderId, recipientId, messageData) => {
    await request.post(`${baseUrl}/friends/${recipientId}?userId=${senderId}`, messageData);
};