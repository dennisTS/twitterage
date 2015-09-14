package com.twitterage.controller;

import com.twitterage.collage.CollageCreator;
import com.twitterage.image.ImageProcessor;
import com.twitterage.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.image.BufferedImage;
import org.apache.commons.codec.binary.Base64;

@Controller
public class HomeController {

    @Autowired
    CollageCreator collageCreator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String home() {
        return "index";
    }

    @RequestMapping(value = "/collage", method = RequestMethod.POST)
    ResponseEntity<String> collage(@RequestParam("username") String username,
                    @RequestParam("size") String size) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        collageCreator.setUsername(username);
        collageCreator.setSize(new Size(size));
        final BufferedImage collage = collageCreator.generateCollage(false);

        final String responseStr = ImageProcessor.imageToBase64String(collage);

        return new ResponseEntity<>(responseStr, headers, HttpStatus.CREATED);
    }
}
