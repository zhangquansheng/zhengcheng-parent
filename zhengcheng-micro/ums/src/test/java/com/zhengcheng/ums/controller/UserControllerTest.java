package com.zhengcheng.ums.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * UserControllerTest
 *
 * @author quansheng1.zhang
 * @since 2022/7/2 0:24
 */
@Slf4j
@AutoConfigureMockMvc
class UserControllerTest extends com.zhengcheng.ums.controller.BaseTest {

    private MockMvc               mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void page() {
    }

    @Test
    void menu() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void enable() {
    }

    @Test
    void checkUsername() {
    }

    @Test
    void submit() {
    }

    @Test
    void login() {
    }

    @Test
    void info() {
    }

    @Test
    void logout() {
    }
}
