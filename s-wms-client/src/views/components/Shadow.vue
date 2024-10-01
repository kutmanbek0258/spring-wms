<script setup lang="ts">
import Shadow from "@/components/style-components/shadow/Shadow.vue";
import Products from '@/components/references/Products.vue';
import Suppliers from '@/components/references/Suppliers.vue';
import SuppliersDialog from '@/components/references/SuppliersDialog.vue';
import Salesmen from '@/components/references/Salesmen.vue';
import SalesmenDialog from '@/components/references/SalesmenDialog.vue';
import PriceTemplates from "@/components/references/PriceTemplates.vue";
import ProductGroups from "@/components/references/ProductGroups.vue";

</script>

<template>
    <v-row>
        <v-col cols="12">
            <Products/>
        </v-col>
        <v-col cols="12">
            <ProductGroups/>
        </v-col>
        <v-col cols="12">
            <PriceTemplates/>
        </v-col>
    </v-row>
</template> 
