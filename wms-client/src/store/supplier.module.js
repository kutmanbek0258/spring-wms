import SupplierService from '../services/supplier.service';
import router from '../router';

const state = {
    supplier: {},
    suppliers: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisibleSuppler: false,
    selectedSupplier: {
        id: {},
        person: {
            fullName: ""
        },
    }
};

const actions = {
    createSupplier({dispatch, commit}, {person, company}){
        SupplierService.createSupplier({person, company}).then(
            supplier => {
                commit('setSupplier', supplier.data);
                dispatch('alert/success', 'Supplier created success!', {root: true});
                dispatch('getAllSuppliers', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSupplier', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllSuppliers({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        SupplierService.getAllSuppliers({page: current - 1, size: pageSize, sort}).then(
            suppliers => {
                commit('setSuppliers', suppliers.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            const suppliers = {
                suppliers: [],
                total: 0,
            }
            commit('setSuppliers', suppliers);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getSupplierById({dispatch, commit}, {id}){
        SupplierService.getSupplierById({id}).then(
            supplier => {
                commit('setSupplier', supplier.data[0]);
            }
        ).catch(error => {
            commit('setSupplier', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updateSupplier({dispatch, commit}, {id, person, company}){
        SupplierService.updateSupplier({id, person, company}).then(
            supplier => {
                commit('setSupplier', supplier.data);
                dispatch('alert/success', 'Supplier updated success!', {root: true});
                dispatch('getAllSuppliers', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSupplier', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteSupplier({dispatch, commit}, {id}){
        SupplierService.deleteSupplier({id}).then(
            supplier => {
                commit('setSupplier', supplier.data);
                dispatch('alert/success', 'Supplier deleted success!', {root: true});
                dispatch('getAllSuppliers', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setSupplier', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    showSupplierDialog({dispatch, commit}){
        commit('setDialogVisibleSuppler', true);
    },

    handleSelectSupplier({dispatch, commit}, {id, person}){
        const selectedSupplier = { id, person };
        commit('setSelectedSupplier', selectedSupplier);
        commit('setDialogVisibleSuppler', false);
    },

    closeSupplierDialog({dispatch, commit}){
        const selectedSupplier = { id: null, name: '' };
        commit('setSelectedSupplier', selectedSupplier);
        commit('setDialogVisibleSuppler', false);
    }
};

const mutations = {
    setSupplier(state, supplier){
        state.supplier = supplier;
    },

    setSuppliers(state, suppliers){
        state.suppliers = suppliers.content;
        state.totalCount = suppliers.totalElements;
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setSortBy(state, sortBy){
        state.sortBy = sortBy
    },

    setDialogVisibleSuppler(state, visibility){
        state.dialogVisibleSuppler = visibility;
    },

    setSelectedSupplier(state, selectedSupplier){
        state.selectedSupplier = selectedSupplier;
    }
};

export const supplierModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}