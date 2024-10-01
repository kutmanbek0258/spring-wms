<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('productGroup', ['current', 'pageSize', 'totalCount', 'productGroups']),
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
        this.getAllProductGroups({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('productGroup', ['getAllProductGroups', 'handleSelectProductGroup']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllProductGroups({current: page, pageSize: itemsPerPage, sortBy})
        },

        handleSelectItem(event, row){
            this.handleSelectProductGroup({id: row.item.id, name: row.item.name});
        }
    }
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='productGroups'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row='handleSelectItem'/>
    </v-card>
</template> 
