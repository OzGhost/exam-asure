export default {
    ssr: false,
    target: 'static',
    modules: ['@nuxtjs/axios'],
    components: true,
    axios: {
        baseURL: 'http://0.0.0.0:1901',
        headers: {
            common: {
                'Content-Type': 'application/json'
            }
        }
    }
}

