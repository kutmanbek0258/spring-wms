<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('company', ['current', 'pageSize', 'totalCount', 'companies']),
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
        this.getAllCompanies({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('company', ['getAllCompanies', 'handleSelectCompany']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllCompanies({current: page, pageSize: itemsPerPage, sortBy})
        },

        handleClickItem(event, row){
            this.handleSelectCompany({id: row.item.id, name: row.item.name})
        }
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='companies'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row="handleClickItem"/>
    </v-card>
</template> 
