import { createApp } from "vue";
import { createWebHistory, createRouter } from "vue-router";

// styles

import "@fortawesome/fontawesome-free/css/all.min.css";
import "../../src/assets/styles/tailwind.css";

// mouting point for the whole app

import App from "App.vue";

// layouts

import Admin from "layouts/Admin.vue";
import Auth from "layouts/Auth.vue";

// views for Admin layout

import Dashboard from "admin/Dashboard.vue";
import Settings from "admin/Settings.vue";
import Tables from "admin/Tables.vue";
import Maps from "admin/Maps.vue";

// views for Auth layout

import Login from "auth/Login.vue";
import Register from "auth/Register.vue";

// views without layouts

import Landing from "Landing.vue";
import Profile from "Profile.vue";
import Index from "Index.vue";


const routes = [
    {
        path: "/admin",
        redirect: "/admin/dashboard",
        component: Admin,
        children: [
            {
                path: "/admin/dashboard",
                component: Dashboard,
            },
            {
                path: "/admin/settings",
                component: Settings,
            },
            {
                path: "/admin/tables",
                component: Tables,
            },
            {
                path: "/admin/maps",
                component: Maps,
            },
        ],
    },
    {
        path: "/auth",
        redirect: "/auth/login",
        component: Auth,
        children: [
            {
                path: "/auth/login",
                component: Login,
            },
            {
                path: "/auth/register",
                component: Register,
            },
        ],
    },
    {
        path: "/landing",
        component: Landing,
    },
    {
        path: "/profile",
        component: Profile,
    },
    {
        path: "/",
        component: Index,
    },
    { path: "/:pathMatch(.*)*", redirect: "/" },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
