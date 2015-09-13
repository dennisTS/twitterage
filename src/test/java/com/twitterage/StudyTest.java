package com.twitterage;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class StudyTest {

    private static final String ACCESS_TOKEN = "3632195477-V6FeZXDtP1HwfCbBfQGBO46fA104SoXeXEQuZAp";
    private static final String ACCESS_SECRET = "AZbiF1Xfa7G5jF48xxlCv6VTLYrEskg3hNiaQDkrPmtge";
    private static final String CONSUMER_KEY= "K4F39xKxLPmpd9JOqTBZsB4Eq";
    private static final String CONSUMER_SECRET = "9vYBNBFEM9WNuykoTJzFkhcXV5FQC2OoNZE7bTsHxVnA2an3yf";


    @Value("twitter.access.token")
    private static String accessToken;
    @Value("twitter.access.secret")
    private static String accessSecret;
    @Value("twitter.consumer.key")
    private static String consumerKey;
    @Value("twitter.consumer.secret")
    private static String consumerSecret;

    public void shouldReturnSomething() {
        Twitter twitter = new TwitterTemplate(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_SECRET);

        String sth = twitter.userOperations().getUserProfile("patrolpoliceua").getProfileImageUrl().toString();

        System.out.println(sth);
    }

}
