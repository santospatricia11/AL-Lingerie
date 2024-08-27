package back.ecommerce_AL_Lingerie.back.services.auth;

import back.ecommerce_AL_Lingerie.back.dto.SignupRequest;
import back.ecommerce_AL_Lingerie.back.dto.UserDto;
import back.ecommerce_AL_Lingerie.back.enums.OrderStatus;
import back.ecommerce_AL_Lingerie.back.enums.UserRole;
import back.ecommerce_AL_Lingerie.back.model.Order;
import back.ecommerce_AL_Lingerie.back.model.User;
import back.ecommerce_AL_Lingerie.back.repository.OrderRepository;
import back.ecommerce_AL_Lingerie.back.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.annotation.PostConstruct;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bEncoder;
    private final OrderRepository orderRepository;

    public UserDto createUser(SignupRequest signupRequest) {
        log.info("Creating user with email: {}", signupRequest.getEmail());

        User createdUser = userRepository.save(User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(bEncoder.encode(signupRequest.getPassword()))
                .role(UserRole.CUSTOMER)
                .build());

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        log.info("Running application for the first time creates an Admin account with default info");
        Optional<User> adminAccountUser = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccountUser.isEmpty()) {
            log.info("Admin account created with email: admin@gmail.com and password: admin");
            userRepository.save(
                    User.builder()
                            .email("admin@gmail.com")
                            .name("admin")
                            .role(UserRole.ADMIN)
                            .password(bEncoder.encode("admin"))
                            .build()
            );
        }
    }

}
