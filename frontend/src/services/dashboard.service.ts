import api from './api';
import { DashboardSummary, ChartData } from '../types';

export const dashboardService = {
  getSummary: async (): Promise<DashboardSummary> => {
    const response = await api.get('/dashboard/summary');
    return response.data;
  },
  getStatusChart: async (): Promise<ChartData[]> => {
    const response = await api.get('/dashboard/charts/status');
    return response.data;
  },
  getSourceChart: async (): Promise<ChartData[]> => {
    const response = await api.get('/dashboard/charts/source');
    return response.data;
  },
  getTrendChart: async (): Promise<ChartData[]> => {
    const response = await api.get('/dashboard/charts/trend');
    return response.data;
  },
};
