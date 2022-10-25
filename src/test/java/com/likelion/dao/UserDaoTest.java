package com.likelion.dao;
import com.likelion.dao.AwsConnectionMaker;
import com.likelion.dao.ConnectionMaker;
import com.likelion.domain.User;
import org.assertj.core.api.AbstractBigIntegerAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.activation.DataHandler;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("10", "hi", "112233");
        this.user2 = new User("20", "hello", "123456");
        this.user3 = new User("30", "bye", "111333");
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.add(user2);
        User user = userDao.findById(user2.getId());

        assertEquals("hello", user.getName());
        assertEquals("123456", user.getPassword());
    }

    @Test
    @DisplayName("add 와 select 테스트")
    void addAndSelect() throws SQLException, ClassNotFoundException {
        user1 = new User("189", "who", "1233456");

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        User user = userDao.findById(user1.getId());
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    void findById() throws SQLException, ClassNotFoundException{
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("10");
        });
    }

}
