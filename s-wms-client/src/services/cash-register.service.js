import api from "./api";

class CashRegisterService{
    createCashRegister({shopID, posID, ofd, printer}){
        return api.post('/cash-register', {
            shopID,
            posID,
            ofd,
            printer,
        })
    }

    getAllCashRegisters({take, skip}){
        return api.post('/cash-register/get-all', {
            take,
            skip,
        })
    }

    getCashRegisterById({id}){
        return api.get('/cash-register/' + id);
    }

    updateCashRegister({id, shopID, posID, ofd, printer}){
        return api.patch('/cash-register/' + id, {
            shopID,
            posID,
            ofd,
            printer,
        })
    }

    deleteCashRegister({id}){
        return api.delete('/cash-register/' + id);
    }
}

export default new CashRegisterService();