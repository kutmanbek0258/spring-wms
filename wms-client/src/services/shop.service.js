import api from "./api";

class ShopService{
    createShop({name, description, address, companyID, managerID, depotID}){
        return api.post('/shop', {
            name,
            description,
            address,
            companyID,
            managerID,
            depotID,
        });
    };

    getAllShops({take, skip}){
        return api.post('/shop/get-all', {
            take,
            skip,
        });
    };

    getShopById({id}){
        return api.get('/shop/' + id);
    };

    updateShop({id, name, description, address, companyID, managerID, depotID}){
        return api.patch('/shop/' + id, {
            name,
            description,
            address,
            companyID,
            managerID,
            depotID,
        });
    };

    deleteShop({id}){
        return api.delete('/shop/' + id);
    }
}

export default new ShopService();