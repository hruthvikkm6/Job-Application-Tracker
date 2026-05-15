import api from './api';
import { Reminder } from '../types';

export interface ReminderRequest {
  message: string;
  reminderDate: string;
}

export const reminderService = {
  getMyReminders: async (): Promise<Reminder[]> => {
    const response = await api.get('/reminders');
    return response.data;
  },

  add: async (
      jobId: number,
      data: ReminderRequest
  ): Promise<Reminder> => {
    const response = await api.post(
        `/jobs/${jobId}/reminders`,
        data
    );

    return response.data;
  },

  complete: async (id: number): Promise<Reminder> => {
    const response = await api.put(`/reminders/${id}/complete`);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/reminders/${id}`);
  },
};