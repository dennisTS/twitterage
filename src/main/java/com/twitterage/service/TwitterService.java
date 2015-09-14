package com.twitterage.service;

import com.twitterage.image.ImageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Component
public class TwitterService {

    @Autowired
    private Twitter twitter;

    public TwitterService() {

    }

    public TwitterService(Twitter twitter) {
        this.twitter = twitter;
    }

    public List<BufferedImage> getProfileImagesForUserFollowings(String screenName, boolean scaled) {

        List<TwitterProfile> followings = getFollowingsForProfile(screenName);
        int totalFollowingsStatusCount = getTotalFollowingsStatusCount(followings);

        List<BufferedImage> followingsImages = new ArrayList<>();
        for (TwitterProfile followingProfile : followings) {
            BufferedImage profileImage = loadImageForProfile(followingProfile);

            if (scaled) {
                profileImage = scaleImageByStatusCount(profileImage,
                        followingProfile.getStatusesCount(),
                        totalFollowingsStatusCount);
            }

            followingsImages.add(profileImage);
        }

        return followingsImages;
    }

    private int getTotalFollowingsStatusCount(List<TwitterProfile> followings) {
        int count = 0;

        for (TwitterProfile profile : followings)
            count += profile.getStatusesCount();

        return count;
    }

    private BufferedImage scaleImageByStatusCount(BufferedImage image, double statusCount, double totalStatusCount) {
        if (image == null)
            return null;

        return ImageProcessor.resizeToPercent(image, statusCount / totalStatusCount);
    }

    private BufferedImage loadImageForProfile(TwitterProfile profile) {
        if (profile == null)
            return null;

        String profileImageUrl = profile.getProfileImageUrl();

        if(profileImageUrl == null || profileImageUrl.isEmpty())
            return null;

        return ImageProcessor.downloadImage(profileImageUrl);
    }

    private List<TwitterProfile> getFollowingsForProfile(String screenName) {
        return twitter.friendOperations().getFriends(screenName);
    }

    public Twitter getTwitter() {
        return this.twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
