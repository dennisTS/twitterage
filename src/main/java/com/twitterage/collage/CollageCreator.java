package com.twitterage.collage;

import com.twitterage.image.ComparableImage;
import com.twitterage.model.Size;
import com.twitterage.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollageCreator {

    private String username;
    private Size size;

    @Autowired
    TwitterService twitterService;

    public CollageCreator() {
    }

    public CollageCreator(String username, Size size) {
        this.username = username;
        this.size = size;
    }

    public BufferedImage generateCollage(boolean scaled) {
        if (username == null || username.isEmpty())
            return null;

        List<BufferedImage> images = twitterService.getProfileImagesForUserFollowings(getUsername(), scaled);

        if (images == null || images.isEmpty())
            return null;

        List<ComparableImage> comparableImages = convertToComparableImages(images);
        Collections.sort(comparableImages);

        return generateCollage(comparableImages);
    }

    private BufferedImage generateCollage(List<ComparableImage> comparableImages) {
        Collage collage = new Collage(comparableImages);

        return collage.getCollageAsImage();
    }

    private List<ComparableImage> convertToComparableImages(List<BufferedImage> images) {
        List<ComparableImage> resultList = images.stream().map(ComparableImage::new).collect(Collectors.toList());

        return resultList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
