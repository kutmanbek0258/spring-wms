import PersonService from '../services/person.service';
import router from '../router';

const state = {
    person: {},
    persons: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisiblePerson: false,
    selectedPerson: {
        id: {},
        fullName: ''
    }
}

const actions = {
    createPerson({dispatch, commit}, {fullName, phone, email, address}){
        PersonService.createPerson({fullName, phone, email, address}).then(
            person => {
                commit('createPersonSuccess', person.data);
                dispatch('getAllPersons', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Person created success!', {root: true});
            }
        ).catch(error => {
            commit('createPersonFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        });
    },

    getAllPersons({dispatch, commit}, {current, pageSize, sortBy}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        PersonService.getAllPersons({page: current - 1, size: pageSize, sort}).then(
            persons => {
                commit('getAllPersonsSuccess', persons.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize);
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            commit('getAllPersonsFailure');
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    getPersonById({dispatch, commit}, {id}){
        PersonService.getPersonById({id}).then(
            person => {
                commit('getPersonByIdSuccess', person.data);
            }
        ).catch(error => {
            commit('getPersonByIdFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    updatePerson({dispatch, commit}, {id, fullName, phone, email, address}){
        PersonService.updatePerson({id, fullName, phone, email, address}).then(
            person => {
                commit('updatePersonSuccess', person);
                dispatch('alert/success', 'Person updated success!', {root: true});
                dispatch('getAllPersons', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy})
            }
        ).catch(error => {
            console.log(error);
            commit('updatePersonFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deletePerson({dispatch, commit}, {id}){
        PersonService.deletePerson({id}).then(
            person => {
                commit('deletePersonSuccess', person.data);
                dispatch('getAllPersons', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
                dispatch('alert/success', 'Person deleted success!', {root: true});
            }
        ).catch(error => {
            commit('deletePersonFailure');
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    setDialogVisiblePerson({dispatch, commit}, ) {
        commit('setDialogVisibility', true);
    },

    handleSelectPerson({dispatch, commit}, {id, fullName}){
        const selectedPerson = { id, fullName };
        commit('setSelectedPerson', selectedPerson);
        commit('setDialogVisibility', false)
    },

    handleCloseSelectionPerson({dispatch, commit}){
        const selectedPerson = {id: {}, fullName: ""};
        commit('setSelectedPerson', selectedPerson);
        commit('setDialogVisibility', false);
    }
}

const mutations = {
    createPersonSuccess(state, person){
        state.person = person;
    },

    createPersonFailure(state){
        state.person = {};
    },

    getAllPersonsSuccess(state, persons){
        state.persons = persons.content;
        state.totalCount = persons.totalElements;
    },

    getAllPersonsFailure(state){
        state.persons = [];
    },

    getPersonByIdSuccess(state, person){
        state.person = person;
    },

    getPersonByIdFailure(state){
        state.person = {};
    },

    updatePersonSuccess(state, person){
        state.person = person;
    },

    updatePersonFailure(state){
        state.person = {};
    },

    deletePersonSuccess(state, person){
        state.person = person;
    },

    deletePersonFailure(state){
        state.person = {};
    },

    setCurrent(state, current){
        state.current = current;
    },

    setPageSize(state, pageSize){
        state.pageSize = pageSize;
    },

    setSortBy(state, sortBy) {
        state.sortBy = sortBy;
    },

    setDialogVisibility(state, visibility) {
        state.dialogVisiblePerson = visibility;
    },

    setSelectedPerson(state, person){
        state.selectedPerson = person;
    },
}

export const personModule = {
    namespaced: true,
    state,
    actions,
    mutations
}