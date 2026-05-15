import React, { useState } from 'react';
import axios, { AxiosError } from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Briefcase } from 'lucide-react';

type ApiErrorResponse = {
  message?: string;
  error?: string;
};

const Register: React.FC = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await register({
        fullName,
        email,
        password,
      });

      navigate('/');
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const error = err as AxiosError<ApiErrorResponse>;

        const errorMessage =
            error.response?.data?.message ||
            error.response?.data?.error ||
            error.response?.data?.error?.[0] ||
            error.message ||
            'Registration failed';

        setError(errorMessage);
      } else {
        setError('Registration failed');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0 bg-gray-50">
        <div className="flex items-center mb-6 text-2xl font-semibold text-primary-600">
          <Briefcase className="w-8 h-8 mr-2" />
          JobTracker
        </div>

        <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0 border border-gray-200">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
              Create an account
            </h1>

            {error && (
                <div
                    className="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 border border-red-200"
                    role="alert"
                >
                  {error}
                </div>
            )}

            <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
              <div>
                <label
                    htmlFor="fullName"
                    className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Full Name
                </label>

                <input
                    type="text"
                    name="fullName"
                    id="fullName"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                    placeholder="John Doe"
                    required
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                />
              </div>

              <div>
                <label
                    htmlFor="email"
                    className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Email
                </label>

                <input
                    type="email"
                    name="email"
                    id="email"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                    placeholder="name@company.com"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
              </div>

              <div>
                <label
                    htmlFor="password"
                    className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Password
                </label>

                <input
                    type="password"
                    name="password"
                    id="password"
                    placeholder="••••••••"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
              </div>

              <button
                  type="submit"
                  disabled={loading}
                  className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center transition-colors disabled:opacity-50"
              >
                {loading ? 'Creating account...' : 'Create account'}
              </button>

              <p className="text-sm font-light text-gray-500">
                Already have an account?{' '}
                <Link
                    to="/login"
                    className="font-medium text-primary-600 hover:underline"
                >
                  Login here
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
  );
};

export default Register;