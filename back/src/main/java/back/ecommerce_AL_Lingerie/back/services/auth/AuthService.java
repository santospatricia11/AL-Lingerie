package back.ecommerce_AL_Lingerie.back.services.auth;


import back.ecommerce_AL_Lingerie.back.dto.SignupRequest;
import back.ecommerce_AL_Lingerie.back.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest signupRequest);

	Boolean hasUserWithEmail(String email);

	void createAdminAccount();
}
