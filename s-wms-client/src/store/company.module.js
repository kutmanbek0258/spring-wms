import CompanyService from '../services/company.serivce';
import router from '../router';

const state = {
  company: {},
  companies: [],
  current: 1,
  pageSize: 10,
  totalCount: 0,
  sortBy: [],
  dialogVisibleCompany: false,
  selectedCompany: {
    id: null,
    name: '',
  }
}

const actions = {
  createCompany({dispatch, commit}, {name, description, address, phone, email}){
    CompanyService.createCompany({name, description, address, phone, email}).then(
      company => {
        commit('createCompanySuccess', company.data);
        dispatch('alert/success', 'Todo created success!', {root:true});
        dispatch('getAllCompanies',{current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
      }
    ).catch(error => {
      commit('createCompanyFailure');
      dispatch('alert/error', error.response.data.message, {root:true})
    })
  },

  getAllCompanies({dispatch, commit}, {current, pageSize, sortBy}){
    const sort = sortBy[0].key + ',' + sortBy[0].order;
    CompanyService.getAllCompanies({page: current - 1, size: pageSize, sort}).then(
      companies => {
        commit('currentSet', current);
        commit('pageSizeSet', pageSize);
        commit('sortBySet', sortBy);
        commit('getAllCompaniesSuccess', companies.data);
      }
    ).catch(error => {
      commit('getAllCompaniesFailure');
      dispatch('alert/error', error.response.data.message, {root: true});
    })
  },

  getCompanyById({dispatch, commit}, {id}){
    CompanyService.getCompanyById({id}).then(
      company => {
        commit('getCompanyByIdSuccess', company.data);
      }
    ).catch(error => {
      commit('getCompanyByIdFailure');
      dispatch('alert/error', error.response.data.message, {root: true});
    })
  },

  updateCompany({dispatch, commit}, {id, name, description, address, phone, email}){
    CompanyService.updateCompany({id, name, description, address, phone, email}).then(
        company => {
          commit('updateCompanySuccess', company.data);
          dispatch('alert/success', 'Company updates success!', {root: true})
          dispatch('getAllCompanies', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy})
        }
    ).catch(error => {
      console.log(error);
      commit('updateCompanyFailure');
      dispatch('alert/error', error.response.data.message, {root: true})
    })
  },

  deleteCompany({dispatch, commit}, {id}){
    CompanyService.deleteCompany({id}).then(
        company => {
          commit('deleteCompanySuccess', company);
          dispatch('alert/success', 'Company updated success', {root: true})
          dispatch('getAllCompanies', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy})
        }
    ).catch(error => {
      commit('deleteCompanyFailure');
      dispatch('alert/error', error.response.data.message, {root: true})
    })
  },

  setDialogVisibleCompany({dispatch, commit}) {
    commit('setDialogVisibility', true);
  },

  handleSelectCompany({dispatch, commit}, {id, name}) {
    const selectedCompany = { id: id, name: name };
    const visibility = false;
    commit('setSelectedCompany', selectedCompany);
    commit('setDialogVisibility', visibility);
  },

  handleCloseSelectionCompany({dispatch, commit}){
    const selectedCompany = { id: null, name: '' };
    commit('setSelectedCompany', selectedCompany);
    commit('setDialogVisibility', false);
  }

}

const mutations = {
  createCompanySuccess(state, company){
    state.company = company;
  },

  createCompanyFailure(state){
    state.company = [];
  },

  getAllCompaniesSuccess(state, companies){
    state.companies = companies.content;
    state.totalCount = companies.totalElements;
  },

  getAllCompaniesFailure(state){
    state.companies = [] ;
  },

  getCompanyByIdSuccess(state, company){
    state.company = company;
  },

  getCompanyByIdFailure(state){
    state.company = [];
  },

  updateCompanySuccess(state, company){
    state.company = company;
  },

  updateCompanyFailure(state){
    state.company = [];
  },

  deleteCompanySuccess(state, company){
    state.company = company;
  },

  deleteCompanyFailure(state){
    state.company = [];
  },

  currentSet(state, current){
    state.current = current;
  },

  pageSizeSet(state, pageSize){
    state.pageSize = pageSize;
  },

  sortBySet(state, sortBy){
    state.sortBy = sortBy
  },

  setDialogVisibility(state, visibility){
    state.dialogVisibleCompany = visibility;
  },

  setSelectedCompany(state, selectedCompany){
    state.selectedCompany = selectedCompany;
  },
}

export const companyModule = {
  namespaced: true,
  state,
  actions,
  mutations
}