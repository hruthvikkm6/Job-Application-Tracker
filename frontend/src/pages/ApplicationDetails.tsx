import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { format } from 'date-fns';
import {
  ArrowLeft,
  Edit,
  Trash2,
  Plus,
  CheckCircle,
  Clock,
  ExternalLink,
} from 'lucide-react';

import { jobService } from '../services/job.service';
import { noteService } from '../services/note.service';
import {
  reminderService,
  ReminderRequest,
} from '../services/reminder.service';

import {
  JobApplication,
  Note,
  Reminder,
} from '../types';

const ApplicationDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [application, setApplication] =
      useState<JobApplication | null>(null);

  const [notes, setNotes] = useState<Note[]>([]);
  const [reminders, setReminders] = useState<Reminder[]>([]);

  const [newNote, setNewNote] = useState('');
  const [loading, setLoading] = useState(true);

  const [showReminderModal, setShowReminderModal] =
      useState(false);

  const [reminderForm, setReminderForm] =
      useState({
        message: '',
        reminderDate: '',
      });

  useEffect(() => {
    const fetchData = async () => {
      if (!id) return;

      try {
        const app = await jobService.getById(parseInt(id));
        setApplication(app);

        const notesData =
            await noteService.getByJob(parseInt(id));
        setNotes(notesData);

        const remindersData =
            await reminderService.getMyReminders();

        setReminders(
            remindersData.filter(
                (r) => r.jobApplicationId === parseInt(id)
            )
        );
      } catch (error) {
        console.error(
            'Error loading application details',
            error
        );
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleDeleteApplication = async () => {
    if (!id) return;

    const confirmed = window.confirm(
        'Are you sure you want to delete this application?'
    );

    if (!confirmed) return;

    try {
      await jobService.delete(parseInt(id));
      navigate('/applications');
    } catch (error) {
      console.error(
          'Error deleting application',
          error
      );
    }
  };

  const handleAddNote = async () => {
    if (!id || !newNote.trim()) return;

    try {
      const note = await noteService.add(
          parseInt(id),
          newNote
      );

      setNotes((prev) => [...prev, note]);
      setNewNote('');
    } catch (error) {
      console.error('Error adding note', error);
    }
  };

  const handleDeleteNote = async (
      noteId: number
  ) => {
    try {
      await noteService.delete(noteId);

      setNotes((prev) =>
          prev.filter((note) => note.id !== noteId)
      );
    } catch (error) {
      console.error('Error deleting note', error);
    }
  };

  const handleAddReminder = async (
      e: React.FormEvent
  ) => {
    e.preventDefault();

    if (!id) return;

    if (
        !reminderForm.message.trim() ||
        !reminderForm.reminderDate
    ) {
      return;
    }

    try {
      const payload: ReminderRequest = {
        message: reminderForm.message,
        reminderDate: new Date(
            reminderForm.reminderDate
        ).toISOString(),
      };

      const reminder =
          await reminderService.add(
              parseInt(id),
              payload
          );

      setReminders((prev) => [
        ...prev,
        reminder,
      ]);

      setReminderForm({
        message: '',
        reminderDate: '',
      });

      setShowReminderModal(false);
    } catch (error) {
      console.error(
          'Error adding reminder',
          error
      );
    }
  };

  const handleCompleteReminder = async (
      reminderId: number
  ) => {
    try {
      const updated =
          await reminderService.complete(
              reminderId
          );

      setReminders((prev) =>
          prev.map((r) =>
              r.id === reminderId ? updated : r
          )
      );
    } catch (error) {
      console.error(
          'Error completing reminder',
          error
      );
    }
  };

  const handleDeleteReminder = async (
      reminderId: number
  ) => {
    try {
      await reminderService.delete(reminderId);

      setReminders((prev) =>
          prev.filter((r) => r.id !== reminderId)
      );
    } catch (error) {
      console.error(
          'Error deleting reminder',
          error
      );
    }
  };

  if (loading) {
    return (
        <div className="flex justify-center p-12">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-600" />
        </div>
    );
  }

  if (!application) {
    return (
        <div className="text-center p-12">
          <h2 className="text-xl font-semibold text-gray-900">
            Application not found
          </h2>
        </div>
    );
  }

  return (
      <>
        <div className="space-y-6">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
              <button
                  onClick={() => navigate(-1)}
                  className="p-2 bg-white border border-gray-200 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <ArrowLeft className="w-5 h-5 text-gray-500" />
              </button>

              <div>
                <h1 className="text-3xl font-bold text-gray-900">
                  {application.companyName}
                </h1>

                <p className="text-lg text-gray-500">
                  {application.jobTitle}
                </p>
              </div>
            </div>

            <div className="flex gap-3">
              <button
                  onClick={() =>
                      navigate(
                          `/applications/${application.id}/edit`
                      )
                  }
                  className="flex items-center px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <Edit className="w-4 h-4 mr-2" />
                Edit
              </button>

              <button
                  onClick={
                    handleDeleteApplication
                  }
                  className="flex items-center px-4 py-2 border border-red-300 text-red-600 rounded-lg hover:bg-red-50 transition-colors"
              >
                <Trash2 className="w-4 h-4 mr-2" />
                Delete
              </button>
            </div>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2 space-y-6">
              <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-900 mb-6">
                  Details
                </h2>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6 text-sm">
                  <div>
                    <p className="text-gray-500">
                      Status
                    </p>
                    <p className="font-medium text-gray-900">
                      {application.status}
                    </p>
                  </div>

                  <div>
                    <p className="text-gray-500">
                      Applied On
                    </p>
                    <p className="font-medium text-gray-900">
                      {format(
                          new Date(
                              application.dateApplied
                          ),
                          'MMMM do, yyyy'
                      )}
                    </p>
                  </div>

                  <div>
                    <p className="text-gray-500">
                      Location
                    </p>
                    <p className="font-medium text-gray-900">
                      {application.location} (
                      {application.jobType})
                    </p>
                  </div>

                  <div>
                    <p className="text-gray-500">
                      Source
                    </p>
                    <p className="font-medium text-gray-900">
                      {application.source}
                    </p>
                  </div>
                </div>

                {application.applicationUrl && (
                    <div className="mt-6">
                      <a
                          href={
                            application.applicationUrl
                          }
                          target="_blank"
                          rel="noopener noreferrer"
                          className="inline-flex items-center text-primary-600 hover:underline"
                      >
                        View Job Posting
                        <ExternalLink className="w-4 h-4 ml-2" />
                      </a>
                    </div>
                )}
              </div>

              <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-900 mb-4">
                  Notes
                </h2>

                <textarea
                    value={newNote}
                    onChange={(e) =>
                        setNewNote(
                            e.target.value
                        )
                    }
                    placeholder="Add a note..."
                    rows={4}
                    className="w-full p-3 bg-gray-50 border border-gray-300 text-gray-900 placeholder:text-gray-500 rounded-lg focus:ring-primary-500 focus:border-primary-500"
                />

                <div className="flex justify-end mt-4">
                  <button
                      onClick={handleAddNote}
                      className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors"
                  >
                    Add Note
                  </button>
                </div>

                <div className="mt-6 space-y-3">
                  {notes.map((note) => (
                      <div
                          key={note.id}
                          className="p-4 bg-gray-50 border border-gray-200 rounded-lg"
                      >
                        <div className="flex justify-between items-start gap-4">
                          <div>
                            <p className="text-gray-900">
                              {note.content}
                            </p>
                            <p className="text-xs text-gray-500 mt-2">
                              {format(
                                  new Date(
                                      note.createdAt
                                  ),
                                  'PPP p'
                              )}
                            </p>
                          </div>

                          <button
                              onClick={() =>
                                  handleDeleteNote(
                                      note.id
                                  )
                              }
                              className="text-red-500 hover:text-red-700"
                          >
                            <Trash2 className="w-4 h-4" />
                          </button>
                        </div>
                      </div>
                  ))}
                </div>
              </div>
            </div>

            <div className="space-y-6">
              <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-900 mb-4">
                  Resume
                </h2>

                {application.resume ? (
                    <a
                        href={application.resume.url}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="inline-flex items-center text-primary-600 hover:underline"
                    >
                      {application.resume.originalFilename}
                      <ExternalLink className="w-4 h-4 ml-2" />
                    </a>
                ) : (
                    <p className="text-gray-500">
                      No resume associated.
                    </p>
                )}
              </div>

              <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-100">
                <div className="flex items-center justify-between mb-4">
                  <h2 className="text-xl font-semibold text-gray-900">
                    Reminders
                  </h2>

                  <button
                      onClick={() =>
                          setShowReminderModal(
                              true
                          )
                      }
                      className="p-2 text-primary-600 hover:bg-primary-50 rounded-lg transition-colors"
                  >
                    <Plus className="w-5 h-5" />
                  </button>
                </div>

                {reminders.length === 0 ? (
                    <p className="text-gray-500">
                      No reminders yet.
                    </p>
                ) : (
                    <div className="space-y-3">
                      {reminders.map(
                          (reminder) => (
                              <div
                                  key={reminder.id}
                                  className="p-4 bg-gray-50 border border-gray-200 rounded-lg"
                              >
                                <div className="flex justify-between items-start gap-3">
                                  <div>
                                    <p className="text-gray-900 font-medium">
                                      {
                                        reminder.message
                                      }
                                    </p>

                                    <p className="text-xs text-gray-500 mt-1 flex items-center">
                                      <Clock className="w-3 h-3 mr-1" />
                                      {format(
                                          new Date(
                                              reminder.reminderDate
                                          ),
                                          'PPP p'
                                      )}
                                    </p>
                                  </div>

                                  <div className="flex gap-2">
                                    {!reminder.completed && (
                                        <button
                                            onClick={() =>
                                                handleCompleteReminder(
                                                    reminder.id
                                                )
                                            }
                                            className="text-green-600 hover:text-green-800"
                                        >
                                          <CheckCircle className="w-4 h-4" />
                                        </button>
                                    )}

                                    <button
                                        onClick={() =>
                                            handleDeleteReminder(
                                                reminder.id
                                            )
                                        }
                                        className="text-red-500 hover:text-red-700"
                                    >
                                      <Trash2 className="w-4 h-4" />
                                    </button>
                                  </div>
                                </div>
                              </div>
                          )
                      )}
                    </div>
                )}
              </div>
            </div>
          </div>
        </div>

        {showReminderModal && (
            <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
              <div className="bg-white p-6 rounded-xl shadow-xl w-full max-w-md space-y-4">
                <h2 className="text-xl font-semibold text-gray-900">
                  Add Reminder
                </h2>

                <form
                    onSubmit={
                      handleAddReminder
                    }
                    className="space-y-4"
                >
              <textarea
                  rows={3}
                  placeholder="Reminder message..."
                  value={
                    reminderForm.message
                  }
                  onChange={(e) =>
                      setReminderForm(
                          (
                              prev
                          ) => ({
                            ...prev,
                            message:
                            e.target.value,
                          })
                      )
                  }
                  className="w-full p-3 bg-gray-50 border border-gray-300 text-gray-900 placeholder:text-gray-500 rounded-lg"
              />

                  <input
                      type="datetime-local"
                      value={
                        reminderForm.reminderDate
                      }
                      onChange={(e) =>
                          setReminderForm(
                              (
                                  prev
                              ) => ({
                                ...prev,
                                reminderDate:
                                e.target.value,
                              })
                          )
                      }
                      className="w-full p-3 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg"
                  />

                  <div className="flex justify-end gap-3">
                    <button
                        type="button"
                        onClick={() =>
                            setShowReminderModal(
                                false
                            )
                        }
                        className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100"
                    >
                      Cancel
                    </button>

                    <button
                        type="submit"
                        className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
                    >
                      Add Reminder
                    </button>
                  </div>
                </form>
              </div>
            </div>
        )}
      </>
  );
};

export default ApplicationDetails;