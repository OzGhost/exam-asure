
export const state = () => ({
    ready: false,
    token: "",
    role: ""
})

export const getters = {
    isReady(state) {
        return state.landed
    },
    token(state) {
        return state.token;
    },
    role(state) {
        return state.role;
    }
}

export const mutations = {
    ready(state) {
        state.ready = true;
    },
    loadToken(state, payload) {
        state.token = payload.token;
        state.role = payload.role;
    }
}

export const actions = {
}

