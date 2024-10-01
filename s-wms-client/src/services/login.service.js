import axios from "axios";
import router from '@/router';

const serverUrl = 'http://localhost:7777';
const clientId = 'test-client';
const authHeaderValue = 'Basic dGVzdC1jbGllbnQ6dGVzdC1jbGllbnQ=';
const redirectUri = 'http://localhost:8080/code';

const ACCESS_TOKEN_KEY = "access_token";
const REFRESH_TOKEN_KEY = "refresh_token";

export default {
    login() {
        let requestParams = new URLSearchParams({
            response_type: "code",
            client_id: clientId,
            redirect_uri: redirectUri,
            scope: 'SSO.USER_PROFILE_INFO SSO.USER_AVATAR SSO.USER_IDENTIFICATION SSO.USER_AUTHORITIES'
        });
        window.location = serverUrl + "/oauth2/authorize?" + requestParams;
    },

    refreshToken() {
        let payload = new FormData()
        payload.append('grant_type', 'refresh_token')
        payload.append('refresh_token', localStorage.getItem(REFRESH_TOKEN_KEY))

        return axios.post(serverUrl + '/oauth2/token', payload, {
                headers: {
                    'Content-type': 'application/url-form-encoded',
                    'Authorization': authHeaderValue
                }
            }
        ).then(response => {
            localStorage.setItem(ACCESS_TOKEN_KEY, response.data[ACCESS_TOKEN_KEY]);
        }).catch(error => {
            router.push('/auth/login');
        })
    },

    getTokens(code) {
        let payload = new FormData()
        payload.append('grant_type', 'authorization_code')
        payload.append('code', code)
        payload.append('redirect_uri', redirectUri)
        payload.append('client_id', clientId)

        return axios.post(serverUrl + '/oauth2/token', payload, {
                headers: {
                    'Content-type': 'application/url-form-encoded',
                    'Authorization': authHeaderValue
                }
            }
        ).then(response => {
            localStorage.setItem(ACCESS_TOKEN_KEY, response.data[ACCESS_TOKEN_KEY])
            localStorage.setItem(REFRESH_TOKEN_KEY, response.data[REFRESH_TOKEN_KEY])
            localStorage.setItem('loggedIn', "true")
            router.push({ name: 'Dashboard' });
        }).catch(error => {
            console.log('error getting tokens', error.response.data);
        })
    },

    getTokenInfo() {
        let payload = new FormData();
        payload.append('token', localStorage.getItem(ACCESS_TOKEN_KEY));

        return axios.post(serverUrl + '/oauth2/token-info', payload, {
            headers: {
                'Authorization': authHeaderValue
            }
        });
    },

    logout() {
        localStorage.removeItem("loggedIn");
    }
}