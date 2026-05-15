import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  LayoutDashboard, 
  Briefcase, 
  FileText, 
  Bell, 
  UserCircle 
} from 'lucide-react';
import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

const Sidebar: React.FC = () => {
  const navItems = [
    { name: 'Dashboard', icon: LayoutDashboard, path: '/' },
    { name: 'Applications', icon: Briefcase, path: '/applications' },
    { name: 'Resumes', icon: FileText, path: '/resumes' },
    { name: 'Reminders', icon: Bell, path: '/reminders' },
    { name: 'Profile', icon: UserCircle, path: '/profile' },
  ];

  return (
    <aside className="fixed top-0 left-0 z-20 flex-col flex-shrink-0 hidden w-64 h-full pt-16 font-normal duration-75 lg:flex transition-width">
      <div className="relative flex flex-col flex-1 min-h-0 pt-0 bg-white border-r border-gray-200">
        <div className="flex flex-col flex-1 pt-5 pb-4 overflow-y-auto">
          <div className="flex-1 px-3 space-y-1 bg-white divide-y divide-gray-200">
            <ul className="pb-2 space-y-2">
              {navItems.map((item) => (
                <li key={item.name}>
                  <NavLink
                    to={item.path}
                    className={({ isActive }) =>
                      cn(
                        'flex items-center p-2 text-base font-normal rounded-lg transition-colors group',
                        isActive
                          ? 'bg-primary-50 text-primary-600'
                          : 'text-gray-900 hover:bg-gray-100'
                      )
                    }
                  >
                    <item.icon className={cn("w-6 h-6 transition duration-75", "text-gray-500 group-hover:text-gray-900")} />
                    <span className="ml-3">{item.name}</span>
                  </NavLink>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
