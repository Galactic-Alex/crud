package com.alex.crud;

import com.alex.crud.service.UserService;
import com.alex.crud.service.UserServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CrudApplicationTests {

    @Mock
    UserService userService;

    @Test
    void contextLoads() {
    }

    @Test()
    void givenDeleteByIdTwo_whenUserServiceDeleting_resultIsNull() {
        Mockito.when(userService.findUserById(2L)).thenThrow(UserServiceException.class);
        userService.deleteById(2L);
        Assertions.assertThrowsExactly(UserServiceException.class, () -> userService.findUserById(2L));
    }

}
