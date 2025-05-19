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
                    title: 'Full Name ',
                    align: 'start',
                    sortable: true,
                    key: 'fullName',
                },
                { title: 'Address', key: 'address' },
                { title: 'Phone', key: 'phone' },
                { title: 'email', key: 'email' },
                { title: 'Actions', key: 'actions', sortable: false },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                id: 0,
                fullName: '',
                address: 0,
                phone: 0,
                email: 0,
            },
            defaultItem: {
                id: 0,
                fullName: '',
                address: 0,
                phone: 0,
                email: 0,
            },
        }
    },
    created(){
        this.getAllPersons({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('person', ['getAllPersons', 'createPerson', 'updatePerson', 'deletePerson']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            this.getAllPersons({current: page, pageSize: itemsPerPage, sortBy})
        },

        reload(){
            this.getAllPersons({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
        },

        editItem (item) {
            console.log(item.id);
            this.editedIndex = this.persons.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            console.log(item.id);
            this.editedIndex = this.persons.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialogDelete = true
        },

        deleteItemConfirm () {
            console.log(this.editedItem.id);
            this.deletePerson({id: this.editedItem.id})
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
                this.updatePerson({
                    id: this.editedItem.id,
                    fullName: this.editedItem.fullName,
                    address: this.editedItem.address,
                    phone: this.editedItem.phone,
                    email: this.editedItem.email
                })
            } else {
                this.createPerson({
                    fullName: this.editedItem.fullName,
                    address: this.editedItem.address,
                    phone: this.editedItem.phone,
                    email: this.editedItem.email
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
            @update:options='loadItems'>

            <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>Persons</v-toolbar-title>
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
                                                v-model="editedItem.fullName"
                                                label="Full Name"
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
                                        >
                                            <v-text-field
                                                v-model="editedItem.phone"
                                                label="Phone"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                        >
                                            <v-text-field
                                                v-model="editedItem.email"
                                                label="Email"
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
