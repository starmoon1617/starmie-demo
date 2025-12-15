const { createApp } = Vue;
const { createVuetify, components, directives } = Vuetify;
const { loadModule } = window['vue3-sfc-loader'];

const options = {
    moduleCache: {
        vue: Vue
    },
    async getFile(url) {
        const res = await fetch(url);
        if (!res.ok)
            throw Object.assign(new Error(res.statusText + ' ' + url), { res });
        return {
            getContentData: asBinary => asBinary ? res.arrayBuffer() : res.text(),
        }
    },
    addStyle(textContent) {
        const style = Object.assign(document.createElement('style'), { textContent });
        const ref = document.head.getElementsByTagName('style')[0] || null;
        document.head.insertBefore(style, ref);
    },
    log(type, ...args) {
        console.info(type);
        console.info(args);
        console[type](...args);
    },
}
const app = createApp();

const routes = [
    { path: '/user', component: Vue.defineAsyncComponent(() => loadModule(/*[[@{/user/toList.vue}]]*/'/user/toList.vue', options)) },
    { path: '/menu', component: Vue.defineAsyncComponent(() => loadModule(/*[[@{/navigation_menu/toList.vue}]]*/'/navigation_menu/toList.vue', options)) },
]
//
const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes,
});
app.use(createVuetify({ theme: { defaultTheme: 'light' }, components, directives })).use(router).mount('#app');
