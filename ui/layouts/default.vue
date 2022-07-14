<template>
    <Nuxt />
</template>
<style>
@import url('https://fonts.googleapis.com/css2?family=Fira+Sans&display=swap');
body, input, textarea {
    font-size: 16px;
    line-height: 1.5;
    font-family: 'Fira Sans', sans-serif;
}
body {
    margin: 0;
}
input {
    outline: none;
    box-shadow: none;
    border: 1px solid lightgrey;
    border-radius: 4px;
    padding: 6px 8px;
    margin: 4px 0;
    background: transparent;
}
button {
    outline: none;
    border: 1px solid lightgrey;
    margin: 4px 6px;
    padding: 12px 16px;
    border-radius: 4px;
}
button:hover {
    box-shadow: 0 0 12px lightgrey;
}
h3 {
    margin: 14px 0;
}
.leaf {
    border-radius: 12px 12px 12px 0;
    padding: 8px 12px;
    box-shadow: 0 3px 8px lightgrey;
    margin: 8px;
}
</style>
