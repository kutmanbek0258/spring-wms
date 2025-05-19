import api from "./api";

class PriceService{
    createPrice({shopID}){
        return api.post('/price', {
            shopID
        })
    };

    getAllPrice({take, skip}){
        return api.post('/price/get-all', {
            take,
            skip
        })
    };

    getPriceById({priceID}){
        return api.get('/price/' + priceID)
    };

    updatePrice({priceID, shopID}){
        return api.patch('/price/' + priceID, {
            shopID
        })
    };

    deletePrice({priceID}){
        return api.delete('/price/' + priceID);
    };

    addItem({priceID, productID, retail_price}){
        return api.post('/price/add-item', {
            priceID,
            productID,
            retail_price,
        })
    };

    getAllItems({priceID}){
        return api.get('/price/get-all-items/' + priceID);
    };

    updateItem({itemID, productID, retail_price}){
        return api.patch('/price/update-item/' + itemID, {
            productID,
            retail_price,
        })
    };

    deleteItem({itemID}){
        return api.delete('/price/delete-item/' + itemID);
    }
}

export default new PriceService();