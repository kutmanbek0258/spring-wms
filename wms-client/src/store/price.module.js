import PriceService from '../services/price.service'
import router from "@/router";

const state = {
    price: {},
    prices: [],
    priceItem: {},
    priceItems: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    editedItem: {},
};

const actions = {
    createPrice({dispatch, commit}, {shopID}){
        PriceService.createPrice({shopID}).then(
            price => {
                commit('setPrice', price.data);
                router.push('/marketing/update-price/' + price.data.id);
                dispatch('alert/success', 'Price created success!', { root: true});
            }
        ).catch(error => {
            commit('setPrice', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllPrices({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        PriceService.getAllPrice({take, skip}).then(
            prices => {
                commit('setPrices', prices.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
            }
        ).catch(error => {
            commit('setPrices', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getPriceById({dispatch, commit}, {priceID}){
        PriceService.getPriceById({priceID}).then(
            price => {
                commit('setPrice', price.data[0]);
            }
        ).catch(error => {
            commit('setPrice', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updatePrice({dispatch, commit}, {priceID, shopID}){
        PriceService.updatePrice({priceID, shopID}).then(
            price => {
                commit('setPrice', price.data);
                router.push('/marketing/price-list')
                dispatch('alert/success', 'Updated success!', {root: true});
            }
        ).catch(error => {
            commit('setPrice', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deletePrice({dispatch, commit}, {priceID}){
        PriceService.deletePrice({priceID}).then(
            price => {
                commit('setPrice', price.data);
                router.go(0);
                dispatch('alert/success', 'Deleted success!', {root: true});
            }
        ).catch(error => {
            commit('setPrice', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    addPriceItem({dispatch, commit}, {priceID, productID, retail_price}){
        PriceService.addItem({priceID, productID, retail_price}).then(
            priceItem => {
                console.log(priceItem.data)
                commit('setPriceItem', priceItem.data);
                dispatch('getAllPriceItems', {priceID});
            }
        ).catch(error => {
            commit('setPriceItem', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllPriceItems({dispatch, commit}, {priceID}){
        PriceService.getAllItems({priceID}).then(
            priceItems => {
                commit('setPriceItems', priceItems.data);
                const newItem = state.priceItems.find(({ id }) => id === state.priceItem.id);
                if(newItem){
                    commit('setEditedItem', newItem);
                }
            }
        ).catch(error => {
            commit('setPriceItems', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updatePriceItem({dispatch, commit}, {itemID, productID, retail_price}){
        PriceService.updateItem({itemID, productID, retail_price: Number(retail_price)}).then(
            priceItem => {
                commit('setPriceItem', priceItem.data);
                commit('setEditedItem', {});
            }
        ).catch(error => {
            commit('setPriceItem', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deletePriceItem({dispatch, commit}, {itemID, priceID}){
        PriceService.deleteItem({itemID}).then(
            priceItem => {
                commit('setPriceItem', priceItem.data);
                dispatch('getAllPriceItems', {priceID})
            }
        ).catch(error => {
            commit('setPriceItem', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    saveEditing({dispatch, commit}, {item}){
        commit('setEditedItem', item);
    },
};

const mutations = {
    setPrice(state, price){
        state.price = price;
    },

    setPrices(state, prices){
        state.prices = prices.prices;
        state.totalCount = prices.total;
    },

    setPriceItem(state, priceItem){
        state.priceItem = priceItem;
    },

    setPriceItems(state, priceItems){
        state.priceItems = priceItems;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setEditedItem(state, editedItem){
        state.editedItem = editedItem;
    },
};

export const priceModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}