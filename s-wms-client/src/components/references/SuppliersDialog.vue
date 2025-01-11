<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';

export default defineComponent({
    computed: {
        ...mapState('supplier', ['current', 'pageSize', 'totalCount', 'suppliers']),
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
        this.getAllSuppliers({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('supplier', ['getAllSuppliers', 'handleSelectSupplier']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllSuppliers({current: page, pageSize: itemsPerPage, sortBy})
        },

        reload(){
            this.getAllSuppliers({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
        },

        handleClickItem(event, row){
            this.handleSelectSupplier({id: row.item.id, person: row.item.person})
        }
    }
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-data-table-server
            :items='suppliers'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'
            @click:row='handleClickItem'>

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
