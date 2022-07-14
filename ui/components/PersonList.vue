<template>
    <div>
        <div v-for="p in persons" :key="p.id">
            name: {{p.name}}<br/>
            detail: {{p.detail}}<br/>
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
                    id: 0,
                    name: "",
                    detail: ""
                }
            ]
        };
    },
    created() {
        this.$axios.$get("/persons")
            .then(rs => {
                this.persons = rs;
            });
    }
}
</script>
