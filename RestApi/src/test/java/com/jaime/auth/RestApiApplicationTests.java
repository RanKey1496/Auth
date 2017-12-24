package com.jaime.auth;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class RestApiApplicationTests {

	@Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
	
    private String token;
    
    @Before
    public void setup() throws ClientProtocolException, IOException, ParseException {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://jaimetest:test@localhost:8081/oauth/token");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("username", "admin.admin"));
        params.add(new BasicNameValuePair("password", "jwtpass"));
        params.add(new BasicNameValuePair("grant_type", "password"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
           //Parse our JSON response               
           JSONParser j = new JSONParser();
           JSONObject o = (JSONObject)j.parse(line);
           Map r = (Map)o.get("response");

           System.out.println(r.toString());
        }
    }
    
	@Test
	public void contextLoads() throws Exception {
		mvc.perform(get("/service/hola")).andExpect(status().is4xxClientError()).andReturn();
	}
	
	@Test
	@WithMockUser(authorities = {"ADMIN_USER"} )
	public void getMessageAuthenticated() throws Exception {
		mvc.perform(get("/service/time")).andDo(print());
	}

}
