import { reactive } from 'vue';

export const auth = reactive({
    isLoggedIn: localStorage.getItem('isLoggedIn') === 'true',
    id: localStorage.getItem('id') || null,
    rol: localStorage.getItem('rol') || null,

    login(id, rol) {
        this.isLoggedIn = true;
        this.id = id;
        this.rol = rol;
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userId', id);
        localStorage.setItem('userRole', rol);
    },

    logout() {
        this.isLoggedIn = false;
        this.id = null;
        this.rol = null;
        localStorage.setItem('isLoggedIn', 'false');
        localStorage.removeItem('id');
        localStorage.removeItem('rol');
    }
});
