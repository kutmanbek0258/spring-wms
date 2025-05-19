import api from './api';

class ReceiptService {
    createReceipt({supplier, depot}){
        return api.post('/receipt', {
            supplier,
            depot
        })
    }

    getAllReceipts({page, size, sort}){
        const params = { page, size, sort }
        return api.get('/receipt/page-query', { params })
    }

    getReceiptById({id}){
        return api.get('/receipt/' + id);
    }

    updateReceipt({id, supplier, depot}){
        return api.put('/receipt/' + id, {
            supplier,
            depot
        })
    }

    deleteReceipt({id}){
        return api.delete('/receipt/' + id);
    }

    createAndFillReceiptDocumentByReceipt({receiptID}){
        console.log(receiptID)
        return api.post('/receipt/create-and-fill-price-by-receipt/' + receiptID);
    }

    addReceiptItem({receipt, product, quantity, price}){
        return api.post('/receipt-item', {
            receipt,
            product,
            quantity,
            price
        })
    }

    getAllReceiptItems({receiptId, page, size, sort}){
        const params = { page, size, sort, receiptId }
        return api.get('/receipt-item/page-query', { params });
    }

    updateReceiptItem({itemID, product, quantity, price}){
        return api.patch('/receipt-item/' + itemID, {
            product,
            quantity,
            price
        })
    }

    deleteReceiptItem({itemID}){
        return api.delete('/receipt-item/' + itemID)
    }
}

export default new ReceiptService();