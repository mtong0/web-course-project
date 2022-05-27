<template>
  <div>
    <h1 class="fs-3">My Account</h1>
    <div>
      <label for="nickName">Nick Name: </label><span id="nickName">{{userInfo['nickName']}}</span>
    </div>
    <div>
      <label for="email">My Email: </label><span id="email">{{userInfo['email']}}</span>
    </div>
    <div>
      <label for="experience">Experience:</label><span id="experience">{{userInfo['experience']}}</span>
    </div>
    <div>
      <label for="badge">My Badge:</label><span id="badge">{{userInfo["badge"]['name']}}</span>
    </div>
    <div>
      <label for="next">Experience needed for next level: </label><span id="next">{{userInfo.badge['forNextLevel']}}</span>
    </div>
    <div>
      <label for="topics">My topics: </label><span id="topics">{{userInfo['topics']}}</span>
    </div>
    <div class="card" style="width: 100%; cursor: pointer" v-for="item in markedAnswers">
      <div class="card-body">
        <h5 class="card-title" style="display: inline-block" @click="goToQuestion(item['questionId'])" >{{item["question"]}}</h5>
        <h6 class="card-subtitle mb-2 text-muted">{{item["posterName"]}}</h6>
        <p style="height: 2rem; overflow: hidden; text-overflow: ellipsis;white-space: nowrap;" class="card-text">{{item["content"]}}</p>
        <span>My tags: {{item["tags"]}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Account",
  data(){return {
    userInfo: {},
    markedAnswers: [],
  }},
  mounted() {
    this.getUserInfo();
    this.getMarkedAnswer();
  },
  methods: {
    getUserInfo(){
      let userId = localStorage.getItem("userId");
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/account/info?userId="+userId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      let _this = this;
      xHttp.onload = function () {
        _this.userInfo = this.response['userInfo'];
      }
      xHttp.send();
    },
    getMarkedAnswer() {
      let userId = localStorage.getItem("userId");
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/account/marked-answers?userId="+userId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      let _this = this;
      xHttp.onload = function () {
        _this.markedAnswers = this.response['markedAnswers'];
        console.log(_this.markedAnswers);
      }
      xHttp.send();
    },
    goToQuestion(questionId){
      this.$router.push({path: "/question?questionId="+questionId})
    },
  }
}
</script>

<style scoped>

</style>