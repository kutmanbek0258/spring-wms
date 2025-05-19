import RecountService from '../services/recount.service'
import router from "@/router";

const state = {
    recount: {},
    recounts: [],
    recountItem: {},
    recountItems: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    dialogVisibleRecount: false,
    selectedRecount: {
        id: null,
        name: '',
    },
    editedItem: {},
}

const actions = {
    createRecount({dispatch, commit}, {shopID, depotID}){
        RecountService.createRecount({shopID, depotID}).then(
            recount => {
                commit('setRecount', recount.data);
                router.push('/depot/recounts');
                dispatch('alert/success', 'created success!', { root: true});
            }
        ).catch(error => {
            commit('setRecount', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllRecounts({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        RecountService.getAllRecounts({take, skip}).then(
            recounts => {
                console.log(recounts.data)
                commit('setRecounts', recounts.data);
                commit('setPageSize', pageSize);
                commit('setCurrent', current);
            }
        ).catch(error => {
            commit('setRecounts', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getRecountById({dispatch, commit}, {id}){
        RecountService.getRecountByID({id}).then(
            recount => {
                commit('setRecount', recount.data[0]);
            }
        ).catch(error => {
            commit('setRecount', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateRecount({dispatch, commit}, {id, shopID, depotID, status}){
        RecountService.updateRecount({id, shopID, depotID, status}).then(
            recount => {
                commit('setRecount', recount.data);
                router.push('/depot/recounts');
                dispatch('alert/success', 'Updated success!', {root: true});
            }
        ).catch(error => {
            commit('setRecount', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteRecount({dispatch, commit}, {id}){
        RecountService.deleteRecount({id}).then(
            recount => {
                commit('setRecount', recount.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setRecount', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    addRecountItem({dispatch, commit}, {recountID, productID, quantity, actual_quantity, price}){
        RecountService.addRecountItem({recountID, productID, quantity, actual_quantity, price}).then(
            recountItem => {
                commit('setRecountItem', recountItem.data);
                dispatch('getAllRecountItems', {recountID});
            }
        ).catch(error => {
            commit('setRecountItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    fillRecountItemsByAccountingQuantity({dispatch, commit}, {recountId}){
        RecountService.fillRecountItemsByAccountingQuantity({recountId}).then(
            recountItems => {
                commit('setRecountItems', recountItems.data);
            }
        ).catch(error => {
            commit('setRecountItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    fillRecountItemsActualQuantityByAccountingQuantity({dispatch, commit}, {recountId}){
        RecountService.fillRecountItemsActualQuantityByAccountingQuantity({recountId}).then(
            recountItems => {
                commit('setRecountItems', recountItems.data);
            }
        ).catch(error => {
            commit('setRecountItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    fillRecountItemsPriceByRetailPrice({dispatch, commit}, {recountId}){
        RecountService.fillRecountItemsPriceByRetailPrice({recountId}).then(
            recountItems => {
                commit('setRecountItems', recountItems.data);
            }
        ).catch(error => {
            commit('setRecountItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    fillRecountItemsPriceByCost({dispatch, commit}, {recountId}){
        RecountService.fillRecountItemsPriceByCost({recountId}).then(
            recountItems => {
                commit('setRecountItems', recountItems.data);
            }
        ).catch(error => {
            commit('setRecountItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    createReceiptDocumentByRecount({dispatch, commit}, {recountId}){
        RecountService.createReceiptDocumentByRecount({recountId}).then(
            receiptId => {
                router.push('/depot/update-receipt/' + receiptId.data.receiptId);
            }
        ).catch(error => {
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    createWriteOffDocumentByRecount({dispatch, commit}, {recountId}){
        RecountService.createWriteOffDocumentByRecount({recountId}).then(
            writeOffId => {
                router.push('/depot/update-write-off/' + writeOffId.data.writeOffId);
            }
        ).catch(error => {
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    getAllRecountItems({dispatch, commit}, {recountID}){
        RecountService.getAllRecountItems({recountID}).then(
            recountItems => {
                commit('setRecountItems', recountItems.data);
                const newItem = state.recountItems.find(({ id }) => id === state.recountItem.id);
                if(newItem){
                    commit('setEditedItem', newItem);
                }
            }
        ).catch(error => {
            commit('setRecountItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    updateRecountItem({dispatch, commit}, {itemID, productID, quantity, actual_quantity, price}){
        RecountService.updateRecountItem({itemID, productID, quantity: Number(quantity), actual_quantity: Number(actual_quantity), price: Number(price)}).then(
            recountItem => {
                commit('setRecountItem', recountItem.data)
            }
        ).catch(error => {
            commit('setRecountItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    deleteRecountItem({dispatch, commit}, {itemID, recountID}){
        RecountService.deleteRecountItem({itemID}).then(
            recountItem => {
                commit('setRecountItem', recountItem.data);
                dispatch('getAllRecountItems', {recountID, sortBy: state.sortBy, order: state.order});
            }
        ).catch(error => {
            commit('setRecountItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    setDialogVisibilityRecount({dispatch, commit}, {visibility}){
        commit('setDialogVisibleReceipt', visibility);
    },

    handleSelectRecount({dispatch, commit}, {id, name}){
        const selectedRecount = { id, name };
        const dialogVisibility = false;
        commit('setSelectedRecount', selectedRecount);
        commit('setDialogVisibleRecount', dialogVisibility);
    },

    handleCloseSelectionRecount({dispatch, commit}){
        const selectedRecount = { id: null, name: '' };
        commit('setSelectedRecount', selectedRecount);
    },

    saveEditing({dispatch, commit}, {item}){
        commit('setEditedItem', item);
    },
}

const mutations = {
    setRecount(state, recount){
        state.recount = recount
    },

    setRecounts(state, recounts){
        state.recounts = recounts.recounts;
        state.totalCount = recounts.total;
    },

    setCurrent(state, current){
        state.current = current
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize
    },

    setDialogVisibleRecount(state, visible){
        state.dialogVisibleRecount = visible
    },

    setSelectedRecount(state, selectedRecount){
        state.selectedRecount = selectedRecount
    },

    setRecountItem(state, item){
        state.recountItem = item
    },

    setRecountItems(state, items){
        state.recountItems = items
    },

    setEditedItem(state, item){
        state.editedItem = item;
    },
}

export  const recountModule = {
    namespaced: true,
    state,
    actions,
    mutations
}