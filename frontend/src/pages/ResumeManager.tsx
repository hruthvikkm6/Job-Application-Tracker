import React, { useEffect, useState } from 'react';
import { resumeService } from '../services/resume.service';
import { Resume } from '../types';
import { FileText, Trash2, Upload, Calendar, ExternalLink } from 'lucide-react';
import { format } from 'date-fns';

const ResumeManager: React.FC = () => {
  const [resumes, setResumes] = useState<Resume[]>([]);
  const [loading, setLoading] = useState(true);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState('');

  const fetchResumes = async () => {
    try {
      const data = await resumeService.getAll();
      setResumes(data);
    } catch (error) {
      console.error('Error fetching resumes', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchResumes();
  }, []);

  const handleUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    if (file.type !== 'application/pdf') {
      setError('Only PDF files are allowed');
      return;
    }

    setUploading(true);
    setError('');
    try {
      await resumeService.upload(file);
      await fetchResumes();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Upload failed');
    } finally {
      setUploading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm('Are you sure you want to delete this resume?')) return;
    try {
      await resumeService.delete(id);
      setResumes(resumes.filter(r => r.id !== id));
    } catch (error) {
      console.error('Error deleting resume', error);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-900">Resume Manager</h1>
        <label className="flex items-center px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors cursor-pointer">
          <Upload className="w-5 h-5 mr-2" />
          {uploading ? 'Uploading...' : 'Upload PDF'}
          <input type="file" className="hidden" accept=".pdf" onChange={handleUpload} disabled={uploading} />
        </label>
      </div>

      {error && (
        <div className="p-4 text-sm text-red-800 rounded-lg bg-red-50 border border-red-200">
          {error}
        </div>
      )}

      {loading ? (
        <div className="flex justify-center p-12">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-600"></div>
        </div>
      ) : resumes.length === 0 ? (
        <div className="bg-white p-12 text-center rounded-lg shadow-sm border border-gray-100">
          <FileText className="w-16 h-16 text-gray-200 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900">No resumes yet</h3>
          <p className="text-gray-500">Upload your resumes to associate them with your applications.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {resumes.map((resume) => (
            <div key={resume.id} className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 space-y-4 hover:shadow-md transition-shadow">
              <div className="flex items-start justify-between">
                <div className="p-3 bg-primary-50 rounded-lg">
                  <FileText className="w-8 h-8 text-primary-600" />
                </div>
                <button
                  onClick={() => handleDelete(resume.id)}
                  className="p-1 text-gray-400 hover:text-red-600 transition-colors"
                >
                  <Trash2 className="w-5 h-5" />
                </button>
              </div>
              <div>
                <h3 className="font-semibold text-gray-900 truncate" title={resume.originalFilename}>
                  {resume.originalFilename}
                </h3>
                <div className="flex items-center text-xs text-gray-400 mt-1">
                  <Calendar className="w-3 h-3 mr-1" />
                  Uploaded on {format(new Date(resume.createdAt), 'MMM dd, yyyy')}
                </div>
              </div>
              <div className="pt-2">
                <a
                  href={resume.url}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center justify-center w-full px-4 py-2 text-sm font-medium text-primary-600 bg-primary-50 rounded-lg hover:bg-primary-100 transition-colors"
                >
                  View Resume
                  <ExternalLink className="w-4 h-4 ml-2" />
                </a>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ResumeManager;
