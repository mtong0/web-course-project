<template>
  <div style="width: 60%; margin: auto">
    <div class="card" style="width: 100%; cursor: pointer">
      <div class="card-body">
        <h5 class="card-title">{{answer["questionName"]}}</h5>
      </div>
    </div>

    <div class="card" style="width: 100%">
      <div class="card-body">
        <div>
          <span style="margin-right: 2rem">{{answer['posterName']}}</span>
          <span>{{answer['createDate'].substring(0, 10)}}</span>
        </div>
        <p>{{answer['content']}}</p>
        <div>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="vote(answer['id'])">vote:&nbsp;</a> <span>{{answer['vote']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="like(answer['id'])">like:&nbsp;</a> <span>{{answer['like']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="mark(answer['id'])">mark:&nbsp;</a> <span>{{answer['mark']}}</span>
          <a style="text-decoration: underline; color: blue; cursor:pointer;" @click="reply(answer['id'])">reply:&nbsp;</a> <span>{{answer['reply']}}</span>
        </div>
      </div>
    </div>
    <div class="card" style="width: 100%" v-for="item in answer['replies']">
      <div class="card-body">
        <div>
          <span style="margin-right: 2rem">{{item['posterName']}}</span>
          <span>{{item['createDate'].substring(0, 10)}}</span>
        </div>
        <p>{{item['content']}}</p>
        <span>Like: {{item['like']}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Answer",
  data(){return{
    answer: {}
  }},
  mounted() {
    let answerId = this.$route.query.answerId;
    const xHttp = new XMLHttpRequest();
    xHttp.open("GET", "/api/answer/get?answerId="+answerId);
    xHttp.setRequestHeader("Accept", "application/json");
    xHttp.setRequestHeader("Content-Type", "application/json");
    xHttp.responseType = "json";
    let _this = this;
    xHttp.onload = function () {
      _this.answer = this.response["answer"];
      console.log(_this.answer);
    }
    xHttp.send();
  },
  methods: {
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
  }
}
</script>

<style scoped>

</style>