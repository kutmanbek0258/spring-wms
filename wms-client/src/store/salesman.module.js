import SalesmanService from '../services/salesman.service';
import router from "../router";

const state = {
    salesman: {},
    salesmen: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    salesmanDialogVisibility: false,
    selectedSalesman: {
        id: {},
        name: ''
    }
};

const actions = {
    createSalesman({dispatch, commit}, {person, company}){
        SalesmanService.createSalesman({person, company}).then(
            salesman => {
                commit('setSalesman', salesman.data);
                dispatch('alert/success', 'Salesman created success!', {root: true});
                dispatch('getAllSalesmen', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSalesman', {});
        })
    },

    getAllSalesmen({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        SalesmanService.getAllSalesmen({page: current - 1, size: pageSize, sort}).then(
            salesmen => {
                commit('setSalesmen', salesmen.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            commit('setSalesmen', []);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getSalesmanById({dispatch, commit}, {id}){
        SalesmanService.getSalesmanById({id}).then(
            salesman => {
                commit('setSalesman', salesman.data[0]);
            }
        ).catch(error => {
            commit('setSalesman', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateSalesman({dispatch, commit}, {id, person, company}){
        SalesmanService.updateSalesman({id, person, company}).then(
            salesman => {
                commit('setSalesman', salesman.data);
                dispatch('alert/success', 'Salesman updated success!', {root: true});
                dispatch('getAllSalesmen', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSalesman', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteSalesman({dispatch, commit}, {id}){
        SalesmanService.deleteSalesman({id}).then(
            salesman => {
                commit('setSalesman', salesman.data);
                dispatch('getAllSalesmen', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSalesman', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    showSalesmanDialog({dispatch, commit}){
        commit('setSalesmanDialogVisibility', true);
    },

    closeSalesmanDialog({dispatch, commit}){
        commit('setSalesmanDialogVisibility', false);
    },

    handleSelectSalesman({dispatch, commit}, {id, name}){
        commit('setSelectedSalesman', {id, name});
        commit('setSalesmanDialogVisibility', false);
    }
};

const mutations = {
    setSalesman(state, salesman){
        state.salesman = salesman;
    },

    setSalesmen(state, salesmen){
        state.salesmen = salesmen.content;
        state.totalCount = salesmen.totalElements;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setSortBy(state, sortBy){
        state.sortBy = sortBy;
    },

    setSalesmanDialogVisibility(state, visibility){
        state.salesmanDialogVisibility = visibility;
    },

    setSelectedSalesman(state, salesman){
        state.selectedSalesman = salesman;
    }
};

export const salesmanModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}