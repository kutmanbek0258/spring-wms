import { createRouter, createWebHistory } from 'vue-router';
import MainRoutes from './MainRoutes';
import AuthRoutes from './AuthRoutes';
import LoginService from '@/services/login.service'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/views/pages/Error404.vue')
        },
        MainRoutes,
        AuthRoutes
    ]
});

router.beforeEach((to, from, next) => {
    // redirect to login page if not logged in and trying to access a restricted page
    const publicPages = ['/code', '/auth/login'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('loggedIn');

    if(!loggedIn && authRequired){
        next({name: 'Login'});
    }

    if (to.path === '/code' && to.query.code != null) {
        LoginService.getTokens(to.query.code).then(() => {
            console.log('Logged in!');
        });
    }

    next();
});

export default router;

