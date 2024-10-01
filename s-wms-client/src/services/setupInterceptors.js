import axiosInstance from "./api";
import LoginService from './login.service'

const ACCESS_TOKEN_KEY = "access_token";

const setup = () => {

  axiosInstance.interceptors.request.use(request => {
    const token = localStorage.getItem(ACCESS_TOKEN_KEY)
    if (token) {
      request.headers['Authorization'] = `Bearer ${token}`;
    }
    return request;
  }, error => {
    return Promise.reject(error);
  });

  axiosInstance.interceptors.response.use(
      (res) => {
        return res;
      },
      async (err) => {
        const originalConfig = err.config;

        // Access Token was expired
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;

          try {
            await LoginService.refreshToken();
            return axiosInstance(originalConfig);
          } catch (_error) {
            return Promise.reject(_error);
          }
        }

        return Promise.reject(err);
      }
  );
};

export default setup;