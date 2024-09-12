import request from "@/api";

//　ユーザーリストを検索
export function listUser(query) {
  return request({
    url: "/user",
    method: "get",
    params: query,
  });
}

//　ユーザー新規登録
export function addUser(data) {
  return request({
    url: "/user",
    method: "post",
    params: data,
  });
}
