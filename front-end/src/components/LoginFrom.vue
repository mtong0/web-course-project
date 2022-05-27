<template>
  <div class="modal fade" id="login" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="loginModalLabel">Log In</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body mb-3">
          <div class="input-group">
            <span class="input-group-text">Username</span>
            <input type="text" class="form_control" placeholder="example@eamil.com" aria-label="Username" id="username">
          </div>
          <div class="input-group mb-3" style="margin-top: 5px">
            <span class="input-group-text">Password</span>
            <input type="password" class="form_control" aria-label="Password" id="password">
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" v-on:click="login()">Log in</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import bootstrapjs from "../static/bootstrap-5.1.3-dist/js/bootstrap.min.js";
export default {
  name: "loginForm",
  methods: {
    login() {
      let username = document.getElementById("username").value;
      let password = document.getElementById("password").value;
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/account/login");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.onreadystatechange = function () {
        if (this.readyState === 4) {
          if (this.status === 200) {
            alert("Log in successfully!");
          }
        }
      }
      xHttp.responseType = "json";
      xHttp.onload = function () {
        let loginInfo = this.response["loginInfo"];
        localStorage.setItem("userId", loginInfo["userId"]);
        localStorage.setItem("nickName", loginInfo["nickName"]);
        localStorage.setItem("isLogin", "true");
        window.location.reload();
      }
      let data = {
        "username": username,
        "password": password
      }
      xHttp.send(JSON.stringify(data));
    }
  }
}
</script>

<style scoped>
</style>