export default {
    ssr: false,
    target: 'static',
    modules: ['@nuxtjs/axios'],
    components: true,
    head: {
        title: 'Exam - Asure',
        meta: [
            { charset: 'utf-8' },
            { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        ]
    },
    axios: {
        baseURL: 'http://0.0.0.0:1901',
        headers: {
            common: {
                'Content-Type': 'application/json'
            }
        }
    }
}

