package com.twitterage;

import com.twitterage.configuration.RootConfig;
import com.twitterage.configuration.WebConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StudyTest {

    @Test
    public void shouldReturnSomething() throws IOException {
        File file = new File("source.jpeg");
        System.out.println(file.getAbsolutePath().toString());

        BufferedImage image = ImageIO.read(new File("source.jpeg"));
        BufferedImage overlay = ImageIO.read(new File("target.jpeg"));

// create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w*2, h*2, BufferedImage.TYPE_INT_ARGB);

// paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, w, h, null);
        g.drawImage(image, w, 0, null);

// Save as new image
        ImageIO.write(combined, "PNG", new File("combined.png"));
    }

}
