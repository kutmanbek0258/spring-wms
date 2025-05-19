import api from './api';

class PriceTemplateService{
    createPriceTemplate({name, description, formula}){
        return api.post('/price-template', {
            name,
            description,
            formula: Number(formula),
        })
    }

    getAllPriceTemplates({page, size, sort}){
        const params = { page, size, sort }
        return api.get('/price-template/page-query', { params })
    }

    getPriceTemplateById({id}){
        return api.get('/price-template/' + id);
    }

    updatePriceTemplate({id, name, description, formula}){
        return api.put('/price-template/' + id, {
            name,
            description,
            formula: Number(formula)
        })
    }

    deletePriceTemplate({id}){
        return api.delete('/price-template/' + id);
    }
}

export default new PriceTemplateService();