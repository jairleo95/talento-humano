package com.app.recruitment.web;
import com.app.recruitment.domain.DgpDocument;
import com.app.recruitment.web.dto.DgpDocumentRequest;
import com.app.recruitment.web.dto.DgpDocumentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.Instant;
@Mapper(componentModel = "spring", imports = {Instant.class})
public interface DgpDocumentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    DgpDocument toEntity(DgpDocumentRequest request);
    DgpDocumentResponse toResponse(DgpDocument entity);
}