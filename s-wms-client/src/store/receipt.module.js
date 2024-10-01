import ReceiptService from '../services/receipt.service'
import router from "@/router";

const state = {
    receipt: {},
    receipts: [],
    receiptItem: {},
    receiptItems: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: 'id',
    order: 'ASC',
    dialogVisibleReceipt: false,
    selectedReceipt: {
        id: null,
        name: '',
    },
    editedItem: {},
}

const actions = {
    createReceipt({dispatch, commit}, {supplierID, shopID, depotID}){
        ReceiptService.createReceipt({supplierID, shopID, depotID}).then(
            receipt => {
                commit('setReceipt', receipt.data);
                router.push('/depot/receipts');
                dispatch('alert/success', 'Receipt created success!', { root: true});
            }
        ).catch(error => {
            commit('setReceipt', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllReceipts({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        ReceiptService.getAllReceipts({take, skip}).then(
            receipts => {
                commit('setReceipts', receipts.data);
                commit('setPageSize', pageSize);
                commit('setCurrent', current);
            }
        ).catch(error => {
            commit('setReceipts', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getReceiptById({dispatch, commit}, {id}){
        ReceiptService.getReceiptById({id}).then(
            receipt => {
                commit('setReceipt', receipt.data[0]);
            }
        ).catch(error => {
            commit('setReceipt', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateReceipt({dispatch, commit}, {id, supplierID, shopID, depotID}){
        ReceiptService.updateReceipt({id, supplierID, shopID, depotID}).then(
            receipt => {
                commit('setReceipt', receipt.data);
                router.push('/depot/receipts');
                dispatch('alert/success', 'Updated success!', {root: true});
            }
        ).catch(error => {
            commit('setReceipt', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteReceipt({dispatch, commit}, {id}){
        ReceiptService.deleteReceipt({id}).then(
            receipt => {
                commit('setReceipt', receipt.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setReceipt', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    createAndFillPriceDocumentByReceipt({dispatch, commit}, {receiptID}){
        console.log(receiptID)
        ReceiptService.createAndFillReceiptDocumentByReceipt({receiptID}).then(
            priceID => {
                router.push('/marketing/update-price/' + priceID.data.priceID);
            }
        ).catch(error => {
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    addReceiptItem({dispatch, commit}, {receiptID, productID, quantity, price}){
        ReceiptService.addReceiptItem({receiptID, productID, quantity, price}).then(
            receiptItem => {
                commit('setReceiptItem', receiptItem.data);
                dispatch('getAllReceiptItem', {receiptID, sortBy: state.sortBy, order: state.order});
            }
        ).catch(error => {
            commit('setReceiptItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    getAllReceiptItem({dispatch, commit}, {receiptID, sortBy, order}){
        ReceiptService.getAllReceiptItems({receiptID, sortBy, order}).then(
            receiptItems => {
                commit('setReceiptItems', receiptItems.data);
                const newItem = state.receiptItems.find(({ id }) => id === state.receiptItem.id);
                if(newItem){
                    commit('setEditedItem', newItem);
                    commit('setSortBy', sortBy);
                    commit('setOrder', order);
                }
            }
        ).catch(error => {
            commit('setReceiptItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    updateReceiptItem({dispatch, commit}, {itemID, productID, quantity, price}){
        ReceiptService.updateReceiptItem({itemID, productID, quantity: Number(quantity), price: Number(price)}).then(
            receiptItem => {
                commit('setReceiptItem', receiptItem.data)
                commit('setEditedItem', {});
            }
        ).catch(error => {
            commit('setReceiptItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    deleteReceiptItem({dispatch, commit}, {itemID, receiptID}){
        ReceiptService.deleteReceiptItem({itemID}).then(
            receiptItem => {
                commit('setReceiptItem', receiptItem.data);
                dispatch('getAllReceiptItem', {receiptID, sortBy: state.sortBy, order: state.order});
            }
        ).catch(error => {
            commit('setReceiptItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    setDialogVisibilityReceipt({dispatch, commit}, {visibility}){
        commit('setDialogVisibleReceipt', visibility);
    },

    handleSelectReceipt({dispatch, commit}, {id, name}){
        const selectedReceipt = { id, name };
        const dialogVisibility = false;
        commit('setSelectedReceipt', selectedReceipt);
        commit('setDialogVisibleReceipt', dialogVisibility);
    },

    handleCloseSelectionReceipt({dispatch, commit}){
        const selectedReceipt = { id: null, name: '' };
        commit('setSelectedReceipt', selectedReceipt);
    },

    saveEditing({dispatch, commit}, {item}){
        commit('setEditedItem', item);
    },
}

const mutations = {
    setReceipt(state, receipt){
        state.receipt = receipt
    },

    setReceipts(state, receipts){
        state.receipts = receipts.receipts;
        state.totalCount = receipts.total;
    },

    setCurrent(state, current){
        state.current = current
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize
    },

    setSortBy(state, sortBy){
        state.sortBy = sortBy
    },

    setOrder(state, order){
        state.order = order
    },

    setDialogVisibleReceipt(state, visible){
        state.dialogVisibleReceipt = visible
    },

    setSelectedReceipt(state, selectedReceipt){
        state.selectedReceipt = selectedReceipt
    },

    setReceiptItem(state, item){
        state.receiptItem = item
    },

    setReceiptItems(state, items){
        state.receiptItems = items
    },

    setEditedItem(state, item){
        state.editedItem = item;
        // const foundedItem = state.editedData.find(({ id }) => id === item.id);
        // if(foundedItem){
        //     state.editedData[foundedItem] = item;
        // }else {
        //     state.editedData.push(item)
        // }
    },
}

export  const receiptModule = {
    namespaced: true,
    state,
    actions,
    mutations
}