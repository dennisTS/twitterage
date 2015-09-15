package com.twitterage.controller;

import com.twitterage.collage.CollageCreator;
import com.twitterage.configuration.WebConfig;
import com.twitterage.image.ImageProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {WebConfig.class})
public class HomeControllerTest {

    public static final String URL_TEMPLATE = "/";
    public static final String EXPECTED_VIEW_NAME = "index";
    public static final String EXPECTED_URL = "/WEB-INF/views/index.jsp";

    public static final String URL_TEMPLATE_COLLAGE = "/collage";

    public static final String USERNAME_PARAM = "username";
    public static final String SIZE_PARAM = "size";
    public static final String USERNAME = "test_username";
    public static final String SIZE = "400x400";
    public static final String TEST_SIZE = "100x100";

    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;
    private BufferedImage image;

    @Mock
    private CollageCreator collageCreator;
    @InjectMocks
    private HomeController controller;

    @Before
    public void setUp() {
        initMocks(this);

        image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void shouldReturnStatusOkAtHome() throws Exception {
        mockMvc = webAppContextSetup(context).build();

        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(view().name(EXPECTED_VIEW_NAME))
                .andExpect(forwardedUrl(EXPECTED_URL));
    }

    @Test
    public void shouldReturnImageAtCollage() throws Exception {
        mockMvc = standaloneSetup(controller).build();

        when(collageCreator.generateCollage(anyBoolean())).thenReturn(image);
        String expResult = ImageProcessor.imageToBase64String(image);

        MvcResult result = mockMvc.perform(post(URL_TEMPLATE_COLLAGE)
                .param(USERNAME_PARAM, USERNAME)
                .param(SIZE_PARAM, SIZE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andReturn();

        String actResult = result.getResponse().getContentAsString();

        assertEquals(expResult, actResult);
    }
}
