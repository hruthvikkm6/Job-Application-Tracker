import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import MainLayout from './layouts/MainLayout';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import ApplicationList from './pages/ApplicationList';
import ApplicationForm from './pages/ApplicationForm';
import ApplicationDetails from './pages/ApplicationDetails';
import ResumeManager from './pages/ResumeManager';
import Reminders from './pages/Reminders';
import Profile from './pages/Profile';
import NotFound from './pages/NotFound';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Public Routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Protected Routes */}
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Dashboard />} />
            <Route path="applications" element={<ApplicationList />} />
            <Route path="applications/new" element={<ApplicationForm />} />
            <Route path="applications/:id" element={<ApplicationDetails />} />
            <Route path="applications/:id/edit" element={<ApplicationForm />} />
            <Route path="resumes" element={<ResumeManager />} />
            <Route path="reminders" element={<Reminders />} />
            <Route path="profile" element={<Profile />} />
          </Route>

          {/* 404 */}
          <Route path="/404" element={<NotFound />} />
          <Route path="*" element={<Navigate to="/404" replace />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
