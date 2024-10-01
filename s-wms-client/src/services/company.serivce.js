import api from './api';

class CompanyService {
  createCompany({name, description, address, phone, email}){
    return api.post('/company', {
      name,
      description,
      address,
      phone,
      email
    })
  }

  getAllCompanies({page, size, sort}){
    const params = { page, size, sort }
    return api.get('/company/page-query', { params });
  }

  getCompanyById({id}){
    return api.get('/company/' + id);
  }

  updateCompany({id, name, description, address, phone, email}){
    return api.put('/company/' + id, {
      name,
      description,
      address,
      phone,
      email
    })
  }

  deleteCompany({id}){
    return api.delete('/company/' + id);
  }
}

export default new CompanyService();