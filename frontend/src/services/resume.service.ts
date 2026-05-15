import api from './api';
import { Resume } from '../types';

export const resumeService = {
  upload: async (file: File): Promise<Resume> => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await api.post('/resumes/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },
  getAll: async (): Promise<Resume[]> => {
    const response = await api.get('/resumes');
    return response.data;
  },
  delete: async (id: number) => {
    await api.delete(`/resumes/${id}`);
  },
};
