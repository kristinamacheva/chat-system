import { API_BASE_URL } from "../constants/constants";
import * as request from "../lib/request";

const baseUrl = `${API_BASE_URL}/users`;

export const getOne = async (id) => {
    const result = await request.get(`${baseUrl}/${id}`);
    return result;
};

export const getAll = async (page, params = {}) => {
    Object.keys(params).forEach(
        (key) => params[key] === "" && delete params[key]
    );
    const queryParams = new URLSearchParams({
        page,
        size: 6,
        ...params, 
    });

    const url = `${baseUrl}?${queryParams.toString()}`;
    const result = await request.get(url);
    return result;
};
