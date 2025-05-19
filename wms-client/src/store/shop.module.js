import ShopService from '../services/shop.service';
import router from "../router";

const state = {
    shop: null,
    shops: null,
    current: 1,
    pageSize: 10,
    totalCount: 0,
    dialogVisibleShop: false,
    selectedShop: {
        id: null,
        name: '',
    }
};

const actions = {
    createShop({dispatch, commit}, {name, description, address, companyID, managerID, depotID}){
        ShopService.createShop({name, description, address, companyID, managerID, depotID}).then(
            shop => {
                commit('setShop', shop.data);
                router.push('/references/shop');
                dispatch('alert/success', 'Shop created success!', {root: true});
            }
        ).catch(error => {
            commit('setShop', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    getAllShops({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        ShopService.getAllShops({take, skip}).then(
            shops => {
                commit('setShops', shops.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
            }
        ).catch(error => {
            commit('setShops', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getShopById({dispatch, commit}, {id}){
        ShopService.getShopById({id}).then(
            shop => {
                console.log(shop.data)
                commit('setShop', shop.data[0]);
            }
        ).catch(error => {
            commit('setShop', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateShop({dispatch, commit}, {id, name, description, address, companyID, managerID, depotID}){
        ShopService.updateShop({id, name, description, address, companyID, managerID, depotID}).then(
            shop => {
                commit('setShop', shop.data);
                router.push('/references/shop');
                dispatch('alert/success', 'Shop updated success!', {root: true});
            }
        ).catch(error => {
            commit('setShop', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    deleteShop({dispatch, commit}, {id}){
        ShopService.deleteShop({id}).then(
            shop => {
                commit('setShop', shop.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setShop', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    setDialogVisibilityShop({dispatch, commit}, {visibility}){
        commit('setDialogVisibleShop', visibility);
    },

    handleSelectShop({dispatch, commit}, {id, name}){
        const selectedShop = { id, name };
        const dialogVisibility = false;
        commit('setSelectedShop', selectedShop);
        commit('setDialogVisibleShop', dialogVisibility);
    },

    handleCloseSelectionShop({dispatch, commit}){
        const selectedShop = { id: null, name: '' };
        commit('setSelectedShop', selectedShop);
    }
};

const mutations = {
    setShop(state, shop){
        state.shop = shop;
    },

    setShops(state, shops){
        state.shops = shops.shops;
        state.totalCount = shops.total;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setDialogVisibleShop(state, visibility){
        state.dialogVisibleShop = visibility;
    },

    setSelectedShop(state, selectedShop){
        state.selectedShop = selectedShop;
    }
};

export const shopModule = {
    namespaced: true,
    state,
    actions,
    mutations,
};