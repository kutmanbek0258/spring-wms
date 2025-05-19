import api from './api';

class PersonService{
    createPerson({fullName, phone, email, address}){
        return api.post('/person', {
            fullName,
            phone,
            email,
            address
        })
    }

    getAllPersons({page, size, sort}){
        const params = { page, size, sort }
        return api.get('/person/page-query', { params })
    }

    getPersonById({id}){
        return api.get('/person/' + id);
    }

    updatePerson({id, fullName, phone, email, address}){
        return api.put('/person/' + id, {
            fullName,
            phone,
            email,
            address
        })
    }

    deletePerson({id}){
        return api.delete('/person/' + id);
    }
}

export default new PersonService();