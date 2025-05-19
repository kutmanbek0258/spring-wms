import api from './api';

class SupplierService{
    createSupplier({person, company}){
        return api.post('/supplier', {
            person,
            company,
        })
    }

    getAllSuppliers({page, size, sort}){
        const params = { page, size, sort };
        return api.get('/supplier/page-query', { params })
    }

    getSupplierById({id}){
        return api.get('/supplier/' + id);
    }

    updateSupplier({id, company, person}){
        return api.put('/supplier/' + id, {
            person,
            company,
        })
    }

    deleteSupplier({id}){
        return api.delete('/supplier/' + id);
    }
}

export default new SupplierService();