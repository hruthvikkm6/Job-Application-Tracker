CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE resumes (
    id BIGSERIAL PRIMARY KEY,
    original_filename VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    public_id VARCHAR(255) NOT NULL,
    version VARCHAR(50),
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE job_applications (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    job_description TEXT,
    application_url VARCHAR(500),
    salary VARCHAR(100),
    location VARCHAR(255) NOT NULL,
    job_type VARCHAR(50) NOT NULL,
    source VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    date_applied DATE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    resume_id BIGINT REFERENCES resumes(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    job_application_id BIGINT NOT NULL REFERENCES job_applications(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE reminders (
    id BIGSERIAL PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    reminder_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    job_application_id BIGINT NOT NULL REFERENCES job_applications(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_job_apps_user ON job_applications(user_id);
CREATE INDEX idx_job_apps_status ON job_applications(status);
CREATE INDEX idx_reminders_user_date ON reminders(user_id, reminder_date);
