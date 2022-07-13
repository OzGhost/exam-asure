<template>
    <div>
        <div v-for="p in persons" :key="p.username">
            name: {p.name}<br/>
            username: {p.username}<br/>
            detail: {p.detail}<br/>
            type: {p.type}</br>
            <hr/>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            persons: [
                {
                    name: "",
                    username: "",
                    detail: "",
                    type: ""
                }
            ]
        };
    },
    methods: {
    },
    mounted() {
        this.$axios.get("/persons");
    }
}
</script>
