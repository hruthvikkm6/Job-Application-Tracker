import React from 'react';
import { useAuth } from '../context/AuthContext';
import { User, Mail, Shield, Calendar } from 'lucide-react';

const Profile: React.FC = () => {
  const { user } = useAuth();

  if (!user) return null;

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">User Profile</h1>

      <div className="bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden">
        <div className="h-32 bg-primary-600"></div>
        <div className="px-6 pb-6">
          <div className="relative flex justify-between items-end -mt-12 mb-6">
            <div className="p-1 bg-white rounded-full">
              <div className="bg-gray-100 rounded-full p-4 border-4 border-white">
                <User className="w-16 h-16 text-gray-400" />
              </div>
            </div>
          </div>

          <div className="space-y-6">
            <div>
              <h2 className="text-2xl font-bold text-gray-900">{user.fullName}</h2>
              <p className="text-gray-500">{user.email}</p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 pt-6 border-t border-gray-100">
              <div className="flex items-center space-x-3 text-gray-700">
                <Mail className="w-5 h-5 text-gray-400" />
                <div>
                  <p className="text-xs text-gray-400 uppercase font-bold">Email Address</p>
                  <p className="font-medium">{user.email}</p>
                </div>
              </div>
              <div className="flex items-center space-x-3 text-gray-700">
                <Shield className="w-5 h-5 text-gray-400" />
                <div>
                  <p className="text-xs text-gray-400 uppercase font-bold">Role</p>
                  <p className="font-medium">{user.role.replace('ROLE_', '')}</p>
                </div>
              </div>
              <div className="flex items-center space-x-3 text-gray-700">
                <User className="w-5 h-5 text-gray-400" />
                <div>
                  <p className="text-xs text-gray-400 uppercase font-bold">Full Name</p>
                  <p className="font-medium">{user.fullName}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
