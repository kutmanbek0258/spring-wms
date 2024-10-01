<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('priceTemplate', ['current', 'pageSize', 'totalCount', 'priceTemplates']),
        formTitle () {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
        },
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                {
                    title: 'Name ',
                    align: 'start',
                    sortable: true,
                    key: 'name',
                },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
        }
    },
    created(){
        this.getAllPriceTemplates({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('priceTemplate', ['getAllPriceTemplates', 'handleSelectPriceTemplate']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllPriceTemplates({current: page, pageSize: itemsPerPage, sortBy})
        },

        handleSelectItem(event, row){
            this.handleSelectPriceTemplate({id: row.item.id, name: row.item.name})
        }
    }
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='priceTemplates'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row='handleSelectItem'/>
    </v-card>
</template> 
