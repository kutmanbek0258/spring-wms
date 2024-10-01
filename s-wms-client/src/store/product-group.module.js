import ProductGroupService from '../services/product-group.service';

const state = {
    productGroup: {},
    productGroups: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisibleProductGroup: false,
    selectedProductGroup: {
        id: null,
        name: '',
    }
}

const actions = {
    createProductGroup({dispatch, commit}, {name, description}){
        ProductGroupService.createProductGroup({name, description}).then(
            productGroup => {
                commit('createProductGroupSuccess', productGroup.data);
                dispatch('getAllProductGroups', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Group created success!', {root: true});
            }
        ).catch(error => {
            commit('createProductGroupFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllProductGroups({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order
        ProductGroupService.getAllProductGroup({page: current -1, size: pageSize, sort}).then(
            productGroups => {
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
                commit('getAllProductGroupsSuccess', productGroups.data);
            }
        ).catch(error => {
            commit('getAllProductGroupsFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getProductGroupById({dispatch, commit}, {id}){
        ProductGroupService.getProductGroupById({id}).then(
            productGroup => {
                commit('getProductGroupByIdSuccess', productGroup.data);
            }
        ).catch(error => {
            commit('getProductGroupByIdFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateProductGroup({dispatch, commit}, {id, name, description}){
        ProductGroupService.updateProductGroup({id, name, description}).then(
            productGroup => {
                commit('updateProductGroupSuccess', productGroup.data);
                dispatch('getAllProductGroups', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Group updated success!', {root: true});
            }
        ).catch(error => {
            commit('updateProductGroupFailure');
            dispatch('alert/error', error.response.data.message);
        })
    },

    deleteProductGroup({dispatch, commit}, {id}){
        ProductGroupService.deleteProductGroup({id}).then(
            productGroup => {
                commit('deleteProductGroupSuccess', productGroup.data);
                dispatch('getAllProductGroups', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/error', 'product-group deleted success!')
            }
        ).catch(error => {
            commit('deleteProductGroupFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    handleSelectProductGroup({dispatch, commit}, {id, name}){
        const selectedProductGroup = { id, name };
        commit('setSelectProductGroup', selectedProductGroup);
        commit('setDialogVisibility', false);
    },

    handleCloseSelectionProductGroup({dispatch, commit}){
        const selectedData = { id: null, name: '' };
        commit('setSelectProductGroup', selectedData);
        commit('setDialogVisibility', false);
    },

    visibleDialogProductGroup({dispatch, commit}) {
        commit('setDialogVisibility', true);
    }
}

const mutations = {
    createProductGroupSuccess(state, productGroup){
        state.productGroup = productGroup;
    },

    createProductGroupFailure(state){
        state.productGroup = {};
    },

    getAllProductGroupsSuccess(state, productGroups){
        state.productGroups = productGroups.content;
        state.totalCount = productGroups.totalElements;
    },

    getAllProductGroupsFailure(state){
        state.productGroups = [];
    },

    getProductGroupByIdSuccess(state, productGroup){
        state.productGroup = productGroup;
    },

    getProductGroupByIdFailure(state){
        state.productGroup = {};
    },

    updateProductGroupSuccess(state, productGroup){
        state.productGroup = productGroup;
    },

    updateProductGroupFailure(state){
        state.productGroup = {};
    },

    deleteProductGroupSuccess(state, productGroup){
        state.productGroup = productGroup;
    },

    deleteProductGroupFailure(state){
        state.productGroup = {};
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize) {
        state.pageSize = pageSize;
    },

    setSortBy(state, sortBy){
        state.sortBy = sortBy;
    },

    setDialogVisibility(state, visibility){
        state.dialogVisibleProductGroup = visibility;
    },

    setSelectProductGroup(state, selectedProductGroup){
        state.selectedProductGroup = selectedProductGroup;
    }
}

export const productGroupModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}