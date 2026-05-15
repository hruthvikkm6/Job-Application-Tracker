import api from './api';
import { Note } from '../types';

export const noteService = {
  getByJob: async (jobId: number): Promise<Note[]> => {
    const response = await api.get(`/jobs/${jobId}/notes`);
    return response.data;
  },
  add: async (jobId: number, content: string): Promise<Note> => {
    const response = await api.post(`/jobs/${jobId}/notes`, { content });
    return response.data;
  },
  update: async (id: number, content: string): Promise<Note> => {
    const response = await api.put(`/notes/${id}`, { content });
    return response.data;
  },
  delete: async (id: number) => {
    await api.delete(`/notes/${id}`);
  },
};
