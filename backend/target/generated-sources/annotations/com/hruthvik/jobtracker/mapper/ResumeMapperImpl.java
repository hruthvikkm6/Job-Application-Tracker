package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.response.ResumeResponse;
import com.hruthvik.jobtracker.entity.Resume;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:42:00+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ResumeMapperImpl implements ResumeMapper {

    @Override
    public ResumeResponse toResponse(Resume resume) {
        if ( resume == null ) {
            return null;
        }

        ResumeResponse.ResumeResponseBuilder resumeResponse = ResumeResponse.builder();

        resumeResponse.id( resume.getId() );
        resumeResponse.originalFilename( resume.getOriginalFilename() );
        resumeResponse.url( resume.getUrl() );
        resumeResponse.publicId( resume.getPublicId() );
        resumeResponse.version( resume.getVersion() );
        resumeResponse.createdAt( resume.getCreatedAt() );

        return resumeResponse.build();
    }
}
