<template>
  <div id="app">
    <nav class="navbar navbar-light bg-info">
      <div class="container-lg">
        <a class="navbar-brand" href="#"></a>
        <div class="d-flex">
          <input type="text" placeholder="search" v-model="searchString">
          <button class="btn btn-primary" @click="search">Search</button>
          <button class="btn btn-danger" @click="goToAddQuestion">Add Question</button>
        </div>
        <div class="d-flex">
          {{nickName}}
          <div v-show="!isUserLogin">
            <button class="btn btn-primary" style="margin-right: 1rem" data-bs-toggle="modal" data-bs-target="#login" id="login_button">Login</button>
            <button class="btn btn-secondary" @click="$router.push({path: '/signup'})">Sign Up</button>
          </div>
          <div v-show="isUserLogin">
            <button class="btn btn-primary" style="margin-right: 1rem" @click="myAccount">My Account</button>
            <button class="btn btn-secondary" @click="logout">Log Out</button>
          </div>
        </div>
      </div>
    </nav>
    <router-view/>
    <login-form></login-form>
  </div>
</template>
<script>
import bootstrapjs from "./static/bootstrap-5.1.3-dist/js/bootstrap.min.js";
import loginForm from "./components/LoginFrom";
export default  {
  name: "app",
  components: {
    "loginForm": loginForm,
  },
  data(){return {
    isUserLogin: localStorage.getItem("isLogin")==="true",
    nickName: localStorage.getItem("nickName"),
    searchString: ""
  }},
  mounted() {
    if (localStorage.getItem("userId")===undefined)
      localStorage.setItem("userId", "0");
    if (localStorage.getItem("userId")==="0") {
      localStorage.setItem("isLogin", "false");
    } else {
      localStorage.setItem("isLogin", "true");
    }
  },
  methods: {
    logout(){
      localStorage.setItem("isLogin", "false");
      localStorage.setItem("userId", "0");
      localStorage.removeItem("nickName");
      this.nickName = "";
      window.location.reload();
    },
    goToAddQuestion(){
      console.log(this.isUserLogin);
      if (!this.isUserLogin) {
        alert("please log in first");
      } else {
       this.$router.push({path: "/question/add"})
      }
    },
    myAccount(){
      this.$router.push({path: "/account"});
    },
    search(){
      localStorage.setItem("searchString", this.searchString);
      let urls = document.URL.split("/");
      if (urls[urls.length-1].startsWith("search")) {
        localStorage.setItem("reLocate", "true");
        window.location = "/";
      } else {
        localStorage.setItem("reLocate", "false");
        this.$router.push({path:"/search"});
      }
    },
  }
}
</script>
<style src="./static/bootstrap-5.1.3-dist/css/bootstrap.min.css"></style>
