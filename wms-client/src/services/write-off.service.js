import api from './api';

class WriteOffService {
    createWriteOff({shopID, depotID}){
        return api.post('/write-off', {
            shopID,
            depotID
        })
    }

    getAllWriteOffs({take, skip}){
        return api.post('/write-off/get-all', {
            take,
            skip
        })
    }

    getWriteOffById({id}){
        return api.get('/write-off/' + id);
    }

    updateWriteOff({id, shopID, depotID}){
        return api.patch('/write-off/' + id, {
            shopID,
            depotID
        })
    }

    deleteWriteOff({id}){
        return api.delete('/write-off/' + id);
    }

    addWriteOffItem({writeOffID, productID, quantity, price}){
        return api.post('/write-off/add-item', {
            writeOffID,
            productID,
            quantity,
            price
        })
    }

    getAllWriteOffItems({writeOffID}){
        return api.get('/write-off/get-all-items/' + writeOffID);
    }

    updateWriteOffItem({itemID, productID, quantity, price}){
        return api.patch('/write-off/update-item/' + itemID, {
            productID,
            quantity,
            price
        })
    }

    deleteWriteOffItem({itemID}){
        return api.delete('/write-off/delete-item/' + itemID)
    }
}

export default new WriteOffService();