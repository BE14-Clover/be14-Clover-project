package com.clover.moodiary.api.sticker;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StickerService {

    public List<String> getStickerFilenames() {
        try {
            File folder = new ClassPathResource("static/stickers").getFile();
            return Arrays.stream(folder.listFiles())
                    .filter(file -> file.isFile())
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("스티커 폴더 접근 실패", e);
        }
    }
}