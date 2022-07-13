<template>
    <span>...</span>
</template>
<script>
export default {
    mounted() {
        this.$store.commit("meta/ready");
        this.$axios.interceptors.request.use(cfg => {
            let token = this.$store.state.meta.token;
            if (token) cfg.headers.autk = token;
            return cfg;
        });
        this.$router.push("login");
    }
}
</script>
