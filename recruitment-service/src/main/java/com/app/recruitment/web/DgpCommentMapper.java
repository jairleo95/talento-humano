package com.app.recruitment.web;
import com.app.recruitment.domain.DgpComment;
import com.app.recruitment.web.dto.DgpCommentRequest;
import com.app.recruitment.web.dto.DgpCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.Instant;
@Mapper(componentModel = "spring", imports = {Instant.class})
public interface DgpCommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    DgpComment toEntity(DgpCommentRequest request);
    DgpCommentResponse toResponse(DgpComment entity);
}