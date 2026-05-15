import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';

const MainLayout: React.FC = () => {
  const { token, loading } = useAuth();

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  return (
    <div className="bg-gray-50 min-h-screen">
      <Navbar />
      <div className="flex overflow-hidden bg-gray-50 pt-16">
        <Sidebar />
        <main className="relative w-full h-full overflow-y-auto bg-gray-50 lg:ml-64 min-h-screen">
          <div className="px-4 py-6">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
};

export default MainLayout;
