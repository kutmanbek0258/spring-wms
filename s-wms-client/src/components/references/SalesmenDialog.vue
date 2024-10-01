<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('salesman', ['current', 'pageSize', 'totalCount', 'salesmen']),
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                { title: 'Full name ', key: 'person.fullName', },
                { title: 'Company', key: 'company.name' },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
        }
    },
    created(){
        this.getAllSalesmen({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('salesman', ['getAllSalesmen', 'handleSelectSalesman']),

        loadItems({ page, itemsPerPage, sortBy }) {
            this.getAllSalesmen({ current: page, pageSize: itemsPerPage, sortBy })
        },

        reload() {
            this.getAllSalesmen({ current: this.current, pageSize: this.pageSize, sortBy: this.sortBy })
        },

        handleSelectItem(event, row){
            this.handleSelectSalesman({id: row.item.id, name: row.item.person.fullName})
        },
    }
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='salesmen'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row='handleSelectItem'>
            <template v-slot:no-data>
                <v-btn
                    color="primary"
                    @click="reload"
                >
                    Reset
                </v-btn>
            </template>

        </v-data-table-server>
    </v-card>
</template> 
