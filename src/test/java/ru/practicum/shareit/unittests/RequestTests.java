package ru.practicum.shareit.unittests;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;

import javax.persistence.EntityManager;

//@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestTests {

    private final EntityManager em;

    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ItemService itemService;

//    @Test
//    void requestServiceSaveRequestOk() {
//
////        User user = userService.addUser(new UserDto("user1", "user1@gmail.com"));
////        // given
////        RequestDto requestDto = new RequestDto("description");
////
////        Request request = requestService.addRequest(user.getId(), requestDto);
////
//////        UserDto userDto = makeUserDto("some@email.com", "Пётр", "Иванов");
////
////        // when
//////        service.saveUser(userDto);
////
////        // then
//////        TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email", User.class);
////        TypedQuery<Request> query = em.createQuery("Select r from Request r where r.description = :description", Request.class);
////        Request request1 = query.setParameter("description", requestDto.getDescription()).getSingleResult();
//////
//////        assertThat(request1.getId(), notNullValue());
////        assertEquals(request1.getUserId(), user.getId());
////        assertThat(user.getFirstName(), equalTo(userDto.getFirstName()));
////        assertThat(user.getLastName(), equalTo(userDto.getLastName()));
////        assertThat(user.getEmail(), equalTo(userDto.getEmail()));
////        assertThat(user.getState(), equalTo(userDto.getState()));
////        assertThat(user.getRegistrationDate(), notNullValue());
//    }
////
////    @Test
////    void saveRequestWrongUser() {
////
//////        User user = userService.addUser(new UserDto("user1", "user1@gmail.com"));
//////
//////        RequestDto requestDto = new RequestDto("description");
//////
//////        // после исполнения блока ошибка попадёт в переменную exception
//////        final NotFoundException exception = assertThrows(
//////
//////                // класс ошибки
//////                NotFoundException.class,
//////
//////                // создание и переопределение экземпляра класса Executable
//////                new Executable() {
//////                    @Override
//////                    public void execute() {
//////                        // здесь блок кода, который хотим проверить
//////                        // при делении на 0 ожидаем ArithmeticException
//////                        requestService.addRequest(Long.valueOf(10), requestDto);
//////                    }
//////                });
////    }
////
////
//////    @Test
//////   @Transactional
//////    void findByUser() {
//////
////////        User user1 = userService.addUser(new UserDto("user1", "user1@gmail.com"));
////////        User user2 = userService.addUser(new UserDto("user2", "user2@gmail.com"));
////////        // given
////////        RequestDto requestDto1 = new RequestDto("description1");
////////        Request request1 = requestService.addRequest(user1.getId(), requestDto1);
////////
////////        RequestDto requestDto2 = new RequestDto("description2");
////////        Request request2 = requestService.addRequest(user1.getId(), requestDto2);
////////
////////        Item item= itemService.add(user2.getId(), new ItemDto(null, "патрубок воздуховода", "патрубок воздуховода", true, request1.getId()));
////////
////////        List<Request> all = requestRepository.findAll();
////////        var tesItems = all.get(0).getItems();
////////        System.out.println(all);
////////
////////        Request request3 = requestRepository.findById(Long.valueOf(1)).get();
////////        List<Item> items = request3.getItems();
////////        Hibernate.initialize(request3.getItems());
////////        List<RequestDto2> requestList = requestService.findRequests(user1.getId());
////////        for (Request request : requestList) {
////////            request.getItems();
//////        }
//////
//////    }
////
////    @Test
////    @Transactional
////    void findAll() {
////
//////        User user1 = userService.addUser(new UserDto("user1", "user1@gmail.com"));
//////        User user2 = userService.addUser(new UserDto("user2", "user2@gmail.com"));
//////
//////        RequestDto requestDto1 = new RequestDto("description1");
//////        Request request1 = requestService.addRequest(user1.getId(), requestDto1);
//////
//////        RequestDto requestDto2 = new RequestDto("description2");
//////        Request request2 = requestService.addRequest(user1.getId(), requestDto2);
//////        var requests = requestRepository.findAll();
//////
//////        var from = 1;
//////        var size = 10;
//////        Pageable pageable = (Pageable) PageRequest.of(from, size, Sort.by("created"));
//////        var requests2 = requestRepository.findAll(pageable);
////    }
//
//    @Test
//    @Transactional
//    void pagTest() {
////
////        User user1 = userService.addUser(new UserDto("user1", "user1@gmail.com"));
////        User user2 = userService.addUser(new UserDto("user2", "user2@gmail.com"));
////        User user3 = userService.addUser(new UserDto("user3", "user3@gmail.com"));
////        User user4 = userService.addUser(new UserDto("user4", "user4@gmail.com"));
////        User user5 = userService.addUser(new UserDto("user5", "user5@gmail.com"));
////        User user6 = userService.addUser(new UserDto("user6", "user6@gmail.com"));
////
////        var from = 2;
////        var size = 2;
////
////        var users = userRepository.findAll(PageRequest.of(from, 1));
//
//
//    }

}
