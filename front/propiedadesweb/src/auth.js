import { reactive } from 'vue';

export const auth = reactive({
    isLoggedIn: localStorage.getItem('isLoggedIn') === 'true',

    login() {
        this.isLoggedIn = true;
        localStorage.setItem('isLoggedIn', 'true');
    },

    logout() {
        this.isLoggedIn = false;
        localStorage.setItem('isLoggedIn', 'false');
    }
});
