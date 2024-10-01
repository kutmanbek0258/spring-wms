import api from './api';

class ReceiptService {
    createReceipt({supplierID, shopID, depotID}){
        return api.post('/receipt', {
            supplierID,
            shopID,
            depotID
        })
    }

    getAllReceipts({page, size}){
        const params = { page, size }
        return api.get('/receipt/page-query', { params })
    }

    getReceiptById({id}){
        return api.get('/receipt/' + id);
    }

    updateReceipt({id, supplierID, shopID, depotID}){
        return api.put('/receipt/' + id, {
            supplierID,
            shopID,
            depotID
        })
    }

    deleteReceipt({id}){
        return api.delete('/receipt/' + id);
    }

    createAndFillReceiptDocumentByReceipt({receiptID}){
        console.log(receiptID)
        return api.post('/receipt/create-and-fill-price-by-receipt/' + receiptID);
    }

    addReceiptItem({receiptID, productID, quantity, price}){
        const receipt = { receiptID }
        const product = { productID }
        return api.post('/receipt-item', {
            receipt,
            product,
            quantity,
            price
        })
    }

    getAllReceiptItems({receiptID, sortBy, order}){
        return api.post('/receipt-item/get-all-items/' + receiptID, {
            sortBy,
            order
        });
    }

    updateReceiptItem({itemID, productID, quantity, price}){
        return api.patch('/receipt/update-item/' + itemID, {
            productID,
            quantity,
            price
        })
    }

    deleteReceiptItem({itemID}){
        return api.delete('/receipt/delete-item/' + itemID)
    }
}

export default new ReceiptService();