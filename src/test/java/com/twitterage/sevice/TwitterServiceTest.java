package com.twitterage.sevice;

import com.twitterage.image.ImageProcessor;
import com.twitterage.service.TwitterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.context.request.FacesRequestAttributes;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageProcessor.class)
public class TwitterServiceTest {

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;

    private static final String USER_SCREEN_NAME = "test_user";
    private static final String IMAGE_URL = "http://some.img";

    private Twitter twitter = PowerMockito.mock(Twitter.class);
    private TwitterProfile twitterProfile = PowerMockito.mock(TwitterProfile.class);
    private FriendOperations friendOperations = PowerMockito.mock(FriendOperations.class);
    @InjectMocks private TwitterService twitterService;

    private BufferedImage image1;

    @Before
    public void setUp() {
        image1 = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnDownloadedProfileImgs() {
        PowerMockito.mockStatic(ImageProcessor.class);

        List<Image> expImgList = new ArrayList<>();
        expImgList.add(image1);

        CursoredList<TwitterProfile> profileList = new CursoredList<>(0, 0, 0);
        profileList.add(twitterProfile);

        when(twitter.friendOperations()).thenReturn(friendOperations);
        when(friendOperations.getFriends(USER_SCREEN_NAME)).thenReturn(profileList);

        when(twitterProfile.getProfileImageUrl()).thenReturn(IMAGE_URL);
        when(ImageProcessor.downloadImage(IMAGE_URL)).thenReturn(image1);

        List<Image> actImgList = twitterService.getProfileImagesForUserFollowings(USER_SCREEN_NAME, false);

        assertEquals(expImgList, actImgList);
    }
}
