<script>
import { mapActions, mapState } from 'vuex';
import { defineComponent } from 'vue';
import ProductGroupsDialog from '@/components/references/ProductGroupsDialog.vue';
import PriceTemplatesDialog from '@/components/references/PriceTemplatesDialog.vue';

export default defineComponent({
    components: { ProductGroupsDialog, PriceTemplatesDialog },
    computed: {
        ...mapState('product', ['current', 'pageSize', 'totalCount', 'product', 'products']),
        ...mapState('productGroup', ['selectedProductGroup', 'dialogVisibleProductGroup']),
        ...mapState('priceTemplate', ['selectedPriceTemplate', 'dialogVisiblePriceTemplate']),
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
                { title: 'Description', key: 'description' },
                { title: 'Quantity', key: 'quantity' },
                { title: 'Price', key: 'price' },
                { title: 'Cost', key: 'cost' },
                { title: 'Actions', key: 'actions', sortable: false },
            ],
            sortBy: [{ key: 'id', order: 'desc' }],
            searchKeyword: "",
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                id: 0,
                name: '',
                description: '',
                barcode: '',
                productGroup: {},
                priceTemplate: {},
                quantity: '',
                price: '',
                cost: '',
            },
            defaultItem: {
                id: 0,
                name: '',
                description: '',
                barcode: '',
                productGroup: {},
                priceTemplate: {},
                quantity: '',
                price: '',
                cost: '',
            },
        }
    },
    created(){
        this.getAllProducts({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
    },
    methods: {
        ...mapActions('product', ['getAllProducts', 'getProductById', 'createProduct', 'updateProduct', 'deleteProduct']),
        ...mapActions('productGroup', ['visibleDialogProductGroup', 'handleCloseSelectionProductGroup']),
        ...mapActions('priceTemplate', ['visibleDialogPriceTemplate', 'handleCloseSelectionPriceTemplate']),

        loadItems ({ page, itemsPerPage, sortBy }) {
            const searchKeyword = this.searchKeyword;
            if(searchKeyword === ""){
                this.getAllProducts({current: page, pageSize: itemsPerPage, sortBy});
            }else {
                this.getAllProducts({current: page, pageSize: itemsPerPage, sortBy, searchKeyword})
            }
        },

        searchProducts(){
            const searchKeyword = this.searchKeyword;
            if(searchKeyword === ""){
                this.getAllProducts({current: this.current, pageSize: this.pageSize, sortBy: this.sortBy})
            }else {
                this.getAllProducts({current: 1, pageSize: this.pageSize, sortBy: this.sortBy, searchKeyword})
            }
        },

        editItem (item) {
            this.editedIndex = this.products.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            this.editedIndex = this.products.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialogDelete = true
        },

        deleteItemConfirm () {
            this.deleteProduct({id: this.editedItem.id})
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
                this.updateProduct({
                    id: this.editedItem.id,
                    name: this.editedItem.name,
                    description: this.editedItem.description,
                    barcode: this.editedItem.barcode,
                    productGroup: this.editedItem.productGroup,
                    priceTemplate: this.editedItem.priceTemplate
                })
            } else {
                this.createProduct({
                    name: this.editedItem.name,
                    description: this.editedItem.description,
                    barcode: this.editedItem.barcode,
                    productGroup: this.editedItem.productGroup,
                    priceTemplate: this.editedItem.priceTemplate
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
        selectedProductGroup(val){
            console.log(val);
            this.editedItem.productGroup = (val.name === "") ? this.editedItem.productGroup : val;
        },
        selectedPriceTemplate(val){
            this.editedItem.priceTemplate = (val.name === "") ? this.editedItem.priceTemplate : val;
        }
    },
})
</script>

<template>
    <v-card elevation="10" class="withbg">
        <v-dialog
            v-model="dialogVisibleProductGroup"
            width="auto"
        >
            <v-card
                max-width="400"
                text="Select group"
            >
                <ProductGroupsDialog/>
                <template v-slot:actions>
                    <v-btn
                        class="ms-auto"
                        text="Ok"
                        @click="handleCloseSelectionProductGroup"
                    ></v-btn>
                </template>
            </v-card>
        </v-dialog>
        <v-dialog
            v-model="dialogVisiblePriceTemplate"
            width="auto"
        >
            <v-card
                max-width="400"
                text="Select price template"
            >
                <PriceTemplatesDialog/>
                <template v-slot:actions>
                    <v-btn
                        class="ms-auto"
                        text="Ok"
                        @click="handleCloseSelectionPriceTemplate"
                    ></v-btn>
                </template>
            </v-card>
        </v-dialog>
        <v-data-table-server
            :items='products'
            :headers='headers'
            :items-per-page='pageSize'
            :items-length='totalCount'
            :sort-by='sortBy'
            @update:options='loadItems'>

            <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>Products</v-toolbar-title>
                    <v-divider
                        class="mx-4"
                        inset
                        vertical
                    ></v-divider>
                    <v-spacer></v-spacer>
                    <v-text-field
                        v-model="searchKeyword"
                        append-inner-icon="mdi-magnify"
                        density="compact"
                        label="Search products"
                        variant="solo"
                        hide-details
                        single-line
                        @click:append-inner="searchProducts"
                        @keydown.enter="searchProducts"
                    ></v-text-field>
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
                                                v-model="editedItem.barcode"
                                                label="Barcode"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                            @click='visibleDialogProductGroup'
                                        >
                                            <v-text-field
                                                v-model="editedItem.productGroup.name"
                                                label="Group"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col
                                            cols="12"
                                            md="4"
                                            sm="6"
                                            @click='visibleDialogPriceTemplate'
                                        >
                                            <v-text-field
                                                v-model="editedItem.priceTemplate.name"
                                                label="Price template"
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
