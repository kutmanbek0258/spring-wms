<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';
import CompaniesDialog from '@/components/references/CompaniesDialog.vue';
import PersonsDialog from '@/components/references/PersonsDialog.vue';

export default defineComponent({
    components: { PersonsDialog, CompaniesDialog },
    computed: {
        ...mapState('supplier', ['current', 'pageSize', 'totalCount', 'suppliers']),
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
                { title: 'Full name ', key: 'person.fullName', },
                { title: 'Company', key: 'company.name' },
                { title: 'Actions', key: 'actions', sortable: false },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                id: 0,
                person: {},
                company: {},
            },
            defaultItem: {
                id: 0,
                person: {},
                company: {},
            },
        }
    },
    created(){
        this.getAllSuppliers({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('supplier', ['getAllSuppliers', 'createSupplier', 'updateSupplier', 'deleteSupplier']),
        ...mapActions('company', ['setDialogVisibleCompany', 'handleCloseSelectionCompany']),
        ...mapActions('person', ['setDialogVisiblePerson', 'handleCloseSelectionPerson']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllSuppliers({current: page, pageSize: itemsPerPage, sortBy})
        },

        reload(){
            this.getAllSuppliers({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
        },

        editItem (item) {
            this.editedIndex = this.suppliers.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            this.editedIndex = this.suppliers.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialogDelete = true
        },

        deleteItemConfirm () {
            this.deleteSupplier({id: this.editedItem.id})
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
                this.updateSupplier({
                    id: this.editedItem.id,
                    company: this.editedItem.company,
                    person: this.editedItem.person
                })
            } else {
                this.createSupplier({
                    company: this.editedItem.company,
                    person: this.editedItem.person
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
            this.editedItem.person = (val.fullName === "") ? this.editedItem.person : val;
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
            :items='suppliers'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'>

            <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>Suppliers</v-toolbar-title>
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
                                                v-model="editedItem.person.fullName"
                                                label="Person"
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
