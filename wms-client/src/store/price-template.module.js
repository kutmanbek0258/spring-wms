import PriceTemplateService from '../services/price-template.service'

const state = {
    priceTemplate: {},
    priceTemplates: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisiblePriceTemplate: false,
    selectedPriceTemplate: {
        id: {},
        name: ''
    },
}

const actions = {
    createPriceTemplate({dispatch, commit}, {name, description, formula}){
        PriceTemplateService.createPriceTemplate({name, description, formula}).then(
            priceTemplate => {
                commit('setPriceTemplate', priceTemplate.data);
                dispatch('getAllPriceTemplates', {current: state.current, pageSize: this.state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Price-template created success!', {root: true});
            }
        ).catch(error => {
            commit('setPriceTemplate', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllPriceTemplates({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        PriceTemplateService.getAllPriceTemplates({page: current - 1, size: pageSize, sort}).then(
            priceTemplates => {
                commit('setPriceTemplates', priceTemplates.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            commit('setPriceTemplates', []);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getPriceTemplateById({dispatch, commit}, {id}){
        PriceTemplateService.getPriceTemplateById({id}).then(
            priceTemplate => {
                commit('setPriceTemplate', priceTemplate.data);
            }
        ).catch(error => {
            commit('setPriceTemplate', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updatePriceTemplate({dispatch, commit}, {id, name, description, formula}){
        PriceTemplateService.updatePriceTemplate({id, name, description, formula}).then(
            priceTemplate => {
                commit('setPriceTemplate', priceTemplate.data);
                dispatch('getAllPriceTemplates', {current: state.current, pageSize: this.state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Price-template updates success!', {root: true});
            }
        ).catch(error => {
            commit('setPriceTemplate', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deletePriceTemplate({dispatch, commit}, {id}){
        PriceTemplateService.deletePriceTemplate({id}).then(
            priceTemplate => {
                commit('setPriceTemplate', priceTemplate.data);
                dispatch('getAllPriceTemplates', {current: state.current, pageSize: this.state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Price-template deleted success!', {root: true});
            }
        ).catch(error => {
            commit('setPriceTemplate', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    handleSelectPriceTemplate({dispatch, commit}, {id, name}){
        const selectedPriceTemplate = { id, name };
        commit('setSelectedPriceTemplate', selectedPriceTemplate);
        commit('setPriceTemplateDialogVisibility', false);
    },

    handleCloseSelectionPriceTemplate({dispatch, commit}){
        const selectedPriceTemplate = { id: {}, name: '' };
        commit('setSelectedPriceTemplate', selectedPriceTemplate);
        commit('setPriceTemplateDialogVisibility', false);
    },

    visibleDialogPriceTemplate({dispatch, commit}){
        commit('setPriceTemplateDialogVisibility', true);
    },
}

const mutations = {
    setPriceTemplate(state, priceTemplate){
        state.priceTemplate = priceTemplate;
    },

    setPriceTemplates(state, priceTemplates){
        state.priceTemplates = priceTemplates.content;
        state.totalCount = priceTemplates.totalElements;
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

    setPriceTemplateDialogVisibility(state, visibility){
        state.dialogVisiblePriceTemplate = visibility;
    },

    setSelectedPriceTemplate(state, selectedPriceTemplate){
        state.selectedPriceTemplate = selectedPriceTemplate;
    }
}

export const priceTemplateModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}