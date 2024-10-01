import DepotService from '../services/depot.service';

const state = {
    depot: {},
    depots: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisibleDepot: false,
    selectedDepot: {
        id: null,
        name: '',
    }
};

const actions = {
    createDepot({dispatch, commit}, {name,description, address, company, manager}){
        DepotService.createDepot({name, description, address, company, manager}).then(
            depot => {
                commit('setDepot', depot.data);
                dispatch('getAllDepots', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Depot created success!', {root: true});
            }
        ).catch(error => {
            commit('setDepot', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    getAllDepots({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        DepotService.getAllDepots({page: current -1, size: pageSize, sort}).then(
            depots => {
                commit('setDepots', depots.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            commit('setDepots', []);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getDepotById({dispatch, commit}, {id}){
        DepotService.getDepotById({id}).then(
            depot => {
                commit('setDepot', depot.data);
            }
        ).catch(error => {
            commit('setDepot', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateDepot({dispatch, commit}, {id, name, description, address, company, manager}){
        DepotService.updateDepot({id, name, description, address, company, manager}).then(
            depot => {
                commit('setDepot', depot.data);
                dispatch('alert/success', 'Depot updated success!', {root: true});
                dispatch('getAllDepots', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            console.log(error);
            commit('setDepot', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteDepot({dispatch, commit}, {id}){
        DepotService.deleteDepot({id}).then(
            depot => {
                commit('setDepot', depot.data);
                dispatch('getAllDepots', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setDepot', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    showDepotDialog({dispatch, commit}){
        commit('setDialogVisibilityDepot', true);
    },

    closeDepotDialog({dispatch, commit}){
        const selectedDepot = {id: null, name: ''};
        commit('setSelectedDepot', selectedDepot);
        commit('setDialogVisibilityDepot', false);
    },

    handleSelectDepot({dispatch, commit}, {id, name}){
        const selectedDepot = {id, name};
        const visibility = false;
        commit('setSelectedDepot', selectedDepot);
        commit('setDialogVisibilityDepot', visibility);
    },
};

const mutations = {
    setDepot(state, depot){
        state.depot = depot;
    },

    setDepots(state, depots){
        state.depots = depots.content;
        state.totalCount = depots.totalElements;
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

    setDialogVisibilityDepot(state, visibility){
        state.dialogVisibleDepot = visibility;
    },

    setSelectedDepot(state, selectedDepot){
        state.selectedDepot = selectedDepot;
    }
};

export const depotModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}