import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { jobService } from '../services/job.service';
import { resumeService } from '../services/resume.service';
import {
  Resume,
  JobType,
  Source,
  JobStatus,
} from '../types';
import { ArrowLeft, Save } from 'lucide-react';

const ApplicationForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const isEdit = Boolean(id);
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [resumes, setResumes] = useState<Resume[]>([]);

  const [formData, setFormData] = useState({
    companyName: '',
    jobTitle: '',
    jobDescription: '',
    applicationUrl: '',
    salary: '',
    location: '',
    jobType: 'REMOTE' as JobType,
    source: 'LINKEDIN' as Source,
    status: 'APPLIED' as JobStatus,
    dateApplied: new Date()
        .toISOString()
        .split('T')[0],
    resumeId: '' as string | number,
  });

  const inputClass =
      'w-full p-2.5 bg-gray-50 border border-gray-300 text-gray-900 placeholder:text-gray-500 rounded-lg focus:ring-primary-500 focus:border-primary-500';

  useEffect(() => {
    const fetchData = async () => {
      try {
        const resumesData =
            await resumeService.getAll();

        setResumes(resumesData);

        if (isEdit && id) {
          const app =
              await jobService.getById(
                  parseInt(id)
              );

          setFormData({
            companyName: app.companyName,
            jobTitle: app.jobTitle,
            jobDescription:
                app.jobDescription || '',
            applicationUrl:
                app.applicationUrl || '',
            salary: app.salary || '',
            location: app.location,
            jobType: app.jobType,
            source: app.source,
            status: app.status,
            dateApplied:
            app.dateApplied,
            resumeId:
                app.resume?.id || '',
          });
        }
      } catch (error) {
        console.error(
            'Error fetching application data',
            error
        );
      }
    };

    fetchData();
  }, [id, isEdit]);

  const handleSubmit = async (
      e: React.FormEvent
  ) => {
    e.preventDefault();
    setLoading(true);

    try {
      const payload = {
        ...formData,
        resumeId:
            formData.resumeId === ''
                ? null
                : parseInt(
                    formData.resumeId as string
                ),
      };

      if (isEdit && id) {
        await jobService.update(
            parseInt(id),
            payload
        );
      } else {
        await jobService.create(payload);
      }

      navigate('/applications');
    } catch (error) {
      console.error(
          'Error saving application',
          error
      );
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (
      e: React.ChangeEvent<
          HTMLInputElement |
          HTMLSelectElement |
          HTMLTextAreaElement
      >
  ) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center space-x-4">
          <button
              onClick={() => navigate(-1)}
              className="p-2 bg-white border border-gray-200 rounded-lg hover:bg-gray-100 transition-colors"
          >
            <ArrowLeft className="w-5 h-5 text-gray-500" />
          </button>

          <h1 className="text-2xl font-bold text-gray-900">
            {isEdit
                ? 'Edit Application'
                : 'New Application'}
          </h1>
        </div>

        <form
            onSubmit={handleSubmit}
            className="bg-white p-6 rounded-lg shadow-sm border border-gray-100 space-y-6"
        >
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Company Name *
              </label>
              <input
                  required
                  type="text"
                  name="companyName"
                  className={inputClass}
                  value={formData.companyName}
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Job Title *
              </label>
              <input
                  required
                  type="text"
                  name="jobTitle"
                  className={inputClass}
                  value={formData.jobTitle}
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Location *
              </label>
              <input
                  required
                  type="text"
                  name="location"
                  className={inputClass}
                  value={formData.location}
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Date Applied *
              </label>
              <input
                  required
                  type="date"
                  name="dateApplied"
                  className={inputClass}
                  value={formData.dateApplied}
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Job Type
              </label>
              <select
                  name="jobType"
                  className={inputClass}
                  value={formData.jobType}
                  onChange={handleChange}
              >
                <option value="REMOTE">
                  Remote
                </option>
                <option value="HYBRID">
                  Hybrid
                </option>
                <option value="ONSITE">
                  Onsite
                </option>
              </select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Source
              </label>
              <select
                  name="source"
                  className={inputClass}
                  value={formData.source}
                  onChange={handleChange}
              >
                <option value="LINKEDIN">
                  LinkedIn
                </option>
                <option value="NAUKRI">
                  Naukri
                </option>
                <option value="REFERRAL">
                  Referral
                </option>
                <option value="CAREERS_PAGE">
                  Careers Page
                </option>
                <option value="OTHER">
                  Other
                </option>
              </select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Status
              </label>
              <select
                  name="status"
                  className={inputClass}
                  value={formData.status}
                  onChange={handleChange}
              >
                <option value="APPLIED">
                  Applied
                </option>
                <option value="ONLINE_ASSESSMENT">
                  Online Assessment
                </option>
                <option value="INTERVIEW">
                  Interview
                </option>
                <option value="HR_ROUND">
                  HR Round
                </option>
                <option value="REJECTED">
                  Rejected
                </option>
                <option value="OFFER">
                  Offer
                </option>
                <option value="ACCEPTED">
                  Accepted
                </option>
                <option value="WITHDRAWN">
                  Withdrawn
                </option>
              </select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">
                Resume
              </label>
              <select
                  name="resumeId"
                  className={inputClass}
                  value={formData.resumeId}
                  onChange={handleChange}
              >
                <option value="">
                  None
                </option>

                {resumes.map((resume) => (
                    <option
                        key={resume.id}
                        value={resume.id}
                    >
                      {
                        resume.originalFilename
                      }
                    </option>
                ))}
              </select>
            </div>

            <div className="space-y-2 md:col-span-2">
              <label className="text-sm font-medium text-gray-700">
                Application URL
              </label>
              <input
                  type="url"
                  name="applicationUrl"
                  className={inputClass}
                  value={
                    formData.applicationUrl
                  }
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2 md:col-span-2">
              <label className="text-sm font-medium text-gray-700">
                Salary
              </label>
              <input
                  type="text"
                  name="salary"
                  className={inputClass}
                  value={formData.salary}
                  onChange={handleChange}
              />
            </div>

            <div className="space-y-2 md:col-span-2">
              <label className="text-sm font-medium text-gray-700">
                Job Description
              </label>
              <textarea
                  rows={5}
                  name="jobDescription"
                  className={inputClass}
                  value={
                    formData.jobDescription
                  }
                  onChange={handleChange}
              />
            </div>
          </div>

          <div className="flex justify-end pt-4">
            <button
                type="submit"
                disabled={loading}
                className="flex items-center px-6 py-2.5 bg-primary-600 text-white font-medium rounded-lg hover:bg-primary-700 transition-colors disabled:opacity-50"
            >
              <Save className="w-5 h-5 mr-2" />
              {loading
                  ? 'Saving...'
                  : 'Save Application'}
            </button>
          </div>
        </form>
      </div>
  );
};

export default ApplicationForm;