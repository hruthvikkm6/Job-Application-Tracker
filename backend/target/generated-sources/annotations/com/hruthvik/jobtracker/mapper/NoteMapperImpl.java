package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.NoteRequest;
import com.hruthvik.jobtracker.dto.response.NoteResponse;
import com.hruthvik.jobtracker.entity.Note;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:42:00+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note toEntity(NoteRequest request) {
        if ( request == null ) {
            return null;
        }

        Note.NoteBuilder<?, ?> note = Note.builder();

        note.content( request.getContent() );

        return note.build();
    }

    @Override
    public NoteResponse toResponse(Note entity) {
        if ( entity == null ) {
            return null;
        }

        NoteResponse.NoteResponseBuilder noteResponse = NoteResponse.builder();

        noteResponse.id( entity.getId() );
        noteResponse.content( entity.getContent() );
        noteResponse.createdAt( entity.getCreatedAt() );
        noteResponse.updatedAt( entity.getUpdatedAt() );

        return noteResponse.build();
    }

    @Override
    public void updateEntity(NoteRequest request, Note entity) {
        if ( request == null ) {
            return;
        }

        entity.setContent( request.getContent() );
    }
}
