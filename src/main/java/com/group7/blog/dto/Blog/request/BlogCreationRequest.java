package com.group7.blog.dto.Blog.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogCreationRequest {
    @NotEmpty(message = "Title is not empty!")
    String title;

    String content;
    String thumbnail;
    String tagName;
}
