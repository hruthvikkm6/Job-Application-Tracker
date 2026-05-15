import api from './api';
import { AuthResponse, User } from '../types';

export const authService = {
  login: async (data: any): Promise<AuthResponse> => {
    const response = await api.post('/auth/login', data);
    return response.data;
  },
  register: async (data: any): Promise<AuthResponse> => {
    const response = await api.post('/auth/register', data);
    return response.data;
  },
  getMe: async (): Promise<User> => {
    const response = await api.get('/auth/me');
    return response.data;
  },
};
