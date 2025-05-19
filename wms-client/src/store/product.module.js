import ProductService from '../services/product.service';

const state = {
    product: {},
    products: [],
    current: 1,
    pageSize: 10,
    totalCount: 0,
    sortBy: [],
    dialogVisibleProduct: false,
    selectedProduct: {
        id: null,
        name: '',
    },
    uploadedProducts: null,
}

const actions = {
    createProduct({dispatch, commit}, {name, description, barcode, productGroup, priceTemplate}){
        ProductService.createProduct({name, description, barcode, productGroup, priceTemplate}).then(
            product => {
                commit('setProduct', product.data);
                dispatch('alert/success', 'Product created success!', { root: true});
                dispatch('getAllProducts', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setProduct', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getAllProducts({dispatch, commit}, {current, pageSize, sortBy, searchKeyword}){
        const sort = sortBy[0].key + "," + sortBy[0].order;
        ProductService.getAllProducts({page: current - 1, size: pageSize, sort, searchKeyword}).then(
            products => {
                commit('setProducts', products.data);
                commit('setCurrent', current);
                commit('setPageSize', pageSize)
                commit('setSortBy', sortBy);
            }
        ).catch(error => {
            commit('setProducts', []);
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    getProductById({dispatch, commit}, {id}){
        ProductService.getProductById({id}).then(
            product => {
                commit('setProduct', product.data);
            }
        ).catch(error => {
            commit('setProduct', {});
            dispatch('alert/error', error.response.data.message, {root: true})
        })
    },

    updateProduct({dispatch, commit}, {id, name, description, barcode, productGroup, priceTemplate}){
        ProductService.updateProduct({id, name, description, barcode, productGroup, priceTemplate}).then(
            product => {
                commit('setProduct', product.data);
                dispatch('alert/success', 'Product created success!', {root: true});
                dispatch('getAllProducts', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            commit('setProduct', {});
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    deleteProduct({dispatch, commit}, {id}){
        ProductService.deleteProduct({id}).then(
            product => {
                commit('setProduct', product.data);
                dispatch('getAllProducts', {current: state.current, pageSize: state.pageSize, sortBy: state.sortBy});
            }
        ).catch(error => {
            dispatch('alert/error', error.response.data.message, {root: true});
            commit('setProduct', {});
        })
    },

    uploadProductsCsvFile({dispatch, commit}, {file}){
        console.log(file);
        ProductService.uploadProductsCsv({file}).then(
            products => {
                dispatch('alert/success', 'Uploaded success!', {root: true});
                commit('setUploadedProducts', products.data);
            }
        ).catch(error => {
            dispatch('alert/error', error.response.data.message, {root: true});
        })
    },

    showProductDialog({dispatch, commit}){
        commit('setDialogVisibleProduct', true);
    },

    closeProductDialog({dispatch, commit}){
        const selectedProduct = {id: {}, name: ""};
        commit('setSelectedProduct', selectedProduct);
        commit('setDialogVisibleProduct', false);
    },

    setSelectedProduct({dispatch, commit}, {id, name}){
        const selectedProduct = { id, name };
        const dialogVisible = false;
        commit('setSelectedProduct', selectedProduct);
        commit('setDialogVisibleProduct', dialogVisible);
    }
}

const mutations = {
    setProduct(state, product){
        state.product = product;
    },

    setProducts(state, products){
        state.products = products.content;
        state.totalCount = products.totalElements;
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

    setDialogVisibleProduct(state, visibility){
        state.dialogVisibleProduct = visibility;
    },

    setSelectedProduct(state, selectedProduct){
        state.selectedProduct = selectedProduct;
    },

    setUploadedProducts(state, products){
        state.uploadedProducts = products;
    }
}

export const productModule = {
    namespaced: true,
    state,
    actions,
    mutations,
}