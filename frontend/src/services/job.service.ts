import api from './api';
import { JobApplication, JobStatus, Source } from '../types';

export const jobService = {
  getAll: async (params: any) => {
    const response = await api.get('/jobs', { params });
    return response.data;
  },
  getById: async (id: number): Promise<JobApplication> => {
    const response = await api.get(`/jobs/${id}`);
    return response.data;
  },
  create: async (data: any): Promise<JobApplication> => {
    const response = await api.post('/jobs', data);
    return response.data;
  },
  update: async (id: number, data: any): Promise<JobApplication> => {
    const response = await api.put(`/jobs/${id}`, data);
    return response.data;
  },
  delete: async (id: number) => {
    await api.delete(`/jobs/${id}`);
  },
};
