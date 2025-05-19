<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';
import CompaniesDialog from '@/components/references/CompaniesDialog.vue';
import PersonsDialog from '@/components/references/PersonsDialog.vue';

export default defineComponent({
    components: { PersonsDialog, CompaniesDialog },
    computed: {
        ...mapState('depot', ['current', 'pageSize', 'totalCount', 'depots']),
        ...mapState('company', ['selectedCompany', 'dialogVisibleCompany']),
        ...mapState('person', ['selectedPerson', 'dialogVisiblePerson']),
        formTitle () {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
        },
    },
    data() {
        return {
            headers: [
                { title: 'ID', key: 'id', align: 'start' },
                { title: 'Name ', key: 'name', },
                { title: 'Description', key: 'description' },
                { title: 'Address', key: 'address' },
                { title: 'Company', key: 'company.name' },
                { title: 'Manager', key: 'manager.fullName' },
                { title: 'Actions', key: 'actions', sortable: false },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                id: 0,
                name: '',
                description: '',
                address: '',
                company: {},
                manager: {},
            },
            defaultItem: {
                id: 0,
                name: '',
                description: '',
                address: '',
                company: {},
                manager: {},
            },
        }
    },
    created(){
        this.getAllDepots({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('depot', ['getAllDepots', 'createDepot', 'updateDepot', 'deleteDepot']),
        ...mapActions('company', ['setDialogVisibleCompany', 'handleCloseSelectionCompany']),
        ...mapActions('person', ['setDialogVisiblePerson', 'handleCloseSelectionPerson']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllDepots({current: page, pageSize: itemsPerPage, sortBy})
        },

        editItem (item) {
            this.editedIndex = this.depots.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            this.editedIndex = this.depots.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialogDelete = true
        },

        deleteItemConfirm () {
            console.log(this.editedItem.id);
            this.deleteDepot({id: this.editedItem.id})
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
                this.updateDepot({
                    id: this.editedItem.id,
                    name: this.editedItem.name,
                    description: this.editedItem.description,
                    address: this.editedItem.address,
                    company: this.editedItem.company,
                    manager: this.editedItem.manager
                })
            } else {
                this.createDepot({
                    name: this.editedItem.name,
                    description: this.editedItem.description,
                    address: this.editedItem.address,
                    company: this.editedItem.company,
                    manager: this.editedItem.manager
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
        selectedCompany(val){
            this.editedItem.company = (val.name === "" ) ? this.editedItem.company : val;
        },
        selectedPerson(val){
            this.editedItem.manager = (val.fullName === "") ? this.editedItem.manager : val;
        }
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-dialog
            v-model="dialogVisibleCompany"
            width="auto"
        >
            <v-card
                max-width="400"
                text="Select company"
            >
                <CompaniesDialog/>
                <template v-slot:actions>
                    <v-btn
                        class="ms-auto"
                        text="Ok"
                        @click="handleCloseSelectionCompany"
                    ></v-btn>
                </template>
            </v-card>
        </v-dialog>
        <v-dialog
            v-model="dialogVisiblePerson"
            width="auto"
        >
            <v-card
                max-width="400"
                text="Select manager"
            >
                <PersonsDialog/>
                <template v-slot:actions>
                    <v-btn
                        class="ms-auto"
                        text="Ok"
                        @click="handleCloseSelectionPerson"
                    ></v-btn>
                </template>
            </v-card>
        </v-dialog>
        <v-data-table-server
            :items='depots'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'>

            <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>Depots</v-toolbar-title>
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
                                        >
                                            <v-text-field
                                                v-model="editedItem.name"
                                                label="Name"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                        >
                                            <v-text-field
                                                v-model="editedItem.description"
                                                label="Description"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                        >
                                            <v-text-field
                                                v-model="editedItem.address"
                                                label="Address"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                            @click='setDialogVisibleCompany'
                                        >
                                            <v-text-field
                                                v-model="editedItem.company.name"
                                                label="Company"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                            @click='setDialogVisiblePerson'
                                        >
                                            <v-text-field
                                                v-model="editedItem.manager.fullName"
                                                label="Manager"
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
                    @click="initialize"
                >
                    Reset
                </v-btn>
            </template>

        </v-data-table-server>
    </v-card>
</template> 
