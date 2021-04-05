package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        //TODO
    }

    @Test
    void testQueryAllUsers_whenUsersExist_returnsAllUsers() {
        //given
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool"));
        userList.add(new User(2L, "tester", "Test", "Man", "test1234", true, "test@man"));
        given(userRepository.findAll()).willReturn(userList);
        //when
        List<User> testList = userController.queryAllUsers();
        //then
        assertEquals(userList, testList);
    }

    @Test
    void testGetUser_whenUserExists_returnsUser() {
        //given
        List<User> userList = new ArrayList<>();
        User firstNewUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
        User secondNewUser = new User(2L, "tester", "Test", "Man", "test1234", true, "test@man");
        userList.add(firstNewUser);
        userList.add(secondNewUser);
        given(userRepository.findById(1L)).willReturn(java.util.Optional.of(firstNewUser));
        //when
        User testUser = userController.getUser(1L);
        //then
        assertEquals(firstNewUser, testUser);
    }

    @Test
    void testGetUser_whenUserDoesNotExists_throwsException() {
        //given
        List<User> userList = new ArrayList<>();
        User firstNewUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
        User secondNewUser = new User(2L, "tester", "Test", "Man", "test1234", true, "test@man");
        userList.add(firstNewUser);
        userList.add(secondNewUser);
        given(userRepository.findById(3L)).willReturn(Optional.empty());
        //when
        RecordNotFoundException myCaughtException = assertThrows(RecordNotFoundException.class, () -> userController.getUser(3L));
        //then
        assertEquals("3", myCaughtException.getMessage());
    }

    @Test
    void testModifyUser_whenUserExists_modifiesUsersEmail() {
        //given
        List<User> userList = new ArrayList<>();
        User firstNewUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
        User secondNewUser = new User(2L, "tester", "Test", "Man", "test1234", true, "test@man");
        userList.add(firstNewUser);
        userList.add(secondNewUser);
        given(userRepository.findById(1L)).willReturn(java.util.Optional.of(firstNewUser));
        //when
        User testUser = userController.modifyUser(secondNewUser, 1L);
        //then
        //assertEquals("test@man", testUser.getEmail());
        //TODO: something here is not right
    }

    @Test
    void deleteUser() {
        //TODO
    }

    @Test
    void login() {
        //TODO
    }
}