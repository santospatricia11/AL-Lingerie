package back.ecommerce_AL_Lingerie.back.service.auth;

import com.aryan.ecom.dto.SignupRequest;
import com.aryan.ecom.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest signupRequest);

	Boolean hasUserWithEmail(String email);

	void createAdminAccount();
}
