<template>
  <div style="width: 60%; margin: auto">
    <div class="card" style="width: 100%;">
      <div class="card-body">
        <h5 class="card-title">{{question["content"]}}</h5>
        <div>
          <label for="vote">Vote Number:</label><span id="vote" style="margin-right: 1rem">{{question["vote"]}}</span>
          <label for="vote">Answer Number: </label><span id="reply">{{question["reply"]}}</span>
          <button class="btn btn-primary" style="margin-left: 20rem;" @click="goToAddAnswer">Answer</button>
        </div>
      </div>
    </div>
    <div class="card" style="width: 100%" v-for="item in question['answers']">
      <div class="card-body" :id="item['id']">
        <div>
          <span style="margin-right: 2rem">{{item['posterName']}}</span>
          <span>{{item['createDate'].substring(0, 10)}}</span>
        </div>
        <p @click="goToAnswer(item['id'])" style="cursor: pointer">{{item['content']}}</p>
        <div>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="vote(item['id'])">vote:&nbsp;</a> <span>{{item['vote']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="like(item['id'])">like:&nbsp;</a> <span>{{item['like']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="mark(item['id'])">mark:&nbsp;</a> <span>{{item['mark']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="reply(item['id'])">reply:&nbsp;</a> <span>{{item['reply']}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Question",
  data() {return {
      question: {}
    }
  },
  methods: {
    goToAddAnswer() {
      if(localStorage.getItem("userId")=="0") {
        alert("Please log in first");
        return;
      }
      this.$router.push({path: "/answer/add?questionId="+this.question["id"]});
    },
    goToAnswer(answerId){
      this.$router.push({path: "/answer/?answerId="+answerId});
    },
    vote(answerId){
      if (localStorage.getItem("userId")=="0") {
        alert("please login first");
        return;
      }
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/answer/vote?userId="
          +localStorage.getItem("userId")+"&answerId="+answerId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.onload = function () {
        alert(this.response["response"]);
      }
      xHttp.send();
    },
    like(answerId){
      if (localStorage.getItem("userId")=="0") {
        alert("please login first");
        return;
      }
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/answer/like?userId="
          +localStorage.getItem("userId")+"&answerId="+answerId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.onload = function () {
        alert(this.response["response"]);
      }
      xHttp.send();
    },
    mark(answerId) {
      if (localStorage.getItem("userId")=="0") {
        alert("please login first");
        return;
      }
      let tags = window.prompt("please enter tags");
      if (tags === null) {
        return;
      }
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/answer/mark?userId="
          +localStorage.getItem("userId")+"&answerId="+answerId
          +"&tags="+tags);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.onload = function () {
        alert(this.response["response"]);
      }
      xHttp.send();
    },
    reply(answerId) {
      if (localStorage.getItem("userId")=="0") {
        alert("please login first");
        return;
      }
      let replyContent = window.prompt("Add Reply:");
      if (replyContent === null) {
        return;
      }
      let data = {
        answerId: answerId,
        posterId: localStorage.getItem("userId"),
        content: replyContent
      }
      const xHttp = new XMLHttpRequest();
      xHttp.open("POST", "/api/answer/reply");
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      xHttp.send(JSON.stringify(data));
    }
  },
  mounted() {
    let questionId = this.$route.query.questionId;
    let answerId = this.$route.query.answerId;
    const xHttp = new XMLHttpRequest();
    xHttp.open("GET", "/api/question/"+questionId);
    xHttp.setRequestHeader("Accept", "application/json");
    xHttp.setRequestHeader("Content-Type", "application/json");
    xHttp.responseType = "json";
    let _this = this;
    xHttp.onload = function () {
      _this.question = this.response["question"];
      console.log(_this.question);
    }
    xHttp.send();
    if (answerId !== undefined) {
      document.getElementById("answerId").scrollIntoView();
    }
  }
}
</script>

<style scoped>

</style>