package org.example.tree.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.RequestPart;
>>>>>>> Stashed changes
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class PostRequestDTO {

    @Getter
    public static class createPost {
        private String content;
        List<MultipartFile> images;
    }

    @Getter
    public static class updatePost {
        private String content;
    }
}
