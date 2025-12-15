const vuetify = new Vuetify({ theme: { defaultTheme: 'light' }, });
const app = new Vue({
    el: '#app',
    vuetify: vuetify,
    data() {
        return {
            open:["x"],
            navigations: []
        }
    },
    mounted() {
        let _this = this;
        Common.initNavigations(/*[[@{/}]]*/'/', function(response) {
            _this.navigations = response.data.data;
        });
    }
});