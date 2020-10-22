package com.example.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AlbumController {

    Logger LOG = LoggerFactory.getLogger(AlbumController.class);

    private AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public Mono<String> getAlbums() {
        return albumService.getAlbumMono();
    }


}




