export type Role = 'ROLE_USER' | 'ROLE_ADMIN';
export type JobType = 'REMOTE' | 'HYBRID' | 'ONSITE';
export type Source = 'LINKEDIN' | 'NAUKRI' | 'REFERRAL' | 'CAREERS_PAGE' | 'OTHER';
export type JobStatus = 'APPLIED' | 'ONLINE_ASSESSMENT' | 'INTERVIEW' | 'HR_ROUND' | 'REJECTED' | 'OFFER' | 'ACCEPTED' | 'WITHDRAWN';

export interface User {
  id: number;
  email: string;
  fullName: string;
  role: Role;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface Resume {
  id: number;
  originalFilename: string;
  url: string;
  publicId: string;
  version: string;
  createdAt: string;
}

export interface JobApplication {
  id: number;
  companyName: string;
  jobTitle: string;
  jobDescription: string;
  applicationUrl: string;
  salary: string;
  location: string;
  jobType: JobType;
  source: Source;
  status: JobStatus;
  dateApplied: string;
  resume?: Resume;
  createdAt: string;
  updatedAt: string;
}

export interface Note {
  id: number;
  content: string;
  createdAt: string;
  updatedAt: string;
}

export interface Reminder {
  id: number;
  message: string;
  reminderDate: string;
  completed: boolean;
  jobApplicationId: number;
  companyName: string;
  createdAt: string;
}

export interface DashboardSummary {
  totalApplications: number;
  interviewsCount: number;
  offersCount: number;
  rejectionCount: number;
  applicationsByStatus: Record<string, number>;
  applicationsBySource: Record<string, number>;
}

export interface ChartData {
  label: string;
  value: number;
}
