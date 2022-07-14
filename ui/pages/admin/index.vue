<template>
    <div class="admin">
        <h3>List of Person</h3>
        <hr/>
        <div class="leaf rbox person-line head">
            <p>Type</p>
            <p>Name</p>
            <p>Username</p>
            <p>Detail</p>
        </div>
        <div class="leaf rbox person-line" v-for="p in persons" :key="p.id">
            <p><label>Type: </label><span class="lowtext">{{p.role}}</span></p>
            <p><label>Name: </label>{{p.name}}</p>
            <p><label>Username: </label>{{p.username}}</p>
            <p><label>Detail: </label>{{p.detail}}</p>
            <button @click="onRemove(p.id)">Remove</button>
        </div>
        <button class="add-btn" @click="opening = true">Add</button>
        <div v-if="opening" class="dbox leaf">
            <button class="nobound" @click="opening = false">&times;</button>
            <h4>Add new person</h4>
            <hr/>
            <p class="pb">Type</p>
            <input type="radio" name="type" id="type-mentor" value="MENTOR" :checked="buf.type == 'MENTOR'" @change="buf.type = 'MENTOR'" />
            <label class="pr-2" for="type-mentor">Mentor</label>
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
            <textarea rows="4" v-model="buf.detail" />
            <button class="sub-btn" @click="onSubmit">Submit</button>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            opening: false,
            buf: {
                // FIXME remove this
                type: "MENTOR",
                username: "p",
                name: "person",
                password: "123",
                repasswd: "123",
                detail: "noDetail"
            },
            persons: [
                {
                    id: 0,
                    name: "...",
                    username: "...",
                    detail: "...",
                    role: "..."
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
            this.$axios.$post("/persons", payload)
                .then(rs => {
                    this.persons.push({
                        id: rs,
                        name: payload.name,
                        username: payload.username,
                        detail: payload.detail,
                        role: payload.type
                    });
                    this.opening = false;
                    // FIXME remove
                    let x = Math.round(Math.random() * 1000);
                    this.buf.name = "person " + x;
                    this.buf.username = "p_" + x;
                });
        },
        onRemove(id) {
            this.$axios.$delete("/persons/"+id+"/")
                .then(() => {
                    this.persons = this.persons.filter(x => x.id != id);
                });
        }
    },
    created() {
        let x = Math.round(Math.random() * 1000);
        this.buf.name += " " + x;
        this.buf.username += "_" + x;
        this.$axios.$get("/persons")
            .then(rs => {
                this.persons = rs;
            });
    }
}
</script>
<style>
.lowtext {
    text-transform: lowercase;
}
.admin {
    overflow-y: auto;
    height: calc(100vh - 42px);
}
.admin::after {
    content: "";
    display: block;
    clear: both;
}
.admin h3 {
    text-align: center;
}
.admin button {
    background: #c9cba3;
    margin: 0;
}
.admin .add-btn {
    color: white;
    background: #e26d5c;
    border-color: #e26d5c;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    box-sizing: border-box;
    margin: 0;
}
.person-line p {
    margin: 0;
}
.dbox {
    position: fixed;
    z-index: 50;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    padding: 12px 16px;
    background: white;
    overflow-y: auto;
}
.dbox .nobound {
    background: transparent;
    border-color: transparent;
    position: absolute;
    right: 0;
    top: 0;
}
.dbox p {
    margin: 16px 0 0;
}
.dbox textarea,
.dbox input[type="text"],
.dbox input[type="password"] {
    width: 100%;
    box-sizing: border-box;
}
.dbox .sub-btn {
    color: white;
    background: #e26d5c;
    border-color: #e26d5c;
}
.dbox input[type="radio"] {
    margin: 4px 8px;
}
.pr-2 {
    padding-right: 8px;
}
.pb {
    padding-bottom: 4px;
}
.person-line.head {
    display: none;
}
@media (min-width: 600px) {
    .rbox {
        display: inline-block;
        float: left;
        box-sizing: border-box;
        width: calc(50% - 16px);
    }
    .admin .add-btn {
        left: calc(50% - 150px);
        width: 300px;
    }
}
@media (min-width: 992px) {
    .rbox {
        display: block;
        float: none;
        width: 100%;
        margin: 4px 0;
    }
    .person-line {
        border-bottom: 1px solid lightgrey;
        box-shadow: none;
    }
    .person-line.head {
        font-weight: bold;
        display: block;
    }
    .person-line::after {
        content: "";
        display: block;
        clear: both;
    }
    .person-line p {
        width: calc(25% - 27px);
        display: inline-block;
    }
    .person-line label {
        display: none;
    }
}
</style>
