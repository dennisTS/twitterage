package com.twitterage.service;

import com.twitterage.image.ImageProcessor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TwitterService {


    private static Twitter twitter;

    public TwitterService(Twitter twitter) {
        this.twitter = twitter;
    }

    public List<Image> getProfileImagesForUserFollowings(String screenName, boolean scaled) {

        List<TwitterProfile> followings = getFollowingsForProfile(screenName);
        int totalFollowingsStatusCount = getTotalFollowingsStatusCount(followings);

        List<Image> followingsImages = new ArrayList<>();
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

        BufferedImage resultImage = ImageProcessor.resizeToPercent(image, statusCount / totalStatusCount);

        return resultImage;
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

}
