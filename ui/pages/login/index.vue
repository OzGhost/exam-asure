<template>
    <div class="leaf logbox">
        <h3>Sign-in</h3>
        <form @submit.prevent="doLogin">
            <input type="text" v-model="username" />
            <input type="password" v-model="password" />
            <button type="submit">Submit</button>
        </form>
    </div>
</template>
<script>
export default {
    data() {
        return {
            username: 'admin',
            password: '2zh2f743'
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
<style>
.logbox {
    margin: 45vh auto 0;
    transform: translateY(-50%);
    background: #723d46;
    max-width: 325px;
}
.logbox * {
    color: white;
    text-align: center;
}
.logbox button {
    color: white;
    background: #e26d5c;
    border-color: #e26d5c;
    margin: auto;
    display: block;
}
.logbox input {
    width: 100%;
    box-sizing: border-box;
}
</style>
