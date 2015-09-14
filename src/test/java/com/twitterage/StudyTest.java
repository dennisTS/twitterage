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

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@SpringApplicationConfiguration(classes = {WebConfig.class, RootConfig.class})
public class StudyTest {

    private static final String ACCESS_TOKEN = "3632195477-V6FeZXDtP1HwfCbBfQGBO46fA104SoXeXEQuZAp";
    private static final String ACCESS_SECRET = "AZbiF1Xfa7G5jF48xxlCv6VTLYrEskg3hNiaQDkrPmtge";
    private static final String CONSUMER_KEY= "K4F39xKxLPmpd9JOqTBZsB4Eq";
    private static final String CONSUMER_SECRET = "9vYBNBFEM9WNuykoTJzFkhcXV5FQC2OoNZE7bTsHxVnA2an3yf";

    @Value("${jasypt.password}")
    private String password;

    @Value("${twitter.consumer.key}")
    private String consumerKey;
    @Value("${twitter.consumer.secret}")
    private String consumerSecret;

    @Autowired
    Environment env;

  //  @Test
    public void shouldReturnSomething() {
        //Twitter twitter = new TwitterTemplate(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_SECRET);

        //String sth = twitter.userOperations().getUserProfile("patrolpoliceua").getProfileImageUrl().toString();

        System.out.println(env.getProperty("twitter.consumer.key"));
    }

}
