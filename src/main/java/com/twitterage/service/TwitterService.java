package com.twitterage.service;

import com.twitterage.image.ImageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

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
        int maxFollowingStatusCount = getMaxFollowingStatusCount(followings);

        List<BufferedImage> followingsImages = new ArrayList<>();
        for (TwitterProfile followingProfile : followings) {
            BufferedImage profileImage = loadImageForProfile(followingProfile);

            if (scaled) {
                profileImage = scaleImageByStatusCount(profileImage,
                        followingProfile.getStatusesCount(),
                        maxFollowingStatusCount);
            }

            followingsImages.add(profileImage);
        }

        return followingsImages;
    }

    private int getMaxFollowingStatusCount(List<TwitterProfile> followings) {
        int maxCount = 0;

        for (TwitterProfile profile : followings) {
            if (profile.getStatusesCount() > maxCount)
                maxCount = profile.getStatusesCount();
        }

        return maxCount;
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

        return ImageProcessor.downloadImage(profileImageUrl.replaceAll("_normal", "_400x400"));
    }

    private List<TwitterProfile> getFollowingsForProfile(String screenName) {
        CursoredList<TwitterProfile> list = twitter.friendOperations().getFriends(screenName);

        List<TwitterProfile> result = new ArrayList<>(list);
        if (list.hasNext())
            result.addAll(twitter.friendOperations().getFriendsInCursor(screenName, list.getNextCursor()));

        return result;
    }

    public Twitter getTwitter() {
        return this.twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
