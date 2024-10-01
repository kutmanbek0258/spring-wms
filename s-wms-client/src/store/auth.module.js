import AuthService  from '../services/auth.service';
import router from '../router';

const user = JSON.parse(localStorage.getItem('user'));
const state = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

const actions = {
    login({ dispatch, commit }, { email, password }) {
        commit('loginRequest', { email });

        AuthService.login({email, password})
            .then(
                user => {
                    commit('loginSuccess', user);
                    router.push('/');
                },
                error => {
                    commit('loginFailure', error);
                    dispatch('alert/error', error.response.data.message, { root: true });
                }
            )
    },
    logout({ commit }) {
        AuthService.logout();
        commit('logout');
    },
    register({ dispatch, commit }, {fullName, email, password}) {
        commit('registerRequest');

        AuthService.register({fullName, email, password}).then(
            user => {
                commit('registerSuccess', user);
                router.push('/verify-email');
                setTimeout(() => {
                    dispatch('alert/success', 'Registration successful', { root: true });
                })
            },
            error => {
                commit('registerFailure', error);
                dispatch('alert/error', error.response.data.message, { root: true });
            }
        )
    },
    refreshToken({ commit }, accessToken) {
        commit('refreshToken', accessToken);
    },
    verifyEmail({ dispatch, commit }, { verification }){
        commit('verifyEmailRequest');

        AuthService.verifyEmail({ verification })
            .then(
                user => {
                    commit('verifyEmailSuccess', user);
                    router.push('/login');
                },
                error => {
                    commit('verifyEmailFailure', error);
                    dispatch('alert/error', error.response.data.message, { root: true })
                }
            )
    },
    forgotPassword({ dispatch, commit }, { email }){
        AuthService.forgotPassword({ email }).then(
            () => {
                commit('forgotPasswordSuccess');
                router.push('/forgot-password-verify');
            },
            error => {
                commit('forgotPasswordFailure');
                dispatch('alert/error', error.response.data.message, { root: true })
            }
        )
    },
    forgotPasswordVerify({ dispatch,  commit}, { verification }){
        AuthService.forgotPasswordVerify({ verification })
            .then(
                () => {
                    commit('forgotPasswordVerifySuccess');
                    router.push('/reset-password');
                },
                error => {
                    commit('forgotPasswordVerifyFailure');
                    dispatch('alert/error', error.response.data.message, { root: true });
                }
            )
    },
    resetPassword({ dispatch, commit }, { email, password }){
        AuthService.resetPassword({email, password})
            .then(
                () => {
                    commit('resetPasswordSuccess');
                    router.push('/login');
                },
                error => {
                    commit('resetPasswordFailure');
                    dispatch('alert/error', error.response.data.message, { root: true })
                }
            )
    }
};

const mutations = {
    loginRequest(state, user) {
        state.status = { loggingIn: true };
        state.user = user;
    },
    loginSuccess(state, user) {
        state.status = { loggedIn: true };
        state.user = user;
    },
    loginFailure(state) {
        state.status = {};
        state.user = null;
    },
    logout(state) {
        state.status = {};
        state.user = null;
    },
    registerRequest(state) {
        state.status = { registering: true };
    },
    registerSuccess(state, user) {
        state.status = {};
        state.user = user;
    },
    registerFailure(state, error) {
        state.status = { error };
    },
    refreshToken(state, accessToken) {
        state.status.loggedIn = true;
        state.user = { ...state.user, accessToken: accessToken };
    },
    verifyEmailRequest(state){
        state.status = { loggedIn: false };
    },
    verifyEmailSuccess(state, user){
        state.status = { loggedIn: true };
        state.user = user;
    },
    verifyEmailFailure(state, error){
        state.status = { loggedIn: false };
    },
    forgotPasswordSuccess(state){
        state.status = { forgotPassword: true }
    },
    forgotPasswordFailure(state){
        state.status = { forgotPassword: false }
    },
    forgotPasswordVerifySuccess(state){
        state.status = { forgotPasswordVerify: true }
    },
    forgotPasswordVerifyFailure(state){
        state.status = { forgotPasswordVerify: false }
    },
    resetPasswordSuccess(state){
        state.status = { resetPassword: true }
    },
    resetPasswordFailure(state){
        state.status = { resetPassword: false }
    }
};

export const authModule = {
    namespaced: true,
    state,
    actions,
    mutations
};