<script>
import { defineComponent, ref } from 'vue';
import {mapActions, mapState} from "vuex";
import LoginService from "@/services/login.service";
const checkbox = ref(true);

export default defineComponent({
    data() {
        return {
            // Binded model property for "Sign In Form" switch button for "Remember Me" .
            email: '',
            password: '',
            submitted: false,
            rememberMe: true,
        }
    },
    computed: {
        ...mapState('auth', ['status']),
        ...mapState('company', ['pageSize', 'current']),
    },
    // beforeCreate() {
    // 	// Creates the form and adds to it component's "form" property.
    // 	this.form = this.$form.createForm(this, { name: 'normal_login' });
    // },
    created () {
        // reset login status
        this.logout();
    },
    methods: {
        // Handles input validation after submission.
        ...mapActions('auth', ['login', 'logout']),
        ...mapActions('company', ['getAllCompanies']),
        handleSubmit() {
            LoginService.login();
            // this.submitted = true;
            // const { email, password } = this;
            // // console.log("Submitted " + email)
            // if (email && password) {
            //   this.login({ email, password })
            // }
        },
        getCompanies(){
            this.getAllCompanies({ current: this.current, pageSize: this.pageSize });
        }
    },
})

</script>

<template>
    <v-row class="d-flex mb-3">
        <v-col cols="12">
            <v-label class="font-weight-bold mb-1">Username</v-label>
            <v-text-field variant="outlined" hide-details color="primary"></v-text-field>
        </v-col>
        <v-col cols="12">
            <v-label class="font-weight-bold mb-1">Password</v-label>
            <v-text-field variant="outlined" type="password"  hide-details color="primary"></v-text-field>
        </v-col>
        <v-col cols="12" class="pt-0">
            <div class="d-flex flex-wrap align-center ml-n2">
                <v-checkbox v-model="checkbox"  color="primary" hide-details>
                    <template v-slot:label class="text-body-1">Remeber this Device</template>
                </v-checkbox>
                <div class="ml-sm-auto">
                    <RouterLink to="/"
                        class="text-primary text-decoration-none text-body-1 opacity-1 font-weight-medium">Forgot
                        Password ?</RouterLink>
                </div>
            </div>
        </v-col>
        <v-col cols="12" class="pt-0">
            <v-btn @click='handleSubmit' color="primary" size="large" block   flat>Sign in</v-btn>
        </v-col>

        <v-col cols="12" class="pt-0">
            <v-btn @click='getCompanies' color="primary" size="large" block   flat>Sign in</v-btn>
        </v-col>
    </v-row>
</template>
