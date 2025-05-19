import PosService from '../services/pos.service';
import router from "../router";

const state = {
    pos: null,
    poses: null,
    current: 1,
    pageSize: 10,
    totalCount: 0,
    dialogVisiblePos: false,
    selectedPos: {
        id: null,
        name: '',
    }
};

const actions = {
    createPos({dispatch, commit}, {name, shopID}){
        PosService.createPos({name, shopID}).then(
            pos => {
                commit('setPos', pos.data);
                router.push('/references/pos');
                dispatch('alert/success', 'POS created success!', {root: true});
            }
        ).catch(error => {
            commit('setPos', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllPoses({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        PosService.getAllPoses({take, skip}).then(
            poses => {
                commit('setPoses', poses.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
            }
        ).catch(error => {
            commit('setPoses', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getPosById({dispatch, commit}, {id}){
        PosService.getPosById({id}).then(
            pos => {
                commit('setPos', pos.data[0]);
            }
        ).catch(error => {
            commit('setPos', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updatePos({dispatch, commit}, {id, name, shopID}){
        PosService.updatePos({id, name, shopID}).then(
            pos => {
                commit('setPos', pos.data);
                router.push('/references/pos');
                dispatch('alert/success', 'POS updated success!', {root: true});
            }
        ).catch(error => {
            commit('setPos', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deletePos({dispatch, commit}, {id}){
        PosService.deleteShop({id}).then(
            pos => {
                commit('setPos', pos.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setPos', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    setDialogVisiblePos({dispatch, commit}, {visibility}){
        commit('setDialogVisiblePos', visibility);
    },

    setSelectedPos({dispatch, commit}, {id, name}){
        const selectedPos = { id, name };
        const dialogVisible = false;
        commit('setSelectedPos', selectedPos);
        commit('setDialogVisiblePos', dialogVisible);
    }
};

const mutations = {
    setPos(state, pos){
        state.pos = pos;
    },

    setPoses(state, poses){
        state.poses = poses.poses;
        state.totalCount = poses.total;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setDialogVisiblePos(state, visibility){
        state.dialogVisiblePos = visibility;
    },

    setSelectedPos(state, selectedPos){
        state.selectedPos = selectedPos;
    }
};

export const posModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}