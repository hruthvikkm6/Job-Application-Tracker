import React, { useEffect, useState } from 'react';
import { reminderService } from '../services/reminder.service';
import { Reminder } from '../types';
import { Bell, CheckCircle2, Trash2, Calendar, Clock, Briefcase } from 'lucide-react';
import { format, isPast } from 'date-fns';

const Reminders: React.FC = () => {
  const [reminders, setReminders] = useState<Reminder[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchReminders = async () => {
    try {
      const data = await reminderService.getMyReminders();
      // Sort by date, soonest first
      setReminders(data.sort((a, b) => new Date(a.reminderDate).getTime() - new Date(b.reminderDate).getTime()));
    } catch (error) {
      console.error('Error fetching reminders', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReminders();
  }, []);

  const handleComplete = async (id: number) => {
    try {
      await reminderService.complete(id);
      setReminders(reminders.map(r => r.id === id ? { ...r, completed: true } : r));
    } catch (error) {
      console.error('Error completing reminder', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm('Delete this reminder?')) return;
    try {
      await reminderService.delete(id);
      setReminders(reminders.filter(r => r.id !== id));
    } catch (error) {
      console.error('Error deleting reminder', error);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center p-12">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Follow-up Reminders</h1>

      {reminders.length === 0 ? (
        <div className="bg-white p-12 text-center rounded-lg shadow-sm border border-gray-100">
          <Bell className="w-16 h-16 text-gray-200 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900">All caught up!</h3>
          <p className="text-gray-500">You don't have any pending reminders.</p>
        </div>
      ) : (
        <div className="space-y-4">
          {reminders.map((reminder) => {
            const isOverdue = isPast(new Date(reminder.reminderDate)) && !reminder.completed;
            return (
              <div 
                key={reminder.id} 
                className={`bg-white p-4 rounded-lg shadow-sm border ${reminder.completed ? 'border-gray-100 opacity-60' : isOverdue ? 'border-red-200' : 'border-gray-100'} flex items-center justify-between gap-4`}
              >
                <div className="flex items-start gap-4">
                  <div className={`p-2 rounded-full ${reminder.completed ? 'bg-green-100' : isOverdue ? 'bg-red-100' : 'bg-blue-100'}`}>
                    {reminder.completed ? (
                      <CheckCircle2 className="w-5 h-5 text-green-600" />
                    ) : (
                      <Bell className={`w-5 h-5 ${isOverdue ? 'text-red-600' : 'text-blue-600'}`} />
                    )}
                  </div>
                  <div>
                    <h3 className={`font-semibold ${reminder.completed ? 'line-through text-gray-500' : 'text-gray-900'}`}>
                      {reminder.message}
                    </h3>
                    <div className="flex flex-wrap gap-x-4 gap-y-1 mt-1">
                      <div className="flex items-center text-xs text-gray-500">
                        <Briefcase className="w-3 h-3 mr-1" />
                        {reminder.companyName}
                      </div>
                      <div className={`flex items-center text-xs ${isOverdue ? 'text-red-600 font-bold' : 'text-gray-500'}`}>
                        <Calendar className="w-3 h-3 mr-1" />
                        {format(new Date(reminder.reminderDate), 'PPP')}
                      </div>
                      <div className="flex items-center text-xs text-gray-500">
                        <Clock className="w-3 h-3 mr-1" />
                        {format(new Date(reminder.reminderDate), 'p')}
                      </div>
                    </div>
                  </div>
                </div>
                <div className="flex items-center gap-2">
                  {!reminder.completed && (
                    <button
                      onClick={() => handleComplete(reminder.id)}
                      className="p-2 text-green-600 hover:bg-green-50 rounded-lg transition-colors"
                      title="Mark Complete"
                    >
                      <CheckCircle2 className="w-5 h-5" />
                    </button>
                  )}
                  <button
                    onClick={() => handleDelete(reminder.id)}
                    className="p-2 text-gray-400 hover:text-red-600 rounded-lg transition-colors"
                    title="Delete"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Reminders;
