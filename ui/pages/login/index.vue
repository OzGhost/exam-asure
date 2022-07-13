<template>
    <div>
        <input type="text" v-model="username" />
        <input type="password" v-model="password" />
        <button @click="doLogin">Login</button>
    </div>
</template>
<script>
export default {
    data() {
        return {
            username: 'admin',
            password: 'h4h27cfc'
        };
    },
    methods: {
        doLogin() {
            let payload = {
                username: this.username,
                password: this.password
            };
            this.$axios.$post("/accounts/tokens", payload)
                .then(rs => {
                    this.$store.commit("meta/loadToken", { token: rs.token, role: rs.role });
                    let des = "";
                    switch (rs.role) {
                        case "ADMIN":
                            des = "/admin"; break;
                        case "MENTOR":
                            des = "/students"; break;
                        default:
                            des = "/mentors"; break;
                    }
                    this.$router.push(des);
                });
        }
    }
}
</script>
