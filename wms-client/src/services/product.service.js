import api from './api';

class ProductService{
    createProduct({name, description, barcode, productGroup, priceTemplate}){
        return api.post('/product', {
            name,
            description,
            barcode,
            productGroup,
            priceTemplate,
        })
    }

    getAllProducts({page, size, sort, searchKeyword}){
        const params = { page, size, sort, keyword: searchKeyword };
        return api.get('/product/page-query', { params });
    }

    getProductById({id}){
        return api.get('/product/' + id);
    }

    updateProduct({id, name, description, barcode, productGroup, priceTemplate}){
        return api.put('/product/' + id, {
            name,
            description,
            barcode,
            productGroup,
            priceTemplate,
        })
    }

    deleteProduct({id}){
        return api.delete('/product/' + id);
    }

    uploadProductsCsv({file}){
        const formData = new FormData();
        formData.append('file', file);
        return api.post('/product/upload-products-csv', formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
    }
}

export default new ProductService();