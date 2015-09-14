package com.twitterage.collage;

import com.twitterage.image.ComparableImage;
import com.twitterage.model.Size;
import com.twitterage.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        BufferedImage collage = generateCollage(comparableImages);
        //TODO test when called without parameters set
        return images.get(0);
    }

    private BufferedImage generateCollage(List<ComparableImage> comparableImages) {
        //??????
        return null;
    }

    private List<ComparableImage> convertToComparableImages(List<BufferedImage> images) {
        List<ComparableImage> resultList = new ArrayList<>();

        for (BufferedImage image : images)
            resultList.add(new ComparableImage(image));

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
