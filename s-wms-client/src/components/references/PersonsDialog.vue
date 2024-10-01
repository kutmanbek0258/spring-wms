<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('person', ['current', 'pageSize', 'totalCount', 'persons']),
        formTitle () {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
        },
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                {
                    title: 'Full name',
                    align: 'start',
                    sortable: true,
                    key: 'fullName',
                },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
        }
    },
    created(){
        this.getAllPersons({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('person', ['getAllPersons', 'handleSelectPerson']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllPersons({current: page, pageSize: itemsPerPage, sortBy})
        },

        handleClickItem(event, row){
            this.handleSelectPerson({id: row.item.id, fullName: row.item.fullName})
        }
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='persons'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row="handleClickItem"/>
    </v-card>
</template> 
