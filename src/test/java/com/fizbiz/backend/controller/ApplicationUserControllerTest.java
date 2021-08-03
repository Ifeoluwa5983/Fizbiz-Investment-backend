package com.fizbiz.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;
    ApplicationUser applicationUser;


    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        applicationUser = new ApplicationUser();
    }

    @Test
    void testCreateApplicationUserEndpoint_thenReturnOK() throws Exception {

        applicationUser.setFirstName("Ife");
        applicationUser.setLastName("Femi");
        applicationUser.setToken("3853");
        applicationUser.setGender(Gender.Male);
        applicationUser.setPassword("password");
        applicationUser.setPhoneNumber("08033995588");

        this.mockMvc.perform(post("/api/user/register")
                .contentType("application/json")
//                .content(mapper.writeValueAsString(applicationUser)))
                        .content(mapper.writeValueAsString(applicationUser)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

//    @Test
//    void testThatWeCanCallTheFindUserByIdEndPoint() throws Exception {
//
//        this.mockMvc.perform(get("/api/user/60da41555e541c35851e57c1")
//                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sd2FoQGdtYWlsLmNvbSIsImV4cCI6MTYyNTgyMjc2N30.0ZsEjXeYxTrfW4yF6qCU9ng8Qil4JCWOvcATo_HLDvIJnxKVQNZ81X-i72MV2fK_KNQBFthlV50idGLZc5EoMw"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void testThatWeCanFindAllUsers() throws Exception {
//        this.mockMvc.perform(get("/api/user/").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sd2FoQGdtYWlsLmNvbSIsImV4cCI6MTYyNTgyMjc2N30.0ZsEjXeYxTrfW4yF6qCU9ng8Qil4JCWOvcATo_HLDvIJnxKVQNZ81X-i72MV2fK_KNQBFthlV50idGLZc5EoMw"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void  testThatAUserCanChangePassword() throws Exception {
//        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
//        changePasswordDto.setOldPassword("password");
//        changePasswordDto.setNewPassword("Ifeoluwa");
//        changePasswordDto.setUserId("60da41555e541c35851e57c1");
//
//        this.mockMvc.perform(post("/api/user/change-password").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sd2FoQGdtYWlsLmNvbSIsImV4cCI6MTYyNTgyMjc2N30.0ZsEjXeYxTrfW4yF6qCU9ng8Qil4JCWOvcATo_HLDvIJnxKVQNZ81X-i72MV2fK_KNQBFthlV50idGLZc5EoMw")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(changePasswordDto)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    void testThatWeCanCallUpdateUserEndpoint() throws Exception {
//        ApplicationUser updateProcurementPartyDto = new ApplicationUser();
//        updateProcurementPartyDto.setId("60da41555e541c35851e57c1");
//        updateProcurementPartyDto.setFirstName("Ifeoluwa");
//
//        this.mockMvc.perform(put("/api/user/update")
//                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sd2FoQGdtYWlsLmNvbSIsImV4cCI6MTYyNTgyMjc2N30.0ZsEjXeYxTrfW4yF6qCU9ng8Qil4JCWOvcATo_HLDvIJnxKVQNZ81X-i72MV2fK_KNQBFthlV50idGLZc5EoMw")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(updateProcurementPartyDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }
//
//    @Test
//    void testThatWeCanCallUSetPinEndpoint() throws Exception {
//        SetPinDto setPinDto = new SetPinDto();
//        setPinDto.setUserId("60e1ab775624d85de263ff05");
//        setPinDto.setPin("1234");
//
//        this.mockMvc.perform(post("/api/user/set-pin")
//                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sd2FoQGdtYWlsLmNvbSIsImV4cCI6MTYyNTgyMjc2N30.0ZsEjXeYxTrfW4yF6qCU9ng8Qil4JCWOvcATo_HLDvIJnxKVQNZ81X-i72MV2fK_KNQBFthlV50idGLZc5EoMw")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(setPinDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }
//
//    @Test
//    void testThatWeCanCallTheDeactivateUserByIdEndpoint() throws Exception {
//        this.mockMvc.perform(delete("/api/user/deactivate/604b6c362b93903cb17727b3")
//                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvbHV3YWhhbmV3QGdtYWlsLmNvbSIsImV4cCI6MTYxODQ4ODg1NX0.JlFFHXrdmR8Zjv42zfpr-x8MzWL7oQu65YxL_jDIT01FyTxFAy0GgyWDOCyt5U8v-cs32G4XCFXcNNE3xIiCAA"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void testThatUserCanLogIn () throws Exception {
//        LoginDto loginDto = new LoginDto();
//
//        loginDto.setEmailAddress("o.ifeoluwah@gmail.com");
//        loginDto.setPassword("Ife");
//
//        this.mockMvc.perform(post("/api/user/login")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(loginDto)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    void testThatUserCanCanBeVerified () throws Exception {
//
////        InternetAddress address = new InternetAddress();
////        address.setAddress("o.ifeoluwah@gmail.com");
//
//        UserVerificationDto userVerificationDto = new UserVerificationDto();
//        userVerificationDto.setEmail("o.ifeoluwah@gmail.com");
//
//        this.mockMvc.perform(post("/api/user/verify")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(userVerificationDto)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }

}