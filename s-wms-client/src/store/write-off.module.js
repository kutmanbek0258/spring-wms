import WriteOffService from '../services/write-off.service'
import router from "@/router";

const state = {
    writeOff: {},
    writeOffs: [],
    writeOffItem: {},
    writeOffItems: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    dialogVisibleWriteOff: false,
    selectedWriteOff: {
        id: null,
        name: '',
    },
    editedItem: {},
}

const actions = {
    createWriteOff({dispatch, commit}, {shopID, depotID}){
        WriteOffService.createWriteOff({shopID, depotID}).then(
            writeOff => {
                commit('setWriteOff', writeOff.data);
                router.push('/depot/write-offs');
                dispatch('alert/success', 'created success!', { root: true});
            }
        ).catch(error => {
            commit('setWriteOff', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllWriteOffs({dispatch, commit}, {current, pageSize}){
        const take = pageSize;
        const skip = (current === 0) ? 0 : pageSize * (current - 1);
        WriteOffService.getAllWriteOffs({take, skip}).then(
            writeOffs => {
                commit('setWriteOffs', writeOffs.data);
                commit('setPageSize', pageSize);
                commit('setCurrent', current);
            }
        ).catch(error => {
            commit('setWriteOffs', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getWriteOffById({dispatch, commit}, {id}){
        WriteOffService.getWriteOffById({id}).then(
            writeOff => {
                commit('setWriteOff', writeOff.data[0]);
            }
        ).catch(error => {
            commit('setWriteOff', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateWriteOff({dispatch, commit}, {id, shopID, depotID}){
        WriteOffService.updateWriteOff({id, shopID, depotID}).then(
            writeOff => {
                commit('setWriteOff', writeOff.data);
                router.push('/depot/write-offs');
                dispatch('alert/success', 'Updated success!', {root: true});
            }
        ).catch(error => {
            commit('setWriteOff', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteWriteOff({dispatch, commit}, {id}){
        WriteOffService.deleteWriteOff({id}).then(
            writeOff => {
                commit('setWriteOff', writeOff.data);
                router.go(0);
            }
        ).catch(error => {
            commit('setReceipt', null);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    addWriteOffItem({dispatch, commit}, {writeOffID, productID, quantity, price}){
        WriteOffService.addWriteOffItem({writeOffID, productID, quantity, price}).then(
            writeOffItem => {
                commit('setWriteOffItem', writeOffItem.data);
                dispatch('getAllWriteOffItems', {writeOffID});
            }
        ).catch(error => {
            commit('setWriteOffItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    getAllWriteOffItems({dispatch, commit}, {writeOffID}){
        WriteOffService.getAllWriteOffItems({writeOffID}).then(
            writeOffItems => {
                commit('setWriteOffItems', writeOffItems.data);
                const newItem = state.writeOffItems.find(({ id }) => id === state.writeOffItem.id);
                if(newItem){
                    commit('setEditedItem', newItem);
                }
            }
        ).catch(error => {
            commit('setWriteOffItems', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    updateWriteOffItem({dispatch, commit}, {itemID, productID, quantity, price}){
        WriteOffService.updateWriteOffItem({itemID, productID, quantity: Number(quantity), price: Number(price)}).then(
            writeOffItem => {
                commit('setWriteOffItem', writeOffItem.data)
                commit('setEditedItem', {})
            }
        ).catch(error => {
            commit('setWriteOffItem', null);
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    deleteWriteOffItem({dispatch, commit}, {itemID, writeOffID}){
        WriteOffService.deleteWriteOffItem({itemID}).then(
            writeOffItem => {
                commit('setWriteOffItem', writeOffItem.data);
                dispatch('getAllWriteOffItems', {writeOffID});
            }
        ).catch(error => {
            commit('setWriteOffItem', null);
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
    setWriteOff(state, writeOff){
        state.writeOff = writeOff
    },

    setWriteOffs(state, writeOffs){
        state.writeOffs = writeOffs.writeOffs;
        state.totalCount = writeOffs.total;
    },

    setCurrent(state, current){
        state.current = current
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize
    },

    setDialogVisibleWriteOff(state, visible){
        state.dialogVisibleWriteOff = visible
    },

    setSelectedWriteOff(state, selectedWriteOff){
        state.selectedWriteOff = selectedWriteOff
    },

    setWriteOffItem(state, item){
        state.writeOffItem = item
    },

    setWriteOffItems(state, items){
        state.writeOffItems = items
    },

    setEditedItem(state, item){
        state.editedItem = item;
    },
}

export  const writeOffModule = {
    namespaced: true,
    state,
    actions,
    mutations
}