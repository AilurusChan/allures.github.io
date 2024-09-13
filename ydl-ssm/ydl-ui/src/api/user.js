import request from "@/api";

export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

export function logout() {
    return request({
        url: '/logout',
        method: 'get',
    })
}

// 新增用户
export function add(data) {
    return request({
        url: '/ydlUser',
        method: 'post',
        data: data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/ydlUser',
        method: 'put',
        data: data
    })
}

// 修改用户
export function deleteUser(userId){
    return request({
        url: '/ydlUser/'+userId,
        method: 'delete'
    })
}

export function listUser(data) {
    return request({
        url: '/ydlUser',
        method: 'get',
        params: data
    })
}

export function getInfo() {
    return request({
        url: '/ydlUser/getInfo',
        method: 'get'
    })
}

export function getById(id) {
    return request({
        url: '/ydlUser/'+id,
        method: 'get'
    })
}

// // 新增用户
// // 查询用户列表
// export function listUser(query) {
//     return request({
//         url: '/login',
//         method: 'post',
//         params: query
//     })
// }

// // 新增用户
// export function addUser(data) {
//     return request({
//         url: '/user',
//         method: 'post',
//         data: data
//     })
// }