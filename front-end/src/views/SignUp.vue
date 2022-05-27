<template>
  <div style="width: 60%; margin: auto">
    <div style="width: 100%;">
      <div>
        <label for="email">Email:</label><input type="email" id="email" v-model="form.email">
      </div>
      <div>
        <label for="nickName">Nick Name:</label><input type="text" id="nickName" v-model="form.nickName">
      </div>
      <div>
        <label for="password">Password:</label><input type="password" id="password" v-model="form.password">
      </div>
      <div class="input-group">
        <label for="topics">Topics:</label><input type="text" id="topics" v-model="form.topics">
        <button class="btn btn-secondary" @click="clearTopic">Clear</button>
      </div>
      <div class="input-group">
        <select class="form-select" aria-label="Default select example" v-model="selectedTopic">
          <option :value="item['id']" v-for="item in topicList">{{item['name']}}</option>
        </select>
        <button class="btn-secondary btn" @click="addTopic">Add Topic</button>
      </div>
      <div>
        <button class="btn btn-primary" @click="signup">Sign Up</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SignUp",
  data(){return {
    form: {
      email: "",
      nickName: "",
      password: "",
      topics: "",
    },
    selectedTopic: "",
    topicList: [
      {id: "sports", name: "Sports"},
      {id: "music", name: "Music"},
      {id: "history", name: "History"},
      {id: "arts", name: "Arts"},
      {id: "literature", name: "Literature"},
    ],
  }},
  methods: {
    addTopic(){
      this.form.topics += this.selectedTopic+",";
    },
    clearTopic(){
      this.form.topics = "";
    },
    signup(){
      let _this = this;
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/account/signup");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.onreadystatechange = function () {
        if (this.readyState === 4) {
          if (this.status === 200) {
            alert("Sign up successfully!");
          }
          _this.$router.push({path: "/"});
        }
      }
      xHttp.responseType = "json";
      xHttp.send(JSON.stringify(this.form));
    }
  }
}
</script>

<style scoped>

</style>