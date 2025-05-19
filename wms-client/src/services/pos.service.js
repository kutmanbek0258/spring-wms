import api from "./api";

class PosService{
    createPos({name, shopID}){
        const workspace = this.getMachineId();
        return api.post('/pos', {
            name,
            shopID,
            workspace
        })
    }

    getAllPoses({take, skip}){
        return api.post('/pos/get-all', {
            take,
            skip,
        })
    }

    getPosById({id}){
        return api.get('/pos/' + id);
    }

    updatePos({id, name, shopID}){
        const workspace = this.getMachineId();
        return api.patch('/pos/' + id, {
            name,
            shopID,
            workspace
        })
    }

    deleteShop({id}){
        return api.delete('/pos/' + id);
    }

    getMachineId() {

        let machineId = localStorage.getItem('MachineId');

        if (!machineId) {
            machineId = crypto.randomUUID();
            localStorage.setItem('MachineId', machineId);
        }

        return machineId;
    }
}

export default new PosService();