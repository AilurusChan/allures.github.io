import axios from "axios";

const request = axios.create({
  baseURL: "http://localhost:8088/",
  timeout: 10000,
  headers: { "Content-Type": "application/json;charset=utf-8" },
});

export default request;
