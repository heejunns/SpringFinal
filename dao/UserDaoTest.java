package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    @Test
    void  addAndSelect(){
        UserDao userDao = new UserDaoFactory().awsUserDao();
        String id = "130";
        userDao.add(new User(id,"hihi","123456"));

        User user = userDao.findById(id);
        System.out.println(user.getPassword());
        Assertions.assertEquals("130",user.getId());
        Assertions.assertEquals("hihi",user.getName());
        Assertions.assertEquals("123456",user.getPassword());

    }

}