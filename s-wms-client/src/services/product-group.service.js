import api from './api';

class ProductGroupService{
    createProductGroup({name, description}){
        return api.post('/product-group', {
            name,
            description,
        });
    }

    getAllProductGroup({page, size, sort}){
        const params = { page, size, sort }
        return api.get('/product-group/page-query', { params });
    }

    getProductGroupById({id}){
        return api.get('/product-group/' + id);
    }

    updateProductGroup({id, name, description}){
        return api.put('/product-group/' + id, {
            name,
            description,
        });
    }

    deleteProductGroup({id}){
        return api.delete('/product-group/' + id);
    }
}

export default new ProductGroupService();