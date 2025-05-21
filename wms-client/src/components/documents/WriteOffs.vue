<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';
import DepotsDialog from '@/components/references/DepotsDialog.vue';

export default defineComponent({
    components: { DepotsDialog },
    computed: {
        ...mapState('writeOff', ['current', 'pageSize', 'totalCount', 'writeOffs']),
        ...mapState('depot', ['selectedDepot', 'dialogVisibleDepot']),
        formTitle () {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
        },
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                { title: 'Depot', key: 'depot.name', },
                { title: 'Actions', key: 'actions', sortable: false },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                id: 0,
                depot: {},
            },
            defaultItem: {
                id: 0,
                depot: {},
            },
        }
    },
    created(){
        this.getAllWriteOffs({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('writeOff', ['getAllWriteOffs', 'createWriteOff', 'updateWriteOff', 'deleteWriteOff']),
        ...mapActions('depot', ['showDepotDialog', 'handleSelectDepot']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllWriteOffs({current: page, pageSize: itemsPerPage, sortBy})
        },

        reload(){
            this.getAllWriteOffs({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
        },

        editItem (item) {
            this.editedIndex = this.writeOffs.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            this.editedIndex = this.writeOffs.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialogDelete = true
        },

        deleteItemConfirm () {
            this.deleteReceipt({id: this.editedItem.id})
            this.closeDelete()
        },

        close () {
            this.dialog = false
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            })
        },

        closeDelete () {
            this.dialogDelete = false
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            })
        },

        save () {
            if (this.editedIndex > -1) {
                this.updateReceipt({
                    id: this.editedItem.id,
                    depot: this.editedItem.depot
                })
            } else {
                this.createReceipt({
                    supplier: this.editedItem.supplier,
                    depot: this.editedItem.depot
                })
            }

            this.close()
        },
    },
    watch: {
        dialog (val) {
            val || this.close()
        },
        dialogDelete (val) {
            val || this.closeDelete()
        },
        selectedDepot(val){
            this.editedItem.depot = (val.name === "" ) ? this.editedItem.depot : val;
        },
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-dialog
            v-model="dialogVisibleDepot"
            width="auto"
        >
            <v-card
                max-width="400"
                text="Select depot"
            >
                <DepotsDialog/>
                <template v-slot:actions>
                    <v-btn
                        class="ms-auto"
                        text="Ok"
                        @click="handleSelectDepot"
                    ></v-btn>
                </template>
            </v-card>
        </v-dialog>
        <v-data-table-server
            :items='writeOffs'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'>

            <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>Write Off list</v-toolbar-title>
                    <v-divider
                        class="mx-4"
                        inset
                        vertical
                    ></v-divider>
                    <v-spacer></v-spacer>
                    <v-dialog
                        v-model="dialog"
                        max-width="1000px"
                    >
                        <template v-slot:activator="{ props }">
                            <v-btn
                                class="mb-2"
                                color="primary"
                                dark
                                v-bind="props"
                            >
                                New Item
                            </v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="text-h5">{{ formTitle }}</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                            @click='showDepotDialog'
                                        >
                                            <v-text-field
                                                v-model="editedItem.depot.name"
                                                label="depot"
                                            ></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="close"
                                >
                                    Cancel
                                </v-btn>
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="save"
                                >
                                    Save
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                    <v-dialog v-model="dialogDelete" max-width="500px">
                        <v-card>
                            <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
                                <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                                <v-spacer></v-spacer>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:item.actions="{ item }">
                <v-icon
                    class="me-2"
                    size="small"
                    @click="editItem(item)"
                >
                    mdi-pencil
                </v-icon>
                <v-icon
                    size="small"
                    @click="deleteItem(item)"
                >
                    mdi-delete
                </v-icon>
            </template>
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
