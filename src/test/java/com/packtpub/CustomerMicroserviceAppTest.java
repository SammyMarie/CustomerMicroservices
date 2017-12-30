package com.packtpub;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerMicroserviceAppTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp(){

    }

    @Test
    public void checkContextIsNotNull(){
        assertThat(applicationContext, is(notNullValue()));
    }
}