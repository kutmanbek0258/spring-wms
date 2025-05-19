import CashRegisterService from '../services/cash-register.service';
import router from "../router";

const state = {
    cashRegister: null,
    cashRegisters: null,
    current: 1,
    pageSize: 10,
    totalCount: 0,
};

const actions = {
    createCashRegister({dispatch, commit}, {shopID, posID, ofd, printer}){
        CashRegisterService.createCashRegister({shopID, posID, ofd, printer}).then(
            cashRegister => {
                commit('setCashRegister', cashRegister.data);
                router.push('/references/cash-register');
                dispatch('alert/success', 'Cash register created success!', {root: true});
            }
        ).catch(error => {
            commit('setCashRegister', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllCashRegisters({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        CashRegisterService.getAllCashRegisters({take, skip}).then(
            cashRegisters => {
                commit('setCashRegisters', cashRegisters.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
            }
        ).catch(error => {
            commit('setCashRegisters', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getCashRegisterById({dispatch, commit}, {id}){
        CashRegisterService.getCashRegisterById({id}).then(
            cashRegister => {
                commit('setCashRegister', cashRegister.data[0]);
            }
        ).catch(error => {
            commit('setCashRegister', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateCashRegister({dispatch, commit}, {id, shopID, posID, ofd, printer}){
        CashRegisterService.updateCashRegister({id, shopID, posID, ofd, printer}).then(
            cashRegister => {
                commit('setCashRegister', cashRegister.data);
                router.push('/references/cash-register');
                dispatch('alert/success', 'Cash register updated success!', {root: true});
            }
        ).catch(error => {
            commit('setCashRegister', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteCashRegister({dispatch, commit}, {id}){
        CashRegisterService.deleteCashRegister({id}).then(
            cashRegister => {
                commit('setCashRegister', cashRegister.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setCashRegister', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    }
};

const mutations = {
    setCashRegister(state, cashRegister){
        state.cashRegister = cashRegister;
    },

    setCashRegisters(state, cashRegisters){
        state.cashRegisters = cashRegisters.cashRegisters;
        state.totalCount = cashRegisters.total;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },
};

export const cashRegisterModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}