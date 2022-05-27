import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "../views/Index"
import AddQuestion from "../views/AddQuestion"
import Question from "../views/Question";
import AddAnswer from "../views/AddAnswer";
import Answer from "../views/Answer";
import Account from "../views/Account";
import SignUp from "../views/SignUp";
import SearchResult from "../views/SearchResult";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
  }, {
    path: "/question/add",
    name: "AddQuestion",
    component: AddQuestion
  }, {
    path: "/question",
    name: "Question",
    component: Question
  }, {
    path: "/answer/add",
    name: "AddAnswer",
    component: AddAnswer
  }, {
    path: "/answer/",
    name: "Answer",
    component: Answer,
  }, {
    path: "/account",
    name: "Account",
    component: Account,
  }, {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
  }, {
    path: "/search",
    name: "Search",
    component: SearchResult,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
