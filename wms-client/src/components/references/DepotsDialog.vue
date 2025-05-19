<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('depot', ['current', 'pageSize', 'totalCount', 'depots']),
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                { title: 'Name ', key: 'name', },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
        }
    },
    created(){
        this.getAllDepots({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('depot', ['getAllDepots', 'handleSelectDepot']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllDepots({current: page, pageSize: itemsPerPage, sortBy})
        },

        handleSelectItem(event, row){
            this.handleSelectDepot({id: row.item.id, name: row.item.name});
        }
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='depots'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row='handleSelectItem'/>
    </v-card>
</template> 
