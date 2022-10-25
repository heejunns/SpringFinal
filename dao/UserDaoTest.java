package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void  addAndSelect(){
        UserDao userDao = new UserDao(new AWSUserMaker());
        String id = "100";
        userDao.add(new User(id,"hihi","123456"));

        User user = userDao.findById(id);
        System.out.println(user.getPassword());

        Assertions.assertEquals("hihi",user.getName());
    }

}