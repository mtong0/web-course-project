<template>
  <div style="width: 60%; margin: auto">
    <div class="card" style="width: 100%; cursor: pointer" v-for="item in questionCards">
      <div class="card-body">
        <h5 class="card-title" style="display: inline-block" @click="goToQuestion(item['questionId'])" >{{item["content"]}}</h5>
        <button @click="markGoodQuestion(item['questionId'])">Good Question: {{item["questionVoteNum"]}}</button>
        <h6 class="card-subtitle mb-2 text-muted">{{item["answerNickName"]}}</h6>
        <p style="height: 2rem; overflow: hidden; text-overflow: ellipsis;white-space: nowrap;" class="card-text">{{item["answerContent"]}}</p>
        <div v-show="item['answerNickName']!==null">
          <label for="vote">Vote Number:</label><span id="vote" style="margin-right: 1rem;">{{item["answerVoteNum"]}}</span>
          <label for="reply">Reply Number:</label><span id="reply">{{item["answerReplyNum"]}}</span>
        </div>
        <div v-show="item['answerNickName']===null">
          <span>No Answer Yet!</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data(){return {
    isUserLogin: !localStorage.getItem("isLogin"),
    questionCards: [],
  }},
  methods: {
    goToQuestion(questionId){
      this.$router.push({path: "/question?questionId="+questionId})
    },
    markGoodQuestion(questionId) {
      if (localStorage.getItem("userId")=="0") {
        alert("please login first");
        return;
      }
      console.log(questionId)
      const xHttp = new XMLHttpRequest();
      xHttp.open("GET", "/api/question/vote?userId="+localStorage.getItem("userId")
        +"&questionId="+questionId);
      xHttp.setRequestHeader("Accept", "application/json");
      xHttp.setRequestHeader("Content-Type", "application/json");
      xHttp.responseType = "json";
      let _this = this;
      xHttp.onload = function () {
        alert(this.response["response"]);
      }
      xHttp.send();
    }
  },
  mounted() {
    if (localStorage.getItem("reLocate")==="true") {
      localStorage.setItem("reLocate", "false");
      this.$router.push({path:"/search"});
    }
    const xHttp = new XMLHttpRequest();
    xHttp.open("GET", "/api/question/list?userId="+localStorage.getItem("userId"));
    xHttp.setRequestHeader("Accept", "application/json");
    xHttp.setRequestHeader("Content-Type", "application/json");
    xHttp.responseType = "json";
    let _this = this;
    xHttp.onload = function () {
      _this.questionCards = this.response["questionCards"];
      console.log(_this.questionCards);
    }
    xHttp.send();
  }
}
</script>