import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/messages`;

export const createChannelMessage = async (senderId, channelId, messageData) => {
    return await request.post(`${baseUrl}/channels/${channelId}?userId=${senderId}`, messageData);
};

export const createFriendMessage = async (senderId, friendId, messageData) => {
    return await request.post(`${baseUrl}/friends/${friendId}?userId=${senderId}`, messageData);
};

export const getAllFriendMessages = async (userId, friendId, lastMessageId = null) => {
    let url = `${baseUrl}/friends/${friendId}?userId=${userId}`;
    if (lastMessageId) {
        url += `&lastMessageId=${lastMessageId}`;
    } 
    const result = await request.get(url);
    return result;
};

export const getAllChannelMessages = async (userId, channelId, lastMessageId = null) => {
    let url = `${baseUrl}/channels/${channelId}?userId=${userId}`;
    if (lastMessageId) {
        url += `&lastMessageId=${lastMessageId}`;
    } 
    const result = await request.get(url);
    return result;
};