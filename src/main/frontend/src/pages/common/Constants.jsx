export const CORS_HEADERS = new Headers({
    'Access-Control-Allow-Origin':'*',
    'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
    'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
    'Content-Type': 'multipart/form-data'
});

export const SERVER_HOST = process.env.host;
export const SERVER_PORT = process.env.port;
export const CONTEXT_PATH = process.env.CONTEXT_PATH || 'tloader';