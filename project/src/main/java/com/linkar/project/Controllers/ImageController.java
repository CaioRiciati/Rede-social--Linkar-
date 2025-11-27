package com.linkar.project.Controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + "/Desktop/uploads_posts/";

    @GetMapping("/imagens/{nome}")
    public ResponseEntity<Resource> getImagem(@PathVariable String nome) {
        try {
            Path caminho = Paths.get(UPLOAD_DIR + nome);

            if (!Files.exists(caminho)) {
                return ResponseEntity.notFound().build();
            }

            byte[] bytes = Files.readAllBytes(caminho);
            ByteArrayResource resource = new ByteArrayResource(bytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nome + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
