import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { jobService } from '../services/job.service';
import { JobApplication, JobStatus, Source } from '../types';
import { 
  Plus, 
  Search, 
  Filter, 
  MoreVertical, 
  MapPin, 
  Calendar,
  ExternalLink
} from 'lucide-react';
import { format } from 'date-fns';

const ApplicationList: React.FC = () => {
  const [applications, setApplications] = useState<JobApplication[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [status, setStatus] = useState<JobStatus | ''>('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchApplications = async () => {
    setLoading(true);
    try {
      const data = await jobService.getAll({
        company: search,
        status: status || undefined,
        page,
        size: 10
      });
      setApplications(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error('Error fetching applications', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchApplications();
  }, [page, status]);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setPage(0);
    fetchApplications();
  };

  const getStatusColor = (status: JobStatus) => {
    switch (status) {
      case 'APPLIED': return 'bg-blue-100 text-blue-800';
      case 'INTERVIEW':
      case 'HR_ROUND': return 'bg-purple-100 text-purple-800';
      case 'OFFER':
      case 'ACCEPTED': return 'bg-green-100 text-green-800';
      case 'REJECTED': return 'bg-red-100 text-red-800';
      case 'WITHDRAWN': return 'bg-gray-100 text-gray-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <h1 className="text-2xl font-bold text-gray-900">Job Applications</h1>
        <Link
          to="/applications/new"
          className="flex items-center px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors"
        >
          <Plus className="w-5 h-5 mr-2" />
          Add Application
        </Link>
      </div>

      {/* Filters */}
      <div className="bg-white p-4 rounded-lg shadow-sm border border-gray-100 flex flex-col md:flex-row gap-4">
        <form onSubmit={handleSearch} className="flex-1 relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
          <input
            type="text"
            placeholder="Search by company or title..."
            className="w-full pl-10 pr-4 py-2 bg-gray-50 border border-gray-300 rounded-lg focus:ring-primary-500 focus:border-primary-500"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </form>
        <select
          className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 p-2.5"
          value={status}
          onChange={(e) => setStatus(e.target.value as JobStatus)}
        >
          <option value="">All Statuses</option>
          <option value="APPLIED">Applied</option>
          <option value="ONLINE_ASSESSMENT">Online Assessment</option>
          <option value="INTERVIEW">Interview</option>
          <option value="HR_ROUND">HR Round</option>
          <option value="REJECTED">Rejected</option>
          <option value="OFFER">Offer</option>
          <option value="ACCEPTED">Accepted</option>
          <option value="WITHDRAWN">Withdrawn</option>
        </select>
      </div>

      {/* Table/List */}
      <div className="bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden">
        {loading ? (
          <div className="p-8 flex justify-center">
            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-primary-600"></div>
          </div>
        ) : applications.length === 0 ? (
          <div className="p-8 text-center text-gray-500">
            No applications found. Try adjusting your filters or add a new one.
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left text-gray-500">
              <thead className="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-200">
                <tr>
                  <th className="px-6 py-3">Company & Title</th>
                  <th className="px-6 py-3">Status</th>
                  <th className="px-6 py-3">Date Applied</th>
                  <th className="px-6 py-3">Location</th>
                  <th className="px-6 py-3 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {applications.map((app) => (
                  <tr key={app.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4">
                      <div className="flex flex-col">
                        <Link to={`/applications/${app.id}`} className="font-semibold text-gray-900 hover:text-primary-600">
                          {app.companyName}
                        </Link>
                        <span className="text-gray-500">{app.jobTitle}</span>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(app.status)}`}>
                        {app.status.replace('_', ' ')}
                      </span>
                    </td>
                    <td className="px-6 py-4 flex items-center">
                      <Calendar className="w-4 h-4 mr-2 text-gray-400" />
                      {format(new Date(app.dateApplied), 'MMM dd, yyyy')}
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center">
                        <MapPin className="w-4 h-4 mr-2 text-gray-400" />
                        {app.location}
                      </div>
                    </td>
                    <td className="px-6 py-4 text-right">
                      <Link
                        to={`/applications/${app.id}`}
                        className="text-primary-600 hover:underline inline-flex items-center"
                      >
                        Details
                        <ExternalLink className="w-4 h-4 ml-1" />
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
        
        {/* Pagination */}
        {totalPages > 1 && (
          <div className="flex items-center justify-between px-6 py-4 bg-gray-50 border-t border-gray-200">
            <button
              disabled={page === 0}
              onClick={() => setPage(p => p - 1)}
              className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 disabled:opacity-50"
            >
              Previous
            </button>
            <span className="text-sm text-gray-700">
              Page {page + 1} of {totalPages}
            </span>
            <button
              disabled={page === totalPages - 1}
              onClick={() => setPage(p => p + 1)}
              className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 disabled:opacity-50"
            >
              Next
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ApplicationList;
