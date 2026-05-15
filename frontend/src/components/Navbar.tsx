import React from 'react';
import { useAuth } from '../context/AuthContext';
import { LogOut, User as UserIcon } from 'lucide-react';

const Navbar: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <nav className="bg-white border-b border-gray-200 fixed w-full z-30 top-0">
      <div className="px-3 py-3 lg:px-5 lg:pl-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center justify-start">
            <span className="self-center text-xl font-bold sm:text-2xl whitespace-nowrap text-primary-600">
              JobTracker
            </span>
          </div>
          <div className="flex items-center">
            <div className="flex items-center ml-3">
              <div className="flex items-center mr-4">
                <UserIcon className="w-5 h-5 text-gray-500 mr-2" />
                <span className="text-sm font-medium text-gray-900">{user?.fullName}</span>
              </div>
              <button
                onClick={logout}
                className="flex items-center px-3 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
              >
                <LogOut className="w-4 h-4 mr-2" />
                Logout
              </button>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
