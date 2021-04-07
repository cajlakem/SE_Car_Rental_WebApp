package fh.se.car.rental.fh.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import fh.se.car.rental.fh.controller.helper.JwtResponse;
import fh.se.car.rental.fh.exceptions.CredentialsWrong;
import fh.se.car.rental.fh.exceptions.LoiginFail;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserController userController;

  @Test
  void testCreateUser_whenReenteredPasswordIsEmpty_throwsException() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    //when
    CredentialsWrong myCaughtException = assertThrows(
      CredentialsWrong.class,
      () -> userController.createUser(testUser, "")
    );
    //then
    assertEquals("Check the password!  != testLOL", myCaughtException.getMessage());
  }

  @Test
  void testCreateUser_whenReenteredPasswordIsDifferent_throwsException() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    //when
    CredentialsWrong myCaughtException = assertThrows(
      CredentialsWrong.class,
      () -> userController.createUser(testUser, "otherPassword")
    );
    //then
    assertEquals(
      "Check the password! otherPassword != testLOL",
      myCaughtException.getMessage()
    );
  }

  @Test
  void testCreateUser_whenUserNameAlreadyExists_throwsException() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    given(userRepository.findByUserName("joker")).willReturn(testUser);
    //when
    UsernameAlreadyInUse myCaughtException = assertThrows(
      UsernameAlreadyInUse.class,
      () -> userController.createUser(testUser, "testLOL")
    );
    //then
    assertEquals("joker already in use!", myCaughtException.getMessage());
  }

  @Test
  void testCreateUser_whenUserNameDoesNotExist_savesUser() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    given(userRepository.findByUserName("joker")).willReturn(null);
    //when
    userController.createUser(testUser, "testLOL");
    //then
    verify(userRepository).save(testUser);
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
    User firstNewUser = new User(
      1L,
      "joker",
      "April",
      "Fool",
      "testLOL",
      true,
      "april@fool"
    );
    given(userRepository.findById(1L)).willReturn(java.util.Optional.of(firstNewUser));
    //when
    User testUser = userController.getUser(1L);
    //then
    assertEquals(firstNewUser, testUser);
  }

  @Test
  void testGetUser_whenUserDoesNotExists_throwsException() {
    //given
    given(userRepository.findById(3L)).willReturn(Optional.empty());
    //when
    RecordNotFoundException myCaughtException = assertThrows(
      RecordNotFoundException.class,
      () -> userController.getUser(3L)
    );
    //then
    assertEquals("3", myCaughtException.getMessage());
  }

  @Test
  void testModifyUser_whenUserExists_modifiesUsersEmail() {
    //given
    User firstNewUser = new User(
      1L,
      "joker",
      "April",
      "Fool",
      "testLOL",
      true,
      "april@fool"
    );
    User secondNewUser = new User(
      2L,
      "tester",
      "Test",
      "Man",
      "test1234",
      true,
      "test@man"
    );
    given(userRepository.findById(1L)).willReturn(java.util.Optional.of(firstNewUser));
    given(userRepository.save(firstNewUser)).willReturn(firstNewUser);
    //when
    User testUser = userController.modifyUser(secondNewUser, 1L);
    //then
    assertEquals("test@man", testUser.getEmail());
  }

  @Test
  void testModifyUser_whenUserDoesNotExist_savesUser() {
    //given
    User secondNewUser = new User(
      2L,
      "tester",
      "Test",
      "Man",
      "test1234",
      true,
      "test@man"
    );
    given(userRepository.findById(1L)).willReturn(Optional.empty());
    //when
    User testUser = userController.modifyUser(secondNewUser, 1L);
    //then
    verify(userRepository).save(secondNewUser);
  }

  @Test
  void testLogin_whenUserDoesNotExist_throwsException() {
    //given
    given(userRepository.findByUserName("joker")).willReturn(null);
    //when
    RecordNotFoundException myCaughtException = assertThrows(
      RecordNotFoundException.class,
      () -> userController.login("joker", "testLOL")
    );
    //then
    assertEquals("joker not found!", myCaughtException.getMessage());
  }

  @Test
  void testLogin_whenPasswordIsIncorrect_throwsException() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    given(userRepository.findByUserName("joker")).willReturn(testUser);
    //when
    LoiginFail myCaughtException = assertThrows(
      LoiginFail.class,
      () -> userController.login("joker", "wrongPassword")
    );
    //then
    assertEquals("Wrong credentials!", myCaughtException.getMessage());
  }

  @Test
  void testLogin_whenUserEntersCorrectCredentials_generatesToken() {
    //given
    User testUser = new User(1L, "joker", "April", "Fool", "testLOL", true, "april@fool");
    given(userRepository.findByUserName("joker")).willReturn(testUser);
    //when
    JwtResponse token = userController.login("joker", "testLOL");
    //then
    assertNotEquals(null, token);
  }
}
