import api from "./api";
import TokenService from "./token.service";

class AuthService {
  login({ email, password }) {
    return api
      .post("/user/login", {
        email,
        password
      })
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }

  logout() {
    TokenService.removeUser();
  }

  register({ fullName, email, password }) {
    return api.post("/user", {
      fullName,
      email,
      password
    });
  }

  verifyEmail({ verification }){
    return api.post("/user/verify-email", {
      verification: Number(verification)
    });
  }

  forgotPassword({ email }){
    return api.post("/user/forgot-password", {
      email
    });
  }

  forgotPasswordVerify({ verification }){
    return api.post("/user/forgot-password-verify", {
      verification: Number(verification)
    });
  }

  resetPassword({ email, password }){
    return api.post("/user/reset-password", {
      email,
      password
    });
  }
}

export default new AuthService();
