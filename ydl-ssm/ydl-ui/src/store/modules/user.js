import { login, logout, getInfo } from '@/api/user.js'
import storage from '@/util/storage.js'

const user = {
    state: {
        username: '',
        nickname: '',
        token: '',
        roles: [],
        permissions: []
    },
    getters: {
        isLogin(state) {
            return state.username !== '' && state.token !== '';
        },
        permissions(state) {
            return state.permissions;
        },
        roles(state) {
            return state.roles;
        }
    },
    mutations: {
        SAVE_USERNAME(state, username) {
            state.username = username;
        },
        SAVE_NICKNAME(state, nickname) {
            state.nickname = nickname;
        },
        SAVE_TOKEN(state, token) {
            state.token = token;
        },
        SAVE_ROLES(state, roles) {
            state.roles = roles;
        },
        SAVE_PERMISSIONS(state, permissions) {
            state.permissions = permissions;
        },

    },
    actions: {
        LOGIN({ commit }, user) {
            return new Promise(function (resolve) {
                login(user).then(res => {
                    // 需要将获取的数据，保存起来
                    commit("SAVE_USERNAME", res.data.ydlUser.userName);
                    commit("SAVE_NICKNAME", res.data.ydlUser.nickName);
                    commit("SAVE_TOKEN", res.data.token);
                    storage.saveSessionObject("loginUser", res.data);
                    resolve(res);
                })
            })
        },
        GET_INFO({ commit }) {
            return new Promise(resolve => {
                getInfo().then(res => {
                    commit("SAVE_ROLES", res.data.roles);
                    commit("SAVE_PERMISSIONS", res.data.perms);
                    resolve();
                })
            })
        },
        LOGOUT({ commit }) {
            return new Promise(function (resolve) {
                logout().then(res => {
                    // 需要将获取的数据，保存起来
                    commit("SAVE_USERNAME", '');
                    commit("SAVE_NICKNAME", '');
                    commit("SAVE_TOKEN", '');
                    storage.remove("loginUser");
                    resolve(res);
                })
            })
        },
        RECOVERY_USER({ commit }) {
            // 从storage中获取数据
            let loginUser = storage.getSessionObject("loginUser");
            if (loginUser) {
                commit("SAVE_USERNAME", loginUser.ydlUser.userName);
                commit("SAVE_NICKNAME", loginUser.ydlUser.nickName);
                commit("SAVE_TOKEN", loginUser.token);
            }

        }
    }
}

export default user