<template>
    <div>
        <div v-for="p in persons" :key="p.username">
            name: {p.name}<br/>
            username: {p.username}<br/>
            detail: {p.detail}<br/>
            type: {p.type}</br>
            <hr/>
        </div>
        <button>Add</button>
        <div>
            <p>Type</p>
            <input type="radio" name="type" id="type-mentor" value="MENTOR" :checked="buf.type == 'MENTOR'" @change="buf.type = 'MENTOR'" />
            <label for="type-mentor">Mentor</label>
            <input type="radio" name="type" id="type-student" value="STUDENT" :checked="buf.type == 'STUDENT'" @change="buf.type = 'STUDENT'" />
            <label for="type-student">Student</label>
            <p>Name</p>
            <input type="text" v-model="buf.name" />
            <p>Username</p>
            <input type="text" v-model="buf.username" />
            <p>Password</p>
            <input type="password" v-model="buf.password" />
            <p>re-Password</p>
            <input type="password" v-model="buf.repasswd" />
            <p>Detail</p>
            <textarea v-model="buf.detail" />
            <button @click="onSubmit">Submit</button>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            buf: {
                type: "MENTOR",
                username: "std",
                name: "student",
                password: "123",
                repasswd: "123",
                detail: "noDetail"
            },
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
        onSubmit() {
            let payload = {
                type: this.buf.type,
                username: this.buf.username,
                password: this.buf.password,
                name: this.buf.name,
                detail: this.buf.detail
            };
            this.$axios.post("/persons", payload)
                .then(rs => {
                    console.log(rs);
                });
        }
    },
    beforeMount() {
        //this.$axios.get("/persons");
        let x = Math.round(Math.random() * 100000);
        this.buf.name += " " + x;
        this.buf.username += "_" + x;
    }
}
</script>
