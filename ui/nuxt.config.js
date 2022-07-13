export default {
    ssr: false,
    target: 'static',
    modules: ['@nuxtjs/axios'],
    axios: {
        baseURL: 'http://0.0.0.0:1901',
        headers: {
            common: {
                'Content-Type': 'application/json'
            }
        }
    }
}

