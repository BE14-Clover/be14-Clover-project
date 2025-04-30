// ✅ StickerController.java (Spring Boot)
package com.clover.moodiary.api.sticker;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class StickerController {

    @GetMapping("/stickers")
    public List<String> getStickerFileNames() throws IOException {
        Path stickerDir = new ClassPathResource("static/stickers").getFile().toPath();

        if (!Files.exists(stickerDir)) {
            return Collections.emptyList();
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(stickerDir)) {
            return StreamSupport.stream(stream.spliterator(), false)
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")
                            || name.endsWith(".svg") || name.endsWith(".gif"))
                    .map(name -> "/stickers/" + name) // ✅ URL 형태로 반환
                    .collect(Collectors.toList());
        }
    }
}