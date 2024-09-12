import { createRouter, createWebHistory } from "vue-router";

const routes = [];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from) => {
  console.log(to);
  console.log(from);
  return true;
});

export default router;
