import api from './api';

class SalesmanService{

    createSalesman({person, company}){
        return api.post('/salesman', {
            person,
            company,
        })
    }

    getAllSalesmen({page, size, sort}){
        const params = { page, size, sort };
        return api.get('/salesman/page-query', { params })
    }

    getSalesmanById({id}){
        return api.get('/salesman/' + id);
    }

    updateSalesman({id, person, company}){
        return api.put('/salesman/' + id, {
            person,
            company,
        })
    }

    deleteSalesman({id}){
        return api.delete('/salesman/' + id);
    }

}

export default new SalesmanService();