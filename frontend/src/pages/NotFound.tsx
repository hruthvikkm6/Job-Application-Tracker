import React from 'react';
import { Link } from 'react-router-dom';
import { Briefcase } from 'lucide-react';

const NotFound: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 px-6">
      <Briefcase className="w-16 h-16 text-primary-600 mb-4" />
      <h1 className="text-6xl font-bold text-gray-900 mb-2">404</h1>
      <h2 className="text-2xl font-semibold text-gray-700 mb-6">Page Not Found</h2>
      <p className="text-gray-500 mb-8 text-center max-w-md">
        Oops! The page you're looking for doesn't exist or has been moved.
      </p>
      <Link
        to="/"
        className="px-6 py-3 bg-primary-600 text-white font-medium rounded-lg hover:bg-primary-700 transition-colors"
      >
        Go back home
      </Link>
    </div>
  );
};

export default NotFound;
