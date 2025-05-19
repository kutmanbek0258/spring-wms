import api from './api';

class DepotService{
    createDepot({name, description, address, company, manager}){
        return api.post('/depot', {
            name,
            description,
            address,
            company,
            manager,
        })
    };

    getAllDepots({page, size, sort}){
        const params = { page, size, sort }
        return api.get('/depot/page-query', { params });
    };

    getDepotById({id}){
        return api.get('/depot/' + id);
    };

    updateDepot({id, name, description, address, company, manager}){
        return api.put('/depot/' + id, {
            name,
            description,
            address,
            company,
            manager,
        });
    };

    deleteDepot({id}){
        return api.delete('/depot/' + id);
    }
}

export default new DepotService();